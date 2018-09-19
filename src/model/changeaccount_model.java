/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import controller.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author Tú Trinh
 */
public class changeaccount_model {
    public void updateUser(String name,String email,String nameold){
        Connect connectDB = new Connect();
        Connection conn = connectDB.getconnect();
        try {
            String sql = "update user set name = ?,email =?  where name =?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, email);
            pst.setString(3,nameold);
            pst.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            //Logger.getLogger(login_model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public ArrayList getdataEmail(){
        String sql = "Select email from user where id != ?";
        ArrayList<String> list = new ArrayList<>();
        try {
            Connect conDB = new Connect();
            Connection  conn = conDB.getconnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, User.id);
            ResultSet rs= pstmt.executeQuery();
            while(rs.next()){
                list.add(rs.getString("email"));
            }
            conn.close();
        } catch (Exception e) {
        }
        return list;
    }
    
    public ArrayList getdataName(){
        String sql = "Select name from user where id !=?";
        ArrayList<String> list = new ArrayList<>();
        try {
            Connect conDB = new Connect();
            Connection  conn = conDB.getconnect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, User.id);
            ResultSet rs= pstmt.executeQuery();
            while(rs.next()){
                list.add(rs.getString("name"));
            }
            conn.close();
        } catch (Exception e) {
        }
        return list;
    }
}
