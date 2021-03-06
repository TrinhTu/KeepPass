/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Tú Trinh
 */
public class login_model {
    
    public boolean Dangnhap(String user, String pass){
        try {
            Connect connectDB = new Connect();
            Connection conn = connectDB.getconnect();
            String sql = "select name,pass from user where name = ? and pass =? ";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user);
            pst.setString(2, pass);
            ResultSet rs= pst.executeQuery();
            while(rs.next()){
                return true;
            }
            conn.close();
        } catch (SQLException ex) {
             Logger.getLogger(login_model.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
        } 
    
    public String getEmail(String user){
        Connect connectDB = new Connect();
        Connection conn = connectDB.getconnect();
        try {
            String sql = "select * from user where name = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user);
            ResultSet rs = pst.executeQuery();
            String email = rs.getString("email");
            conn.close();
            return email;
        } catch (SQLException ex) {
            Logger.getLogger(login_model.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    public int getID(String user){
        Connect connectDB = new Connect();
        Connection conn = connectDB.getconnect();
        try {
            String sql = "select * from user where name = ?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user);
            ResultSet rs = pst.executeQuery();
            String a =  rs.getString("id");
            int id = Integer.parseInt(a);
            conn.close();
            return id;
            
        } catch (SQLException ex) {
            Logger.getLogger(login_model.class.getName()).log(Level.SEVERE, null, ex);
            return 0;
        }
    }
}
