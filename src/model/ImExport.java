package model;

import controller.Encryptor;
import controller.User;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Connect;

public class ImExport {
    PreparedStatement pstmt;
    Connect conDB = new Connect();
    Connection  conn = conDB.getconnect();
    public boolean exportfile(String filename) {
        try {
            ResultSet rs;
            FileWriter fw = new FileWriter(filename);
            String sql = "select * from new_info";
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                fw.append(rs.getString(1));
                fw.append(',');
                fw.append(rs.getString(2));
                fw.append(',');
                fw.append(rs.getString(3));
                fw.append(',');
                String pass = new Encryptor(User.pass).decrypt(rs.getString(4));
                fw.append(pass);
                fw.append(',');
                fw.append(rs.getString(5));
                fw.append(',');
                fw.append(rs.getString(6));
                fw.append(',');
                fw.append(rs.getString(7));
                fw.append(',');
                fw.append(rs.getString(8));
                fw.append('\n');
               }
            fw.flush();
            fw.close();
            conn.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean importfile(String filename)
    {
//        String query;
//        Statement stmt;
//        try
//        {
//            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
//           // query = "LOAD DATA INFILE '"+filename+"' INTO TABLE new_info  FIELDS TERMINATED BY ',' (id,title,username,pass,url,note,nameGroup,name)";
//            query = "LOAD DATA INFILE '" + filename + "' INTO TABLE new_info FIELDS TERMINATED BY ','(id,title,username,pass,url,note,nameGroup,name)";
//            stmt.executeUpdate(query);
//        }
//        catch(Exception e) {
//          e.printStackTrace(); }
//        
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while((line = br.readLine())!=null){
                String[] value =  line.split(",");
                String sql = "insert into new_info (id,title,username,pass,url,note,nameGroup,name)" + "values('"+value[0]+"', '"+value[1]+"', '"+value[2]+"', '"+value[3]+"', '"+value[4]+"', '"+value[5]+"', '"+value[6]+"', '"+value[7]+"')";
                pstmt = conn.prepareStatement(sql);
                pstmt.executeUpdate();
            }
            return true;
        } catch (Exception e) {
            //e.printStackTrace();
            return false;
        }
}
}