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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



/**
 *
 * @author Tú Trinh
 */
public class table_model {

    String title, username, pass, url, note, nameGroup, name;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public String getName(){
        return nameGroup;
    }
    public void setName(String name){
        this.name = name;
    }
    
    public String getNameGroup(){
        return nameGroup;
    }
    public void setNameGroup(String nameGroup){
        this.nameGroup = nameGroup;
    }
    public table_model(int id, String title, String username, String pass, String url, String note,String nameGroup, String name){
        this.id = id;
        this.title = title;
        this.username = username;
        this.pass = pass;
        this.url = url;
        this.note = note;
        this.nameGroup = nameGroup;
        this.name = name;
    }
    
    public table_model(){
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    
    Connect conDB = new Connect();
    PreparedStatement pstmt;
    // select all database on table view 
    public ObservableList<table_model> getTable(int userid){
        String sql = "SELECT * FROM new_info where userID = ? ";
        ObservableList<table_model> oblist = FXCollections.observableArrayList();
        String newpass = null;
        String groupname = "";
        try{
          Connection  conn = conDB.getconnect();
          pstmt = conn.prepareStatement(sql);
          pstmt.setInt(1, userid);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                ArrayList<String> passchange = new ArrayList<>();
                passchange.add(rs.getString(4));
                    for(String s:passchange){
                       String decryptpass = new Encryptor(User.pass).decrypt(s);
                       newpass = decryptpass.replaceAll(".", "*");
                       groupname = new Newinfo_model().getnamegroup(rs.getInt("groupID"));
                       oblist.add(new table_model(rs.getInt("id"), rs.getString("title"),rs.getString("username"),newpass, rs.getString("url"), rs.getString("note"),groupname,rs.getString("userID")));
                    }
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return oblist;
    }
    
    //update database
   public void UpdateTitle(String title,int id){
       String sql = "update new_info set title = ?  where id = ?";
       try {
            Connection  conn = conDB.getconnect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,title);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
   }
   
   public void UpdateUser(String user,int id){
       String sql = "update new_info set username = ?  where id = ?";
       try {
            Connection  conn = conDB.getconnect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,user);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
   }
   
   public void UpdatePass(String pass,int id){
       String sql = "update new_info set pass = ?  where id = ?";
       try {
           Connection  conn = conDB.getconnect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,pass);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
   }
   
   public void UpdateUrl(String url,int id){
       String sql = "update new_info set url = ?  where id = ?";
       try {
           Connection  conn = conDB.getconnect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,url);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
   }
   
   public void UpdateNote(String note,int id){
       String sql = "update new_info set note = ?  where id = ?";
       try {
           Connection  conn = conDB.getconnect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,note);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
   }
   //update table new_info
   public void UpdateGroup(String group,int id){
       int groupid=new Newinfo_model().getidgroup(group);
       String sql = "update new_info set groupID= ?  where id = ?";
       try {
            Connection  conn = conDB.getconnect();
            pstmt = conn.prepareStatement(sql);
            System.out.println(groupid);
            pstmt.setInt(1,groupid);
            pstmt.setInt(2,id);
            pstmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
   }
   
  
    
    //select pass
    public String getPass(int id){
        String sql = "select * from new_info where id = ?";
        String getpass= "";
        String decryptpass = "";
        try {
            Connection  conn = conDB.getconnect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {                
               getpass = rs.getString(4);
               decryptpass = new Encryptor(User.pass).decrypt(getpass);
            }
            conn.close();
            return decryptpass;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    //select database on listview
    public ObservableList loadListview(int userid){
        String sql = "Select * from group_accounts where userID =? ";
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            Connection  conn = conDB.getconnect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userid);
            ResultSet rs= pstmt.executeQuery();
            while(rs.next()){
                list.add(rs.getString("nameGroup"));
            }
            conn.close();
        } catch (Exception e) {
        }
        return list;
    }
    
    //delete row in tableview
    public void deleterow(int id){
//        int groupid = new Newinfo_model().getidgroup(groupname);
        String sql = "delete from new_info where id = ?";
        try {
            Connection  conn = conDB.getconnect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            conn.close();
        } catch (Exception ex) {
            Logger.getLogger(table_model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //insert database new group listview
    public void NewGroup(String group,int userID){
        String sql = "insert into group_accounts(nameGroup,userID) values(?,?)";
        try {
            Connection  conn = conDB.getconnect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, group);
            pstmt.setInt(2, userID);
            pstmt.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            //Logger.getLogger(table_model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //delete row group listview
    public void DeleteGroup(String namegroup){
        String sql = "delete from group_accounts where nameGroup = ?";
        try {
            Connection  conn = conDB.getconnect();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, namegroup);
            pstmt.executeUpdate();
            conn.close();
        } catch (Exception ex) {
             Logger.getLogger(table_model.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //select new_info where namegroup when click group listview
     public ObservableList<table_model> GroupClick(int userid,String namegroup){
        int groupid = new Newinfo_model().getidgroup(namegroup);
        String sql = "SELECT * FROM new_info where userID = ? and groupID= ? ";
        ObservableList<table_model> oblist = FXCollections.observableArrayList();
        String newpass = null;
        String groupname = "";
        try{
            Connection  conn = conDB.getconnect();
          pstmt = conn.prepareStatement(sql);
          pstmt.setInt(1, userid);
          pstmt.setInt(2, groupid);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                ArrayList<String> passchange = new ArrayList<>();
                passchange.add(rs.getString(4));
                    for(String s:passchange){
                       String decryptpass = new Encryptor(User.pass).decrypt(s);
                       newpass = decryptpass.replaceAll(".", "*");
                       groupname = new Newinfo_model().getnamegroup(rs.getInt("groupID"));
                       oblist.add(new table_model(rs.getInt("id"), rs.getString("title"),rs.getString("username"),newpass, rs.getString("url"), rs.getString("note"),groupname,rs.getString("userID")));
                    }
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return oblist;
    }
   
}
    

