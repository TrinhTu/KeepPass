/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXTextField;
import java.net.URL;
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
import model.email_model;

/**
 *
 * @author Tú Trinh
 */
public class SendMail implements Initializable{
    @FXML
    JFXTextField tf_email;
    Message message ;

    public void sendmail(){
                String username = "rinchan1503@gmail.com"; // enter your mail id
		String password = "letutrinh1503";// enter ur password
                String emailto = tf_email.getText();

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
                
                //lay username
                email_model em = new email_model();
                String user = em.Email(emailto);
                
                //random pass
                new_info nwi = new new_info();
                String pass = nwi.randompasswd(8);
                
                //update pass
                String newpass = new sha256().getSHA256(pass);
                em.Updatepass(newpass, emailto);

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
			message.setText("Keep Pass"
				+ "\n\n User: " + user+ " yeu cau dat lai mat khau. " + "\n\n Mat khau moi la: " + pass);
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
                        Stage stage = (Stage) tf_email.getScene().getWindow();
                        stage.close();

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
}
