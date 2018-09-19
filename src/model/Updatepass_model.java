/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.Encryptor;
import controller.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Tú Trinh
 */
public class Updatepass_model {
    public ArrayList<ArrayList<String>> getPass(){
        Connect conDB = new Connect();
       
        ArrayList <ArrayList<String>> row = new ArrayList<ArrayList<String>>();
        try {
            ArrayList<String> item;
            String sql = "Select id, pass from new_info where userID = ?";
            Connection  conn = conDB.getconnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            System.out.print(User.id);
            pstmt.setInt(1, User.id);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                item = new ArrayList<String>();
                item.add(String.valueOf(rs.getInt("id")));
                item.add(new Encryptor(User.pass).decrypt(rs.getString("pass")));
                row.add(item);
            }
            conn.close();
            return row;
        } catch (Exception e) {
            return null;
        }
    }
    
    public void UpdatePass_newinfo(String id, String pass){
       Connect conDB = new Connect();
       String sql = "update new_info set pass =? where id = ?";
       try {
           Connection  conn = conDB.getconnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,pass);
            pstmt.setString(2, id);
            pstmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
   }
    
}
