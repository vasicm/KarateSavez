/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavezrepublikesrpske.gui.controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import karatesavezrepublikesrpske.data.dto.BorbePojdeinacnoInfoDTO;
import karatesavezrepublikesrpske.data.dto.KarateKlubDTO;
import karatesavezrepublikesrpske.data.dto.TakmicarAnalizaDTO;
import karatesavezrepublikesrpske.data.dto.TakmicarInfoDTO;
import karatesavezrepublikesrpske.util.KSRSUtilities;

/**
 * FXML Controller class
 *
 * @author Marko
 */
public class TakmicarInfoEditController implements Initializable {

    @FXML
    private TextField kilaza;
    @FXML
    private DatePicker datumPiker;
    @FXML
    private TextField ime;
    @FXML
    private TextField prezime;
    @FXML
    private TextField pojas;
    @FXML
    private ChoiceBox<KarateKlubDTO> klub;
    @FXML
    private Label jmb;
    @FXML
    private Label labela;
    @FXML
    private PieChart pieChart;
    @FXML
    private Label brojMeceva;
    @FXML
    private Label prosjekPostignutih;
    @FXML
    private Label prosjekPrimljenih;
    @FXML
    private Button dugmeSacuvaj;

    private TakmicarInfoDTO takmicar;
    /**
     * Initializes the controller class.
     */

    private final EventHandler<MouseEvent> naKlikSacuvaj = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {

            String strJmb = jmb.getText();
            String strIme = ime.getText();
            String strPrezime = prezime.getText();
            String strPojas = pojas.getText();
            LocalDate datum = datumPiker.getValue();
            KarateKlubDTO kKlub = klub.getSelectionModel().getSelectedItem();
            String strKilaza = kilaza.getText();

            if (strJmb == null || strIme == null || strPrezime == null || strPojas == null || datum == null || kKlub == null || strKilaza == null) {
                labela.setText("Niste popunili sva polja!!!");
                labela.setTextFill(Color.web("#ff0000"));
                return;
            }

            if (!KSRSUtilities.tryParseLong(strJmb)) {
                labela.setText("Nekorektan unos za JMB!!!");
                labela.setTextFill(Color.web("#ff0000"));
                return;
            }
            if (!KSRSUtilities.tryParseDouble(strKilaza)) {
                labela.setText("Nekorektan unos za kilazu!!!");
                labela.setTextFill(Color.web("#ff0000"));
                return;
            }

            TakmicarInfoDTO takmicar = new TakmicarInfoDTO(
                    Long.valueOf(strJmb),
                    strIme,
                    strPrezime,
                    strPojas,
                    new Date(datum.toEpochDay()),
                    kKlub,
                    Double.parseDouble(strKilaza)
            );
            KSRSUtilities.getDAOFactory().getTakmicarDAO().azurirajTakmicara(takmicar);
            Stage stage = (Stage) dugmeSacuvaj.getScene().getWindow();
            stage.close();
        }
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dugmeSacuvaj.setOnMouseClicked(naKlikSacuvaj);
        List<KarateKlubDTO> klubovi = KSRSUtilities.getDAOFactory().getKarateKlubDAO().klubovi();
        klub.setItems(FXCollections.observableList(
                klubovi
        ));
    }

    public void setTakmicar(TakmicarInfoDTO takmicar) {
        this.takmicar = takmicar;
        if(takmicar==null)
            return;
        System.out.println(Long.toString(takmicar.getJmb()));
        TakmicarAnalizaDTO analiza = KSRSUtilities.
                getDAOFactory().
                getTakmicarAnalizaDAO().
                analizaTakmicara(takmicar.getJmb());
        prosjekPostignutih.setText(Double.toString(analiza.getProsjekPostignutihPoena()));
        prosjekPrimljenih.setText(Double.toString(analiza.getProsjekPrimljenihPoena()));
        brojMeceva.setText(Integer.toString(analiza.getBrojMeceva()));

        jmb.setText(Long.toString(takmicar.getJmb()));
        ime.setText(takmicar.getIme());
        prezime.setText(takmicar.getPrezime());
        pojas.setText(takmicar.getPojas());
//        datumPiker.setValue(new LocalDate(takmicar.getDatumRodjenja().getTime()));
        klub.setValue(takmicar.getIdKluba());
        kilaza.setText(Double.toString(takmicar.getKilaza()));
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        pieChart.setTitle("Statistika");
        pieChartData.add(new PieChart.Data("Postignuti poeni po mecu", analiza.getProsjekPostignutihPoena()));
        pieChartData.add(new PieChart.Data("Primljeni poeni po mecu", analiza.getProsjekPrimljenihPoena()));
        pieChart.setData(pieChartData);
    }

}
