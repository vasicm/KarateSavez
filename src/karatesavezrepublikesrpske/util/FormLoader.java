/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavezrepublikesrpske.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import karatesavezrepublikesrpske.data.dto.TakmicarInfoDTO;
import karatesavezrepublikesrpske.gui.controller.MainFrameController;
import karatesavezrepublikesrpske.gui.controller.TakmicarInfoEditController;

/**
 *
 * @author Marko
 */
public class FormLoader {
    public static Node getNodePocetna() {
        FXMLLoader fxmlLoader = new FXMLLoader(FormLoader.class.getResource("/karatesavezrepublikesrpske/gui/fxml/Pocetna.fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public static Node getNodePregled() {
        FXMLLoader fxmlLoader = new FXMLLoader(FormLoader.class.getResource("/karatesavezrepublikesrpske/gui/fxml/Pregled.fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public static Node getNodePrijava() {
        FXMLLoader fxmlLoader = new FXMLLoader(FormLoader.class.getResource("/karatesavezrepublikesrpske/gui/fxml/Prijava.fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public static Node getNodePodesavanja() {
        FXMLLoader fxmlLoader = new FXMLLoader(FormLoader.class.getResource("/karatesavezrepublikesrpske/gui/fxml/Podesavanja.fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException ex) {
            Logger.getLogger(MainFrameController.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    public static void ucitajFormuZaDodavanjeTakmicara() throws IOException {
        FXMLLoader loader = new FXMLLoader(FormLoader.class.getResource("/karatesavezrepublikesrpske/gui/fxml/FormaZaDodavanjeTakmicara.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Dodaj takmicara");

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.show();
    }
    public static void ucitajFormuZaEditovanjeTakmicara(TakmicarInfoDTO takmicar) throws IOException {
        FXMLLoader loader = new FXMLLoader(FormLoader.class.getResource("/karatesavezrepublikesrpske/gui/fxml/TakmicarInfoEdit.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);
        
        TakmicarInfoEditController controler = loader.getController();
        controler.setTakmicar(takmicar);
        
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Dodaj takmicara");

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.show();
    }
    public static void ucitajFormuZaDodavanjeBorbe() throws IOException {
        FXMLLoader loader = new FXMLLoader(FormLoader.class.getResource("/karatesavezrepublikesrpske/gui/fxml/FormaZaDodavanjeBorbe.fxml"));

        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Dodaj takmicara");

        stage.initModality(Modality.APPLICATION_MODAL);

        stage.show();
    }
}
