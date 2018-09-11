/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Tú Trinh
 */
public class new_windows {
     public Stage windows(String a){
        try {
            Parent root = FXMLLoader.load(getClass().getResource(a));
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            return stage;
        } catch (IOException ex) {
            Logger.getLogger(new_windows.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return null;
     }
}

