
package controller;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import main.new_windows;
import model.login_model;

public class login_control implements Initializable{
    @FXML
    TextField tf_name, tf_pass;
    
    @FXML
    Button bt_login;
   
   
    public void Login(){
        String text_name, text_pass;
        text_name = tf_name.getText();
        text_pass = tf_pass.getText();
        login_model setlogin = new login_model();
        String newpass = new Encryptor().EncryptPassUser(text_pass);
        if(setlogin.Dangnhap(text_name, newpass)){
            User.name=text_name;
            User.email = setlogin.getEmail(text_name);
            User.id = setlogin.getID(text_name);
            User.pass = text_pass;
            Stage stage = (Stage) tf_name.getScene().getWindow();
            stage.close();
            //mo cua so moi
            new_windows windows = new new_windows();
            windows.windows("/view/home.fxml");
            
        }
        else if(text_name.equals("") && text_pass.equals("")){
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("VUi long nhap thong tin");
            alert.showAndWait();
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Login");
            alert.setContentText("Login failed !!!");
            tf_name.setText("");
            tf_pass.setText("");
            alert.showAndWait();
        }
    }
    
    //event enter
    public void EnterAction(KeyEvent e){
    if(e.getCode().equals(KeyCode.ENTER))
        Login();
    }
    
    //button cancel
    public void Cancel (ActionEvent event){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Login");
        alert.setContentText("Are you sure exit ? ");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
             System.exit(0);
        } 
    }
    
    public void Register(){
        Stage stage = (Stage) tf_name.getScene().getWindow();
        stage.close();
        new_windows windows = new new_windows();
        windows.windows("/view/register.fxml");
       
    }
    
    //getUserName
    
    
    public void Reset(){
        new_windows windows = new new_windows();
        windows.windows("/view/resetpass.fxml");
    }
        
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
