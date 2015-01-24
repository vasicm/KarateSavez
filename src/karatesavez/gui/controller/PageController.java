/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavez.gui.controller;

import javafx.event.EventHandler;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import karatesavez.jpa.controller.ClanJpaController;
import karatesavez.jpa.controller.KarateKlubJpaController;
import karatesavez.jpa.controller.KategorijaJpaController;
import karatesavez.jpa.controller.TakmicarJpaController;
import karatesavez.jpa.controller.TakmicenjeJpaController;
import karatesavez.jpa.entity.Clan;
import karatesavez.jpa.entity.KarateKlub;
import karatesavez.jpa.entity.Kategorija;
import karatesavez.jpa.entity.Takmicar;
import karatesavez.jpa.entity.Takmicenje;

/**
 * FXML Controller class
 *
 * @author Marko
 */
public class PageController implements Initializable {

    private int i = 0;
    @FXML
    private ImageView imgIzlaz;
    @FXML
    private ImageView imgPregled;
    @FXML
    private ImageView imgPrijava;
    @FXML
    private ImageView imgPodesavanja;
    @FXML
    private ImageView imgPocetna;
    @FXML
    private AnchorPane anchorPrijava;
    @FXML
    private AnchorPane anchorPocetna;
    @FXML
    private TableView<?> tableViewOglasi;
    @FXML
    private TableColumn<?, ?> kolonaDatum;
    @FXML
    private TableColumn<?, ?> kolonaTipOglasa;
    @FXML
    private TableColumn<?, ?> kolonaOglas;
    @FXML
    private AnchorPane anchorPregled;
    @FXML
    private Button btnDodajTakmicaraPregled;
    @FXML
    private Button btnIzbrisiTakmicaraPregled;
    @FXML
    private Button btnOsvjeziPregled;
    @FXML
    private ImageView btnOsvjezi;
    @FXML
    private TableView<Clan> tableViewPregled;
    @FXML
    private TableColumn<Clan, String> tableColumnImePregled;
    @FXML
    private TableColumn<Clan, String> tableColumnPrezimePregled;
    @FXML
    private TableColumn<Clan, String> tableColumnKlubPregled;
    @FXML
    private TableColumn<Clan, String> tableColumnGodinaRodjenjaPregled;
    @FXML
    private TableColumn<Clan, String> tableColumnPojasPregled;
    @FXML
    private ChoiceBox<KarateKlub> choiceBoxKlubPregled;
    @FXML
    private CheckBox checkBoxPregled;
    @FXML
    private ChoiceBox<Takmicenje> choiceBoxTakmicenjePregled;
    @FXML
    private ChoiceBox<Kategorija> choiceBoxKategorijaPregled;
    @FXML
    private AnchorPane anchorPodesavanja;
    @FXML
    private AnchorPane anchorProba;
    @FXML
    private Button btn;
    @FXML
    private Label label;

    private AnchorPane activeAnchorPane;

    private Map<ImageView, AnchorPane> anchorMap;

    private EntityManagerFactory emf;

