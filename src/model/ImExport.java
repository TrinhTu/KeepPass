package model;
import controller.Encryptor;
import controller.User;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ImExport {  
    
    public  ArrayList<ArrayList<String>> ExportData(){
    	ArrayList<ArrayList<String>>  data = new ArrayList<>();
    	ArrayList<String> rows;
    	
        try {
            Connect conDB = new Connect();
            Connection  conn = conDB.getconnect();
            String sql = "select title,username,pass,url,note, group_accounts.nameGroup from new_info, group_accounts where new_info.groupID = group_accounts.id and new_info.userID = ?";
            PreparedStatement pstmt  = conn.prepareStatement(sql);  
            pstmt.setInt(1, User.id);
            ResultSet rs = pstmt.executeQuery();
              
            while (rs.next()) {  
                rows = new ArrayList<>();
                rows.add(rs.getString("title"));
                rows.add(rs.getString("username"));
                rows.add(new Encryptor(User.pass).decrypt(rs.getString("pass")));
                rows.add(rs.getString("url"));
                rows.add(rs.getString("note"));
                rows.add(rs.getString("nameGroup"));
                data.add(rows);
            }
            conn.close();
            return data;
            
        } catch (SQLException e) {  
            System.out.println(e.getMessage());  
        }  
       return null;
    }
    public  void export_db(ArrayList<ArrayList<String>> data, String filename) throws ParserConfigurationException, TransformerException{
    	Element  root, entry, title, username, pass, url, note,group;
    	
    	DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		
		// root elements
		Document doc = docBuilder.newDocument();
		root = (Element) doc.createElement("entries");
		doc.appendChild((Node) root);
		
		for (ArrayList<String> row : data) {
			entry = (Element) doc.createElement("entry");
			
			title = doc.createElement("title");
			username =  doc.createElement("username");
			pass = doc.createElement("pass");
			url =  doc.createElement("url");
			note =  doc.createElement("note");
                        group = doc.createElement("group");

			title.appendChild(doc.createTextNode(row.get(0)));
			username.appendChild(doc.createTextNode(row.get(1)));
			pass.appendChild(doc.createTextNode(row.get(2)));
			url.appendChild(doc.createTextNode(row.get(3)));
			note.appendChild(doc.createTextNode(row.get(4)));
                        group.appendChild(doc.createTextNode(row.get(5)));
			
			entry.appendChild(title);
			entry.appendChild(username);
			entry.appendChild(pass);
			entry.appendChild(url);
			entry.appendChild(note);
                        entry.appendChild(group);
			
			root.appendChild((Node) entry);		
			
			
		}
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File(filename));
                transformer.transform(source, result);
			
    }

    public  ArrayList<ArrayList> import_db(String filename) throws ParserConfigurationException, SAXException, IOException{
    	ArrayList<ArrayList> data = new ArrayList();
    	ArrayList<String> row;
    	File xmlfile = new File(filename);
    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    	Document doc = dBuilder.parse(xmlfile);
    	doc.getDocumentElement().normalize();
    	
    	NodeList entries = doc.getElementsByTagName("entry");
    	for (int temp = 0; temp < entries.getLength(); temp++) {
    		Node entry = entries.item(temp);
    				
    		if (entry.getNodeType() == Node.ELEMENT_NODE) {
    			Element eElement = (Element) entry;
    			row = new ArrayList();
    			row.add(eElement.getElementsByTagName("title").item(0).getTextContent());
    			row.add(eElement.getElementsByTagName("username").item(0).getTextContent());
    			row.add(new Encryptor(User.pass).encrypt(
                                eElement.getElementsByTagName("pass").item(0).getTextContent()
                        ));
    			row.add(eElement.getElementsByTagName("url").item(0).getTextContent());
    			row.add(eElement.getElementsByTagName("note").item(0).getTextContent());
                        row.add(eElement.getElementsByTagName("group").item(0).getTextContent());
    			data.add(row);
    		}
    	}
    	return data;
    }
    
    public void Insert(ArrayList<ArrayList> data) throws SQLException{
            int groupid;
            Connect conDB = new Connect();
            Connection  conn = conDB.getconnect();
            String sql = "Insert into new_info (title, username, pass,url,note,groupID,userID) values(?,?,?,?,?,?,?) ";
            PreparedStatement pstmt  = conn.prepareStatement(sql);
            ArrayList<String> list = new ArrayList<>();
            Newinfo_model dataSlipt = new Newinfo_model();
            list.addAll(dataSlipt.getdataGroup(User.id));
            for (ArrayList row : data) {
                if (!list.contains(row.get(5))){
                     new table_model().NewGroup((String) row.get(5), User.id);
                }
                groupid = new Newinfo_model().getidgroup((String) row.get(5));
                ///
                pstmt.setString(1, (String) row.get(0));
                pstmt.setString(2, (String) row.get(1));
                pstmt.setString(3, (String) row.get(2));
                pstmt.setString(4, (String) row.get(3));
                pstmt.setString(5, (String) row.get(4));
                pstmt.setInt(6, groupid);
                pstmt.setInt(7,  User.id);
                pstmt.executeUpdate();
            }
            conn.close();
    }
}