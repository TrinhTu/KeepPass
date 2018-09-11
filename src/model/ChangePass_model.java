/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Tú Trinh
 */
public class ChangePass_model {
    Connect conDB = new Connect();
    PreparedStatement pstmt ;
    ResultSet rs;
    public String getPass(String name){
        String sql = "Select pass from user where name= ?";
        String pass = null;
        try {
            Connection  conn = conDB.getconnect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            while (rs.next()) {                
                pass = rs.getString("pass");
            }
            conn.close();
            return pass;
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void UpdatePass(String newpass, String username){
        String sql = "update user set pass = ? where name = ?";
        try {
            Connection  conn = conDB.getconnect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newpass);
            pstmt.setString(2, username);
            pstmt.executeUpdate();
            conn.close();
        } 
        
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
