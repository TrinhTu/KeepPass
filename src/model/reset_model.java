/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.Encryptor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Tú Trinh
 */
public class reset_model {
    
    PreparedStatement pstmt ;
    
    public String Email(String name){
        String sql = "Select email from user where name = ?";
        String email = "";
        try {
            Connect conDB = new Connect();
            Connection  conn = conDB.getconnect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {                
                email = rs.getString("email");
            }
            conn.close();
            return email;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    //get userID
    public int getUserId(String name){
        String sql = "Select id from user where name = ?";
        int id = 0 ;
        try {
            Connect conDB = new Connect();
            Connection  conn = conDB.getconnect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {                
                id = rs.getInt("id");
            }
            conn.close();
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public String getPass(String name){
        String sql = "select pass from user where name = ?";
        String pass;
        try {
            Connect conDB = new Connect();
            Connection  conn = conDB.getconnect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();
            pass = rs.getString("pass");
            conn.close();
            return pass;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public void Updatepass(String pass,String name){
        String sql = "update user set pass = ? where name = ?";
        try {
            Connect conDB = new Connect();
            Connection  conn = conDB.getconnect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, pass);
            pstmt.setString(2, name);
            pstmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
}
