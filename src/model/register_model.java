
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javafx.scene.control.Alert;

/**
 *
 * @author Tú Trinh
 */
public class register_model {
   public boolean insert(String name, String email, String pass) {
        String sql = "INSERT INTO user(name,email,pass) VALUES(?,?,?)";
        
        try{
            Connect conDB = new Connect();
            Connection  conn = conDB.getconnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setString(3, pass);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("the username exists or somethings");
            alert.showAndWait();
            System.out.println(e.getMessage());
            return false;
        }
    }
 }