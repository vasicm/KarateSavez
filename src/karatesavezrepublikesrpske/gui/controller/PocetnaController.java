/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavezrepublikesrpske.gui.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import karatesavezrepublikesrpske.data.dto.OglasDTO;
import karatesavezrepublikesrpske.util.KSRSUtilities;

/**
 * FXML Controller class
 *
 * @author Marko
 */
public class PocetnaController implements Initializable {

    @FXML
    private AnchorPane anchorPocetna;
    @FXML
    private TableView<OglasDTO> tabela;
    @FXML
    private TableColumn<OglasDTO, String> kolonaDatum;
    @FXML
    private TableColumn<OglasDTO, String> kolonaTipOglasa;
    @FXML
    private TableColumn<OglasDTO, String> kolonaOglas;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<OglasDTO> oglasi = KSRSUtilities.getDAOFactory().getOglasDAO().oglasi();
        ObservableList<OglasDTO> data = FXCollections.observableList(oglasi);
        tabela.setItems(data);
        setFactory();
    }

    private void setFactory() {
        tabela.setRowFactory(new Callback<TableView<OglasDTO>, TableRow<OglasDTO>>() {
            @Override
            public TableRow<OglasDTO> call(TableView<OglasDTO> tableView) {
                final TableRow<OglasDTO> row = new TableRow<>();
                final ContextMenu contextMenu = new ContextMenu();
                final MenuItem mnuItemEdit = new MenuItem("Edit");
                final MenuItem mnuItemDelete = new MenuItem("Delete");
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
        kolonaDatum.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OglasDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OglasDTO, String> p) {
                return new ReadOnlyObjectWrapper("no date");
            }
        });
        kolonaTipOglasa.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OglasDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OglasDTO, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getTipOglasa());
            }
        });
        kolonaOglas.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OglasDTO, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OglasDTO, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getTekstOglasa());
            }
        });
    }
}
