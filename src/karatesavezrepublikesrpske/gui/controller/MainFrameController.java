/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavezrepublikesrpske.gui.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import karatesavezrepublikesrpske.util.FormLoader;

/**
 * FXML Controller class
 *
 * @author Marko
 */
public class MainFrameController implements Initializable {

    @FXML
    private ImageView imgPocetna;
    @FXML
    private ImageView imgPregled;
    @FXML
    private ImageView imgPrijava;
    @FXML
    private ImageView imgPodesavanja;
    @FXML
    private ImageView imgIzlaz;
    @FXML
    private BorderPane borderPane;

    private Map<ImageView, Node> anchorNode;
    private Node activeNode;

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

    private final EventHandler<MouseEvent> eventOnClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent t) {
            activeNode = anchorNode.get((ImageView) t.getTarget());
            if (activeNode == null) {
                System.exit(0);
            }
            borderPane.setCenter(activeNode);
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
        anchorNode = new HashMap<>();

        anchorNode.put(imgPocetna, FormLoader.getNodePocetna());
        
        activeNode = anchorNode.get(imgPocetna);
        borderPane.setCenter(activeNode);
        
        anchorNode.put(imgPodesavanja, FormLoader.getNodePodesavanja());
        anchorNode.put(imgPrijava, FormLoader.getNodePrijava());
        anchorNode.put(imgPregled, FormLoader.getNodePregled());
        anchorNode.put(imgIzlaz, null);

        Iterator<ImageView> iterator = anchorNode.keySet().iterator();

        while (iterator.hasNext()) {
            ImageView img = iterator.next();
            img.setOnMouseEntered(eventOnEntered);
            img.setOnMouseExited(eventOnExited);
            img.setOnMouseClicked(eventOnClick);
        }

    }

}
