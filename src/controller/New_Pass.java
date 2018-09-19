/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.ChangePass_model;
import model.Updatepass_model;

/**
 *
 * @author Tú Trinh
 */
public class New_Pass implements Initializable{
   
    @FXML
    TextField tf_user;
    @FXML
    PasswordField tf_oldpass, tf_newpass, tf_conpass;
    public String text_newpass, text_conpass, text_oldpass;
    public  void getChange(){
        
        text_oldpass = tf_oldpass.getText();
        text_newpass = tf_newpass.getText();
        text_conpass = tf_conpass.getText();
        String newpass = new Encryptor().EncryptPassUser(text_oldpass);
        String passold = new ChangePass_model().getPass(User.name);
        if(newpass.equals(passold) &&  text_newpass.equals(text_conpass)){
            Updatepass_model update = new Updatepass_model();
            String en_pass = new Encryptor().EncryptPassUser(text_newpass);
            ArrayList<ArrayList<String>> rows = update.getPass();
            
            User.pass = text_newpass;
            for(ArrayList<String> row : rows){
               update.UpdatePass_newinfo(
                       row.get(0),
                       new Encryptor(User.pass).encrypt(row.get(1))
               );
            }
            new ChangePass_model().UpdatePass(en_pass, User.name); //update pass
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Successfull !!!");
            alert.showAndWait();
            Stage stage = (Stage) tf_user.getScene().getWindow();
            stage.close();
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Failed !!! ");
            alert.showAndWait();
            tf_oldpass.setText("");
            tf_newpass.setText("");
            tf_conpass.setText("");
        }
        
    }
    //event enter
    public void EnterAction(KeyEvent e){
    if(e.getCode().equals(KeyCode.ENTER))
        getChange();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tf_user.setText(User.name);
    }
}
