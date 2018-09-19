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
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import model.Newinfo_model;
import model.table_model;

/**
 *
 * @author Tú Trinh
 */
public class newgroup_control implements Initializable{
    
    @FXML
    TextField tf_group;
    public void Save(){
        String group = tf_group.getText();
        table_model tb = new table_model();
        ArrayList<String> list = new ArrayList<>();
        Newinfo_model dataSlipt = new Newinfo_model();
        list.addAll(dataSlipt.getdataGroup(User.id));
        if(!list.contains(group) && !group.trim().isEmpty()){
            tb.NewGroup(group, User.id);
        }
        
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Group already exists");
            alert.showAndWait();
        }
        Stage stage = (Stage) tf_group.getScene().getWindow();
        stage.close();
    }
    
    public void EnterAction(KeyEvent e){
    if(e.getCode().equals(KeyCode.ENTER))
        Save();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
}
