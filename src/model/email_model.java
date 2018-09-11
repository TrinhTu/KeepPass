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
public class email_model {
    Connect conDB = new Connect();
    Connection  conn = conDB.getconnect();
    PreparedStatement pstmt ;
    ResultSet rs;
    public String Email(String email){
        String sql = "Select name from user where email = ?";
        String username = "";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, email);
            rs = pstmt.executeQuery();
            while (rs.next()) {                
                username = rs.getString(1);
            }
            return username;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void Updatepass(String pass,String email){
        String sql = "update user set pass = ? where email = ?";
        String passupdate = "";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pass);
            pstmt.setString(2, email);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
