
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.new_windows;
import model.register_model;

/**
 *
 * @author Tú Trinh
 */
public class Signup implements Initializable{
    
    @FXML
    TextField name, email, pass , con_pass;
    
    
    @FXML
    public void signup(){
        String text_name = name.getText();
        String text_email = email.getText();
        String text_pass = pass.getText();
        String text_conpass = con_pass.getText();
        
        register_model rgm = new register_model();
        
        //sha 256
        String new_pass = new sha256().getSHA256(text_pass);
        //cac truong khong dc null
        if (text_name.trim().equals("") || text_email.trim().equals("") || text_pass.trim().equals("") || text_conpass.trim().equals("")){
            Alert fail= new Alert(AlertType.INFORMATION);
            fail.setHeaderText("Not null");
            fail.setContentText("Dien day du thong tin");
            fail.showAndWait();
        }
        
        //kiem tra username
        else if(checkuser(text_name)==false){
            Alert fail= new Alert(AlertType.WARNING);
            fail.setHeaderText("Fail");
            fail.setContentText("Ten dang nhap khong hop le");
            fail.showAndWait();
            
        }
        
        //kiem tra email hop le
        else if(checkmail(text_email)==false){
            Alert fail= new Alert(AlertType.WARNING);
            fail.setHeaderText("Fail");
            fail.setContentText("Nhap lai email");
            fail.showAndWait();
        }
        
        //so sanh password
        else if(!text_pass.equals(text_conpass)){
            Alert fail= new Alert(AlertType.INFORMATION);
            fail.setHeaderText("Fail");
            fail.setContentText("Mat khau khong trung , vui long nhap lai");
            fail.showAndWait();
            
        }
        
        // dang ki
        else if(rgm.insert(text_name, text_email, new_pass)){
            Alert fail= new Alert(AlertType.INFORMATION);
            fail.setHeaderText("Success full");
            fail.setContentText("Dang ki thanh cong");
            fail.showAndWait();
            
            Stage stage = (Stage) name.getScene().getWindow();
            stage.close();
            //mo cua so moi
            new_windows windows = new new_windows();
            windows.windows("/view/fxlogin.fxml");
            
        }
       
    }
    
    @FXML
    public void Back(){
        Stage stage = (Stage) name.getScene().getWindow();
        stage.close();
        //mo cua so moi
        new_windows windows = new new_windows();
        windows.windows("/view/fxlogin.fxml");
        
    }
    
    
    //kiem tra tinh hop le cua email
    public  boolean checkmail(String emailStr) {
        String value = "^[A-Za-z0-9]+@[A-Za-z0-9]+\\.[A-Za-z0-9]{2,6}$";
        Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile(value, Pattern.CASE_INSENSITIVE);
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(emailStr);
        return matcher.find();
    }
   
    //kiem tra tinh hop le cua username
    
    public boolean  checkuser(String userstr){
        String user_value = "[a-zA-Z0-9.\\\\-_]{3,10}";
        Pattern username = Pattern.compile(user_value, Pattern.CASE_INSENSITIVE);
        Matcher matcher = username.matcher(userstr);
        return  matcher.find();
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
}
