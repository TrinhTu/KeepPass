/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.Updatepass_model;
import model.reset_model;

/**
 *
 * @author Tú Trinh
 */
public class SendMail implements Initializable{
    @FXML
    JFXTextField tf_name;
    Message message ;

    public void sendmail(){
                String username = "rinchan1503@gmail.com"; // enter your mail id
		String password = "letutrinh1503";// enter ur password
                String name = tf_name.getText();

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
                
                //lay email
                reset_model reset = new reset_model();
                String emailto = reset.Email(name);
                
                
                //random pass
                new_info nwi = new new_info();
                String pass = nwi.randompasswd(8);
                
                //update pass
                String newpass = new Encryptor().EncryptPassUser(pass);
                
                
                User.pass = new Encryptor().DecryptPassUser(reset.getPass(name)); // lấy pass cũ
                User.id = reset.getUserId(name);
                Updatepass_model update = new Updatepass_model();
                ArrayList<ArrayList<String>> rows = update.getPass();
                
                User.pass = pass;
                for(ArrayList<String> row : rows){
                   update.UpdatePass_newinfo(
                           row.get(0),
                           new Encryptor(User.pass).encrypt(row.get(1))
                   );
                }
                
                reset.Updatepass(newpass, name);
                
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });
		try {
			message = new MimeMessage(session);
			message.setFrom(new InternetAddress("rinchan1503@gmail.com")); // same email id
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(emailto));// whome u have to send mails that person id
			message.setSubject("Reset Password");
			message.setText("Keep Pass - Reset Password"
				+ "\n\n User: " + name+ "\n\n Mat khau moi la: " + pass);
			new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Transport.send(message);
                                } catch (MessagingException ex) {
                                    Logger.getLogger(SendMail.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        }).start();
                        Stage stage = (Stage) tf_name.getScene().getWindow();
                        stage.close();

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
}
