/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import model.changeaccount_model;

/**
 *
 * @author Tú Trinh
 */
public class changeaccount implements Initializable{
    
    @FXML 
    JFXTextField tf_user, tf_email;
    
    @FXML
    Button cancel,save;
    
    //event button save
    public String text_user,text_email;
    public void ChangeAccount(){
        text_user = tf_user.getText();
        text_email = tf_email.getText();
        changeaccount_model change_model = new changeaccount_model();
        if(change_model.updateUser(text_user, text_email, User.name)){
            User.name = text_user;
            User.email = text_email;
            Stage stage = (Stage) tf_user.getScene().getWindow();
            stage.close();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Failed");
            alert.showAndWait();
        }
    }
    
    //button cancel
    public void Cancel(){
        Stage stage = (Stage) tf_user.getScene().getWindow();
        stage.close();
    }
    
   
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tf_user.setText(User.name);
        tf_email.setText(User.email);
    }
    
}
