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
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import karatesavezrepublikesrpske.data.dto.BorbePojdeinacnoInfoDTO;
import karatesavezrepublikesrpske.data.dto.KarateKlubDTO;
import karatesavezrepublikesrpske.data.dto.OglasDTO;
import karatesavezrepublikesrpske.data.dto.TakmicarInfoDTO;
import karatesavezrepublikesrpske.data.dto.TakmicenjeDTO;
import karatesavezrepublikesrpske.util.FormLoader;
import karatesavezrepublikesrpske.util.KSRSUtilities;

/**
 * FXML Controller class
 *
 * @author Marko
 */
public class PregledController implements Initializable {

    @FXML
    private AnchorPane anchorPregled;
    @FXML
    private TableView<TakmicarInfoDTO> tabela;
    @FXML
    private TableColumn<TakmicarInfoDTO, String> kolonaIme;
    @FXML
    private TableColumn<TakmicarInfoDTO, String> kolonaPrezime;
    @FXML
    private TableColumn<TakmicarInfoDTO, String> kolonaKlub;
    @FXML
    private TableColumn<TakmicarInfoDTO, String> kolonaDatumRodjenja;
    @FXML
    private TableColumn<TakmicarInfoDTO, String> kolonaPojas;
    @FXML
    private ChoiceBox<KarateKlubDTO> choiceBoxKlubPregled;
    @FXML
    private ChoiceBox<TakmicenjeDTO> choiceBoxTakmicenjePregled;
    @FXML
    private ChoiceBox<BorbePojdeinacnoInfoDTO> choiceBoxKategorijaPregled;
    @FXML
    private Button dugmeOsvjezi;
    @FXML
    private Button dugmeDodajTakmicara;
    @FXML
    private Button dugmeObrisiTakmicara;

    private final EventHandler<MouseEvent> naKlikOsvjezi = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            KarateKlubDTO klub = choiceBoxKlubPregled.getSelectionModel().getSelectedItem();
            TakmicenjeDTO takmicenje = choiceBoxTakmicenjePregled.getSelectionModel().getSelectedItem();
            List<TakmicarInfoDTO> takmicari;
            if (klub.getIdKluba() == 0 && takmicenje.getIdTakmicenja() == 0) {
                takmicari = KSRSUtilities.getDAOFactory().getTakmicarDAO().tamicari();
            } else if (klub.getIdKluba() == 0 && takmicenje.getIdTakmicenja() != 0) {
                takmicari = KSRSUtilities.getDAOFactory().getTakmicarDAO().tamicari(null, takmicenje.getNazivTakmicenja(), null);
            } else if (klub.getIdKluba() != 0 && takmicenje.getIdTakmicenja() == 0) {
                takmicari = KSRSUtilities.getDAOFactory().getTakmicarDAO().tamicari(klub.getNazivKarateKluba(), null, null);
            } else {
                takmicari = KSRSUtilities.getDAOFactory().getTakmicarDAO().tamicari(klub.getNazivKarateKluba(), takmicenje.getNazivTakmicenja(), null);
            }
            ObservableList<TakmicarInfoDTO> data = FXCollections.observableList(takmicari);
            tabela.setItems(data);
        }
    };
    private final EventHandler<MouseEvent> naKlikDodajTakmicara = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            try {
                FormLoader.ucitajFormuZaDodavanjeTakmicara();
            } catch (IOException ex) {
                //TODO
                Logger.getLogger(PregledController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dugmeOsvjezi.setOnMouseClicked(naKlikOsvjezi);
        setFactory();
        List<TakmicarInfoDTO> takmicari = KSRSUtilities.getDAOFactory().getTakmicarDAO().tamicari();
        ObservableList<TakmicarInfoDTO> data = FXCollections.observableList(takmicari);
        tabela.setItems(data);
        initializeChoiceBox();
        dugmeDodajTakmicara.setOnMouseClicked(naKlikDodajTakmicara);
        dugmeObrisiTakmicara.setDisable(true);
    }

    private void initializeChoiceBox() {
        List<KarateKlubDTO> klubovi = KSRSUtilities.getDAOFactory().getKarateKlubDAO().klubovi();
        klubovi.add(0, new KarateKlubDTO(0, null, "<svi klubovi>", null, null, null));
        choiceBoxKlubPregled.setItems(FXCollections.observableList(
                klubovi
        ));
        choiceBoxKlubPregled.setValue(klubovi.get(0));
        List<TakmicenjeDTO> takmicenja = KSRSUtilities.getDAOFactory().getTakmicenjeDAO().takmicenja();
        takmicenja.add(0, new TakmicenjeDTO(0, "<sva takmicenja>", null, null, null, null));
        choiceBoxTakmicenjePregled.setItems(FXCollections.observableList(
                takmicenja
        ));
        choiceBoxTakmicenjePregled.setValue(takmicenja.get(0));
        choiceBoxKategorijaPregled.setItems(FXCollections.observableList(
                KSRSUtilities.getDAOFactory().getKategorijaBorbeDAO().sveKategorije()
        ));
        choiceBoxKlubPregled.setDisable(false);
        choiceBoxTakmicenjePregled.setDisable(false);
        choiceBoxKategorijaPregled.setDisable(true);
    }

    private void setFactory() {
        tabela.setRowFactory(new Callback<TableView<TakmicarInfoDTO>, TableRow<TakmicarInfoDTO>>() {
            @Override
            public TableRow<TakmicarInfoDTO> call(TableView<TakmicarInfoDTO> tableView) {
                final TableRow<TakmicarInfoDTO> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem mnuItemEdit = new MenuItem("Prikazi");
                final MenuItem mnuItemDelete = new MenuItem("Obrisi");
                mnuItemEdit.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            FormLoader.ucitajFormuZaEditovanjeTakmicara(tabela.getSelectionModel().getSelectedItem());
                        } catch (IOException ex) {
                            Logger.getLogger(PregledController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                contextMenu.getItems().add(mnuItemEdit);
//                contextMenu.getItems().add(mnuItemDelete);
                row.contextMenuProperty().bind(Bindings.when(row.emptyProperty()).then((ContextMenu) null).otherwise(contextMenu));
                return row;
            }
        });
        kolonaIme.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TakmicarInfoDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TakmicarInfoDTO, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getIme());
            }
        });
        kolonaPrezime.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TakmicarInfoDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TakmicarInfoDTO, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getPrezime());
            }
        });
        kolonaKlub.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TakmicarInfoDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TakmicarInfoDTO, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getIdKluba().getNazivKarateKluba());
            }
        });
        kolonaDatumRodjenja.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TakmicarInfoDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TakmicarInfoDTO, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getDatumRodjenja().toString());
            }
        });
        kolonaPojas.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<TakmicarInfoDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<TakmicarInfoDTO, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getPojas());
            }
        });
    }
}