    private final EventHandler<MouseEvent> eventOnClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            activeAnchorPane.setVisible(false);
            activeAnchorPane = anchorMap.get((ImageView) t.getTarget());
            if (activeAnchorPane == null) {
                System.exit(0);
            }
            activeAnchorPane.setVisible(true);
        }

    };

    private final EventHandler<MouseEvent> eventOnEntered = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            ImageView tmp = (ImageView) t.getTarget();
            tmp.setFitHeight(90);
            tmp.setFitWidth(90);
        }
    };
    private final EventHandler<MouseEvent> eventOnExited = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            ImageView tmp = (ImageView) t.getTarget();
            tmp.setFitHeight(80);
            tmp.setFitWidth(80);
        }
    };

    private final EventHandler<MouseEvent> eventOnClickChoiceBoxKlubPregled = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            System.out.println("eventOnClickChoiceBoxKlubPregled" + t.getTarget());
            boolean check = !choiceBoxKategorijaPregled.isDisable();
            choiceBoxKategorijaPregled.setDisable(check);
            choiceBoxKlubPregled.setDisable(check);
            choiceBoxTakmicenjePregled.setDisable(check);

        }
    };

    @FXML
    private Button btnDodajTakmicenejPrijava;
    @FXML
    private Button btnIzbrisiTakmicenjePrijava;
    @FXML
    private Button btnOsveziPrijava;
    @FXML
    private Button btnPrijaviTakmicara;
    @FXML
    private ChoiceBox<Takmicenje> choiceBoxTakmicenjaPrijava;
    @FXML
    private ChoiceBox<Kategorija> choiceBoxKategorijaPrijava;
    @FXML
    private ChoiceBox<Takmicar> choiceBoxTakmicarPrijava;
    @FXML
    private Button btnDodajIzvodjenjePrijava;

    KategorijaJpaController kategorijaJpa;
    TakmicenjeJpaController takmicenjeJpa;
    KarateKlubJpaController klubJpa;
    TakmicarJpaController takmicarJpa;
    ClanJpaController clanJpa;

    public PageController() {
        this.eventOnClickBtnOsvjezi = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {
                KarateKlub kk = choiceBoxKlubPregled.getSelectionModel().getSelectedItem();
                EntityManager em = emf.createEntityManager();
                
                TypedQuery<Clan> query
                        = (TypedQuery<Clan>) em.createNamedQuery("Clan.findIdKluba");
                TypedQuery<Clan> setParameter = query.setParameter("IDKluba",kk.getIDKluba());
                ObservableList<Clan> data = FXCollections.observableList(query.getResultList());
                tableViewPregled.setItems(data);
            }
        };
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        emf = Persistence.createEntityManagerFactory("KarateSavezPU");
        kategorijaJpa = new KategorijaJpaController(emf);
        takmicenjeJpa = new TakmicenjeJpaController(emf);
        klubJpa = new KarateKlubJpaController(emf);
        takmicarJpa = new TakmicarJpaController(emf);
        clanJpa = new ClanJpaController(emf);

        anchorMap = new HashMap<ImageView, AnchorPane>();

        anchorMap.put(imgPocetna, anchorPocetna);
        anchorMap.put(imgPodesavanja, anchorPodesavanja);
        anchorMap.put(imgPrijava, anchorPrijava);
        anchorMap.put(imgPregled, anchorPregled);
        anchorMap.put(imgIzlaz, null);

        activeAnchorPane = anchorPocetna;
        activeAnchorPane.setVisible(true);

        Iterator<ImageView> iterator = anchorMap.keySet().iterator();

        while (iterator.hasNext()) {
            ImageView img = iterator.next();
            img.setOnMouseEntered(eventOnEntered);
            img.setOnMouseExited(eventOnExited);
            img.setOnMouseClicked(eventOnClick);
        }
        initializePocetna();
        initializePregled();
        initializePrijava();
        initializePodesavanja();
    }

    private void initializePocetna() {
    }
    private final EventHandler<MouseEvent> eventOnClickBtnOsvjezi;

    private void initializePregled() {

        checkBoxPregled.setOnMouseClicked(eventOnClickChoiceBoxKlubPregled);
        choiceBoxKategorijaPregled.getItems().addAll(kategorijaJpa.findKategorijaEntities());
        choiceBoxTakmicenjePregled.getItems().addAll(takmicenjeJpa.findTakmicenjeEntities());
        choiceBoxKlubPregled.getItems().addAll(klubJpa.findKarateKlubEntities());
        btnOsvjeziPregled.setOnMouseClicked(eventOnClickBtnOsvjezi);

        ObservableList<Clan> data = FXCollections.observableList(clanJpa.findClanEntities());
        tableViewPregled.setItems(data);
        setCellValueFactoryPregled();
    }

    private void initializePrijava() {
        choiceBoxTakmicenjaPrijava.getItems().addAll(takmicenjeJpa.findTakmicenjeEntities());
        choiceBoxKategorijaPrijava.getItems().addAll(kategorijaJpa.findKategorijaEntities());
    }

    private void initializePodesavanja() {
    }

    private void setCellValueFactoryPregled() {
        tableColumnImePregled.setCellValueFactory(new Callback<CellDataFeatures<Clan, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Clan, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getIme());
            }
        });
        tableColumnPrezimePregled.setCellValueFactory(new Callback<CellDataFeatures<Clan, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Clan, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getPrezime());
            }
        });
        tableColumnKlubPregled.setCellValueFactory(new Callback<CellDataFeatures<Clan, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Clan, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getIDKluba().getNazivKarateKluba());
            }
        });
        tableColumnPojasPregled.setCellValueFactory(new Callback<CellDataFeatures<Clan, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(CellDataFeatures<Clan, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getPojas());
            }
        });
        tableColumnGodinaRodjenjaPregled.setCellValueFactory(new Callback<CellDataFeatures<Clan, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(CellDataFeatures<Clan, String> p) {
                return new ReadOnlyObjectWrapper(p.getValue().getDatumRodjenja().toString());
            }
        });
    }
}
