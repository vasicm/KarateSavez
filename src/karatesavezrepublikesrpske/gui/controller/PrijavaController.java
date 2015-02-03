/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavezrepublikesrpske.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import karatesavezrepublikesrpske.data.dto.BorbaDTO;
import karatesavezrepublikesrpske.data.dto.BorbePojdeinacnoInfoDTO;
import karatesavezrepublikesrpske.data.dto.TakmicarInfoDTO;
import karatesavezrepublikesrpske.data.dto.TakmicenjeDTO;
import karatesavezrepublikesrpske.util.FormLoader;
import karatesavezrepublikesrpske.util.KSRSUtilities;

/**
 * FXML Controller class
 *
 * @author Marko
 */
public class PrijavaController implements Initializable {

    @FXML
    private AnchorPane anchorPrijava;
    @FXML
    private TableView<BorbaDTO> tabela;
    @FXML
    private TableColumn<BorbaDTO, String> kolonaImePrezimePlavog;
    @FXML
    private TableColumn<BorbaDTO, String> kolonaImePrezimeCrvenog;
    @FXML
    private TableColumn<BorbaDTO, String> kolonaPoeniCrveni;
    @FXML
    private TableColumn<BorbaDTO, String> kolonaPoeniPlavi;
    @FXML
    private TableColumn<BorbaDTO, String> kolonaPobjednik;
    @FXML
    private TableColumn<BorbaDTO, String> kolonaKrug;
    @FXML
    private ChoiceBox<TakmicenjeDTO> choiceBoxTakmicenje;
    @FXML
    private ChoiceBox<BorbePojdeinacnoInfoDTO> choiceBoxKategorija;
    @FXML
    private ChoiceBox<TakmicarInfoDTO> choiceBoxTakmicar;
    @FXML
    private Button dugmeOsvjezi;
    @FXML
    private Button dugmePrijaviTakmicara;
    @FXML
    private Button dugmeDodajBorbu;
    
    private final EventHandler<MouseEvent> naKlikOsvjezi = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            TakmicenjeDTO takmicenje = choiceBoxTakmicenje.getSelectionModel().getSelectedItem();
            BorbePojdeinacnoInfoDTO kategorija = choiceBoxKategorija.getSelectionModel().getSelectedItem();
            if(kategorija==null || takmicenje==null){
                return;
            }
            List<BorbaDTO> borbe = KSRSUtilities.getDAOFactory().getBorbaDAO().borbe(takmicenje.getIdTakmicenja(), kategorija.getIdKategorije());
            ObservableList<BorbaDTO> data = FXCollections.observableList(borbe);
            tabela.setItems(data);
        }
    };
    private final EventHandler<MouseEvent> naKlikDodajBorbu = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            try {
                FormLoader.ucitajFormuZaDodavanjeBorbe();
            } catch (IOException ex) {
                //TODO
                Logger.getLogger(PrijavaController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    private final EventHandler<MouseEvent> popuniKategorije = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {

            List<BorbePojdeinacnoInfoDTO> kategorije = KSRSUtilities.
                    getDAOFactory().
                    getKategorijaBorbeDAO().
                    sveKategorijeNaTakmicenju(
                            choiceBoxTakmicenje.
                            getSelectionModel().
                            getSelectedItem().
                            getIdTakmicenja()
                    );
            choiceBoxKategorija.setItems(FXCollections.observableList(
                    kategorije
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
        setFactory();
        initializeChoiceBox();
        dugmeOsvjezi.setOnMouseClicked(naKlikOsvjezi);
        dugmeDodajBorbu.setOnMouseClicked(naKlikDodajBorbu);
        dugmePrijaviTakmicara.setDisable(true);
    }

    private void initializeChoiceBox() {
        List<TakmicenjeDTO> takmicenja = KSRSUtilities.getDAOFactory().getTakmicenjeDAO().takmicenja();
        choiceBoxTakmicenje.setItems(FXCollections.observableList(
                takmicenja
        ));
        choiceBoxTakmicenje.setValue(takmicenja.get(0));

        List<BorbePojdeinacnoInfoDTO> kategorije = KSRSUtilities.getDAOFactory().getKategorijaBorbeDAO().sveKategorije();
        choiceBoxKategorija.setItems(FXCollections.observableList(
                kategorije
        ));
        choiceBoxKategorija.setValue(kategorije.get(0));
        choiceBoxKategorija.setOnMouseClicked(popuniKategorije);
        choiceBoxTakmicar.setDisable(true);
    }

    private void setFactory() {
        tabela.setRowFactory(new Callback<TableView<BorbaDTO>, TableRow<BorbaDTO>>() {
            @Override
            public TableRow<BorbaDTO> call(TableView<BorbaDTO> tableView) {
                final TableRow<BorbaDTO> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem mnuItemEdit = new MenuItem("Prikazi");
                final MenuItem mnuItemDelete = new MenuItem("Obrisi");
                mnuItemEdit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                    }
                });
                contextMenu.getItems().add(mnuItemEdit);
                contextMenu.getItems().add(mnuItemDelete);
                row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
                return row;
            }
        });
        kolonaImePrezimePlavog.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BorbaDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BorbaDTO, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getJmbPlavi().getIme() + " " + p.getValue().getJmbPlavi().getPrezime());
            }
        });
        kolonaImePrezimeCrvenog.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BorbaDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BorbaDTO, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getJmbCrveni().getIme() + " " + p.getValue().getJmbCrveni().getPrezime());
            }
        });
        kolonaPoeniPlavi.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BorbaDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BorbaDTO, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getPoeniPlavi());
            }
        });
        kolonaPoeniCrveni.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BorbaDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BorbaDTO, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getPoeniCrveni());
            }
        });
        kolonaPobjednik.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BorbaDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BorbaDTO, String> p) {
                return new ReadOnlyObjectWrapper("");
            }
        });
        kolonaKrug.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BorbaDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<BorbaDTO, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getNivoTakmicenja());
            }
        });
    }
}
