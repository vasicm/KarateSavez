/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package karatesavezrepublikesrpske;

import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import karatesavezrepublikesrpske.data.dao.DAOFactory;
import karatesavezrepublikesrpske.data.dao.OglasDAO;
import karatesavezrepublikesrpske.data.dto.OglasDTO;
import karatesavezrepublikesrpske.data.dto.TakmicarAnalizaDTO;
import karatesavezrepublikesrpske.util.KSRSUtilities;

/**
 *
 * @author Marko
 */
public class KarateSavezRepublikeSrpske extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("gui/fxml/MainFrame.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add(KarateSavezRepublikeSrpske.class.getResource("gui/css/mainframe.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
