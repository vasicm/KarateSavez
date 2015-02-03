/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavezrepublikesrpske.gui.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import karatesavezrepublikesrpske.data.dto.BorbaDTO;
import karatesavezrepublikesrpske.data.dto.BorbePojdeinacnoInfoDTO;
import karatesavezrepublikesrpske.data.dto.TakmicarInfoDTO;
import karatesavezrepublikesrpske.data.dto.TakmicenjeDTO;
import karatesavezrepublikesrpske.util.KSRSUtilities;

/**
 * FXML Controller class
 *
 * @author Marko
 */
public class FormaZaDodavanjeBorbeController implements Initializable {

    @FXML
    private BorderPane borderPane;
    @FXML
    private Button uRedu;
    @FXML
    private ChoiceBox<TakmicenjeDTO> takmicenje;
    @FXML
    private ChoiceBox<BorbePojdeinacnoInfoDTO> kategorija;
    @FXML
    private ChoiceBox<TakmicarInfoDTO> plavi;
    @FXML
    private ChoiceBox<TakmicarInfoDTO> crveni;
    @FXML
    private TextField poeniPlavi;
    @FXML
    private TextField poeniCrveni;
    @FXML
    private TextField kaznePlavi;
    @FXML
    private TextField kazneCrveni;
    @FXML
    private TextField nivoTakmicenja;
    @FXML
    private Label labela;

    private final EventHandler<MouseEvent> naKlikURedu = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            TakmicenjeDTO takmicenjeS = takmicenje.getSelectionModel().getSelectedItem();
            BorbePojdeinacnoInfoDTO kategorijaS = kategorija.getSelectionModel().getSelectedItem();
            TakmicarInfoDTO plaviS = plavi.getSelectionModel().getSelectedItem();
            TakmicarInfoDTO crveniS = crveni.getSelectionModel().getSelectedItem();
            String strPoeniPlavi = poeniPlavi.getText();
            String strPoeniCrveni = poeniCrveni.getText();
            String strKaznePlavi = kaznePlavi.getText();
            String strKazneCrveni = kazneCrveni.getText();
            String strNivoTak = nivoTakmicenja.getText();

            if (takmicenjeS == null || kategorijaS == null || plaviS == null || crveniS == null || strPoeniPlavi == null || strPoeniCrveni == null || strKaznePlavi == null || strKazneCrveni == null || strNivoTak == null) {
                labela.setText("Niste popunili sva polja!!!");
                labela.setTextFill(Color.web("#ff0000"));
                return;
            }
            if (!KSRSUtilities.tryParseInt(strPoeniPlavi) || !KSRSUtilities.tryParseInt(strPoeniCrveni) || !KSRSUtilities.tryParseInt(strKaznePlavi) || !KSRSUtilities.tryParseInt(strKazneCrveni) || !KSRSUtilities.tryParseInt(strNivoTak)) {
                labela.setText("Niste korektno popunili polja!!!");
                labela.setTextFill(Color.web("#ff0000"));
                return;
            }
            
            KSRSUtilities.getDAOFactory().getBorbaDAO().dodajBorbu(
                    new BorbaDTO(
                            takmicenjeS.getIdTakmicenja(), 
                            kategorijaS.getIdKategorije(), 
                            plaviS, 
                            crveniS, 
                            Integer.valueOf(strPoeniPlavi), 
                            Integer.valueOf(strPoeniCrveni), 
                            Integer.valueOf(strKaznePlavi), 
                            Integer.valueOf(strKazneCrveni), 
                            Integer.valueOf(strNivoTak)
                    )
            );
            
            Stage stage = (Stage) uRedu.getScene().getWindow();
            stage.close();
        }
    };

    private final EventHandler<MouseEvent> popuniKategorije = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            System.out.println("popuniKategorije11111111");
            if(takmicenje.getSelectionModel().getSelectedItem()==null){
                return;
            }
            System.out.println("popuniKategorije22222222");
            List<BorbePojdeinacnoInfoDTO> kategorije = KSRSUtilities.
                    getDAOFactory().
                    getKategorijaBorbeDAO().
                    sveKategorijeNaTakmicenju(
                            takmicenje.
                            getSelectionModel().
                            getSelectedItem().
                            getIdTakmicenja()
                    );
            kategorija.setItems(FXCollections.observableList(
                    kategorije
            ));
        }
    };
    private final EventHandler<MouseEvent> popuniTakmicarePlavi = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            if(takmicenje.getSelectionModel().getSelectedItem()==null || kategorija.getSelectionModel().getSelectedItem()==null){
                return;
            }
            List<TakmicarInfoDTO> takmicari = KSRSUtilities.
                    getDAOFactory().
                    getTakmicarDAO().
                    tamicari(
                            takmicenje.getSelectionModel().getSelectedItem().getIdTakmicenja(),
                            kategorija.getSelectionModel().getSelectedItem().getIdKategorije()
                    );
            plavi.setItems(FXCollections.observableList(
                    takmicari
            ));

        }
    };
    private final EventHandler<MouseEvent> popuniTakmicareCrveni = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            if(takmicenje.getSelectionModel().getSelectedItem()==null || kategorija.getSelectionModel().getSelectedItem()==null){
                return;
            }
            List<TakmicarInfoDTO> takmicari = KSRSUtilities.
                    getDAOFactory().
                    getTakmicarDAO().
                    tamicari(
                            takmicenje.getSelectionModel().getSelectedItem().getIdTakmicenja(),
                            kategorija.getSelectionModel().getSelectedItem().getIdKategorije()
                    );
            crveni.setItems(FXCollections.observableList(
                    takmicari
            ));
        }
    };

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        uRedu.setOnMouseClicked(naKlikURedu);
        kategorija.setOnMouseClicked(popuniKategorije);
        plavi.setOnMouseClicked(popuniTakmicarePlavi);
        crveni.setOnMouseClicked(popuniTakmicareCrveni);

        List<TakmicenjeDTO> takmicenja = KSRSUtilities.
                getDAOFactory().
                getTakmicenjeDAO().
                takmicenja();

        takmicenje.setItems(FXCollections.observableList(
                takmicenja
        ));

  

  
    }

}
