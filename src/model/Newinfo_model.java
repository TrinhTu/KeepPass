package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Tú Trinh
 */
public class Newinfo_model {
    PreparedStatement pstmt;
    Connect conDB = new Connect();
    ResultSet rs;
            
    //insert data into newInfo
    public boolean insert(String title,String username,String pass, String url, String note, String groupname, int userID) {
        Integer groupid = getidgroup(groupname);
        String sql = "INSERT INTO new_info(title,username,pass,url,note,groupID,userID) VALUES(?,?,?,?,?,?,?)";
        
        try{
                Connection  conn = conDB.getconnect();

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, username);
            pstmt.setString(3, pass);
            pstmt.setString(4, url);
            pstmt.setString(5, note);
            pstmt.setInt(6, groupid);
            pstmt.setInt(7, userID);
            pstmt.executeUpdate();
            conn.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    
    //hien thi menuItem on slipt
   public ArrayList getdataGroup(int userid){
        String sql = "Select * from group_accounts where userID = ?";
        ArrayList<String> list = new ArrayList<>();
        try {
                Connection  conn = conDB.getconnect();

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, userid);
            rs= pstmt.executeQuery();
            while(rs.next()){
                list.add(rs.getString("nameGroup"));
            }
            conn.close();
        } catch (Exception e) {
        }
        return list;
   }
   
   public int getidgroup(String groupname){
        String sql = "Select id from group_accounts where nameGroup = ?";
        ArrayList<String> list = new ArrayList<>();
        try {
                Connection  conn = conDB.getconnect();

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, groupname);
            rs= pstmt.executeQuery();
            rs.next();        
            int id = rs.getInt("id");
            conn.close();
            return id;
        } catch (Exception e) {
        }
        return 0;
   }
   
   public String getnamegroup(int groupid){
        String sql = "Select nameGroup from group_accounts where id = ?";
        try {
                Connection  conn = conDB.getconnect();

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, groupid);
            rs= pstmt.executeQuery();
            rs.next();    
            String name = rs.getString("nameGroup");
            conn.close();
            return name;
        } catch (Exception e) {
        }
        return null;
   }
    
    
}
