/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavezrepublikesrpske.gui.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import karatesavezrepublikesrpske.data.dto.KarateKlubDTO;
import karatesavezrepublikesrpske.data.dto.TakmicarInfoDTO;
import karatesavezrepublikesrpske.util.KSRSUtilities;

/**
 * FXML Controller class
 *
 * @author Marko
 */
public class FormaZaDodavanjeTakmicaraController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private Button uRedu;
    @FXML
    private Label labela;
    @FXML
    private TextField jmb;
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

    private final EventHandler<MouseEvent> naKlikURedu = new EventHandler<MouseEvent>() {
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
            KSRSUtilities.getDAOFactory().getTakmicarDAO().dodajTakmicara(takmicar);
            Stage stage = (Stage) uRedu.getScene().getWindow();
            stage.close();
        }
    };

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        uRedu.setOnMouseClicked(naKlikURedu);
        List<KarateKlubDTO> klubovi = KSRSUtilities.getDAOFactory().getKarateKlubDAO().klubovi();
        klub.setItems(FXCollections.observableList(
                klubovi
        ));
    }

}
