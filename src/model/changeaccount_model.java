/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tú Trinh
 */
public class changeaccount_model {
    public boolean updateUser(String name,String email,String nameold){
        Connect connectDB = new Connect();
        Connection conn = connectDB.getconnect();
        try {
            String sql = "update user set name = ?,email =?  where name =?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3,nameold);
            pst.executeUpdate();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(login_model.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
}
