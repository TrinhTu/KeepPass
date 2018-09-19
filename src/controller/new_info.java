/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.new_windows;
import model.Newinfo_model;
/**
 *
 * @author Tú Trinh
 */
public class new_info implements Initializable{
    
    @FXML
    private JFXSlider length;
    
    @FXML
    private JFXToggleButton lower, upper, number, symbol;
    
    @FXML
    private Label title;

    @FXML
    private TextField tf_title, tf_user, tf_url,  tf_note, tf_pass;
    
    @FXML
    private SplitMenuButton slipt;

    // mặc định là có tất cả các kí tự
    ArrayList<String> charset = new ArrayList<String>(
            Arrays.asList("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!#$@_".split(""))
    );
    
    public void onChange(){
        // Xóa mật khẩu cũ trong trường password
        tf_pass.setText("");
        // Lấy độ dài
        Double len_ = length.getValue();
        int len = len_.intValue();
        // Tạo giá trị mới cho charset
        charset = new ArrayList<String>();
        // Dưới đây chỉ để tạo charset, ko có tạo mật khẩu
        if(lower.isSelected()){
            charset.addAll(
                    Arrays.asList("abcdefghijklmnopqrstuvwxyz".split(""))
            );
        }
        if(upper.isSelected()){
            charset.addAll(
                    Arrays.asList("ABCDEFGHIJKLMNOPQRSTUVWXYZ".split(""))
            );
        }
        
        if(number.isSelected()){
            charset.addAll(
                    Arrays.asList("0123456789".split(""))
            );
        }
        if(symbol.isSelected()){
             charset.addAll(
                     Arrays.asList("!\"#$%&\\'()*+,-./:;<=>?@[\\\\]^_`{|}~".split(""))
             );
        }
        tf_pass.setText(randompasswd(len));
        }
   
    public void reload(){
        onChange();
    }
    
    //get data and import database
    public String text_title, text_url, text_note, text_user, text_pass , group, name;
  
    public void getinfo(){
        text_title = tf_title.getText();
        text_url = tf_url.getText();
        text_note = tf_note.getText();
        text_user = tf_user.getText();
        text_pass = tf_pass.getText();
        group = slipt.getText();
        //encrypt password aes 
        String passencrypt = new Encryptor(User.pass).encrypt(text_pass);
        Newinfo_model setinfo = new Newinfo_model();
        if(text_user.trim().isEmpty() || text_title.trim().isEmpty() || text_pass.trim().isEmpty() ||text_url.trim().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Vui long nhap day du thong tin");
            alert.showAndWait();
        }
        
        else if(setinfo.insert(text_title,text_user,passencrypt, text_url, text_note, group, User.id)){
            Cancel();
        }
    }
    
    //event enter
    public void EnterAction(KeyEvent e){
    if(e.getCode().equals(KeyCode.ENTER))
        getinfo();
    }
    
    //edit data
   public  void Editdata(String title,String user,String pass,String url,String note,String group){
        tf_title.setText(title);
        tf_url.setText(url);
        tf_user.setText(user);
        tf_pass.setText(pass);
        tf_note.setText(note);
        slipt.setText(group);
   }    
    MenuItem a;
    //get database on sliptmenu
    public void GetdataSlipt(){
       ArrayList<String> list = new ArrayList<>();
       Newinfo_model dataSlipt = new Newinfo_model();
       list.addAll(dataSlipt.getdataGroup(User.id));
       String listString;
       slipt.getItems().clear();
        for (String s : list){
            listString = s + "\n";
            MenuItem a = new MenuItem(listString);
            slipt.getItems().addAll(a);
            a.setOnAction((ActionEvent e) -> {
                slipt.setText(s);
            });
        }
    }
    
    //add group
    public void AddGroup(){
        new_windows windows = new new_windows(); //open window add group
        Stage stage = windows.windows("/view/new_group.fxml");
        stage.setOnHidden((WindowEvent event1) -> {
            GetdataSlipt();
        });
    }
    
    //event button cancel
    public void Cancel(){
        Stage stage = (Stage) title.getScene().getWindow();
        stage.close();
        
    }
    
    //random password
    public String randompasswd(int length){
        Random randomizer = new Random();
        String pwd = "";
        for( int i = 0; i < length; i++ ) 
             pwd += charset.get(randomizer.nextInt(charset.size()));
        pwd.join("");
        return pwd;
    }
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // giá trị mặc định là 8
        tf_pass.setText(randompasswd(8));
        GetdataSlipt();
        
    } 
}
