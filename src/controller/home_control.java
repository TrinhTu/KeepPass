package controller;

import model.test;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import main.new_windows;
import model.Newinfo_model;
import model.table_model;
import org.xml.sax.SAXException;


/**
 *
 * @author Tú Trinh
 */
public class home_control implements Initializable {
    
    @FXML
    TableView<table_model> table ;
    @FXML
    MenuButton menubutton;
    @FXML
    JFXListView <String> list;
    
    @FXML
    JFXButton newinfo;
    
    @FXML
    TableColumn<table_model,String> col_title, col_user, col_pass, col_url, col_note,col_group;
    @FXML
    ObservableList<String> items = FXCollections.observableArrayList();
    @FXML
    ObservableList<table_model> oblist = FXCollections.observableArrayList();
    
    
    //when you open window , it will be auto reload 
    public void Openform(){
        new_windows windows = new new_windows();
        Stage stage = windows.windows("/view/new_info.fxml");
        stage.setOnHidden((WindowEvent event1) -> {
            load_data();
            load_listview();
        });
    }
    
    //open window changepass
    public void OpenChangePass(){
        new_windows windows = new new_windows();
        windows.windows("/view/New_Pass.fxml");
    }
    
    //Logout account
    public void logout(){
        Stage stage = (Stage) menubutton.getScene().getWindow();
        stage.close();
        new_windows windows = new new_windows();
        windows.windows("/view/fxlogin.fxml");
        
    }
    
    //open change account
    public void OpenChangeAccount(){
        new_windows windows = new new_windows();
        Stage stage = windows.windows("/view/change_account.fxml");
        stage.setOnHidden((WindowEvent e) -> {
            menubutton.setText(User.name);
            load_listview();
        });
    }
    
    
    //load folder left
    public void load_listview(){
        items = new table_model().loadListview(User.id); 
        list.setItems(items);
        
    }
    
    // function chinh, load function nay dau tien
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       load_data();
       load_listview();
       contextmenu_sidebar();
       contextmenu_main();
       menubutton.setText(User.name);
       table.setEditable(true);
       col_title.setCellFactory(TextFieldTableCell.forTableColumn());
       col_user.setCellFactory(TextFieldTableCell.forTableColumn());
       col_pass.setCellFactory(TextFieldTableCell.forTableColumn());
       col_url.setCellFactory(TextFieldTableCell.forTableColumn());
       col_note.setCellFactory(TextFieldTableCell.forTableColumn());
       col_group.setCellFactory(TextFieldTableCell.forTableColumn());
       
            
       
    }
    
    
    // function load database on tableview
    public void load_data(){
       SetCell();
       col_group.setCellValueFactory(new PropertyValueFactory<>("nameGroup"));
       oblist = new table_model().getTable(User.id);   
       table.setItems(oblist);  
       PressControlC();
    }
    
    //set Cell
    public void SetCell(){
       col_title.setCellValueFactory(new PropertyValueFactory<>("title"));
       col_user.setCellValueFactory(new PropertyValueFactory<>("username"));
       col_pass.setCellValueFactory(new PropertyValueFactory<>("pass"));
       col_url.setCellValueFactory(new PropertyValueFactory<>("url"));
       col_note.setCellValueFactory(new PropertyValueFactory<>("note"));
    }
    
    //fuction press CTRL+C
    // set event for selected row and pressed CTRL + C
    public void PressControlC(){
        table.getSelectionModel().selectedIndexProperty().addListener((num) -> {
           table.setOnKeyPressed(keyevent -> {
                if (keyevent.getCode() == KeyCode.C && keyevent.isShortcutDown()){
                    table_model row = table.getSelectionModel().selectedItemProperty().get();
                    table_model tb = new table_model();
                    String pass = tb.getPass(row.getId());
                    Copy(pass);
                }
            });
       });
    }
    
    
    // Copy str to cliplboard
    public void Copy(String str){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Clipboard clipboard = toolkit.getSystemClipboard();
        StringSelection strSel = new StringSelection(str);
        clipboard.setContents(strSel, null);
    }
    
    //create contextmenu listview
    public void contextmenu_sidebar(){
        list.setCellFactory(lv -> {
            
            ListCell<String> cell = new ListCell<>();
            ContextMenu contextMenu = new ContextMenu();
            MenuItem addItem = new MenuItem();
            
           cell.setOnMousePressed((MouseEvent event) -> {
               String group = cell.itemProperty().getValue();
                if(group == null){
                    event.consume();
                }
                else{
                    SetCell();
                    oblist = new table_model().GroupClick(User.id, group);
                    table.setItems(oblist);  
                    PressControlC();
                }
            });
            
            //item add
            addItem.textProperty().bind(Bindings.format("Add Group", cell.itemProperty()));
            addItem.setOnAction(event -> {
                String item = cell.getItem();
                new_windows windows = new new_windows(); //open window add group
                Stage stage = windows.windows("/view/new_group.fxml");
                stage.setOnHidden((WindowEvent event1) -> {
                    load_listview();
                });
            });
            MenuItem deleteItem = new MenuItem();
            //delete item 
            deleteItem.textProperty().bind(Bindings.format("Delete", cell.itemProperty()));
            deleteItem.setOnAction(event -> {
                    String valuegroup = cell.itemProperty().getValue();
                    list.getItems().remove(cell.getItem());
                    new table_model().DeleteGroup(valuegroup);
                    new table_model().deleterow(valuegroup);
                    load_data();
                    
            }
            );
            contextMenu.getItems().addAll(addItem, deleteItem);
            cell.textProperty().bind(cell.itemProperty());
            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });
            return cell ;
        });
    }
    
    String newtitle,newuser,newpass,newurl,newnote,newgroup;
    int id;
    //function edit title
    public void changeTitleevent(CellEditEvent edit){
        table_model tableselect = table.getSelectionModel().getSelectedItem();
        tableselect.setTitle(edit.getNewValue().toString());
        newtitle = tableselect.getTitle();
        id= tableselect.getId();
        new table_model().UpdateTitle(newtitle,id);
    }
    //edit user
    public void changeUserevent(CellEditEvent edit){
        table_model tableselect = table.getSelectionModel().getSelectedItem();
        tableselect.setUsername(edit.getNewValue().toString());
        newuser = tableselect.getUsername();
        id= tableselect.getId();
        new table_model().UpdateUser(newuser, id);
    }
    //edit pass
    public void changePassevent(CellEditEvent edit){
        table_model tableselect = table.getSelectionModel().getSelectedItem();
        tableselect.setPass(edit.getNewValue().toString());
        newpass = tableselect.getPass();
        String encryptpass = new Encryptor(User.pass).encrypt(newpass);
        id= tableselect.getId();
        new table_model().UpdatePass(encryptpass,id);
        load_data();
    }
    //edit url
    public void changeUrlevent(CellEditEvent edit){
        table_model tableselect = table.getSelectionModel().getSelectedItem();
        tableselect.setUrl(edit.getNewValue().toString());
        newurl = tableselect.getUrl();
        id= tableselect.getId();
        new table_model().UpdateUrl(newurl,id);
    }
    //edit note
    public void changeNoteevent(CellEditEvent edit){
        table_model tableselect = table.getSelectionModel().getSelectedItem();
        tableselect.setNote(edit.getNewValue().toString());
        newnote = tableselect.getNote();
        id= tableselect.getId();
        new table_model().UpdateNote(newnote, id);
    }
    
    //compare string to arraylist
    public boolean containsString(String testString, ArrayList<String> list) 
   {
        return list.contains(testString);
   }
    
    //change group
    public void changeGroupEvent(CellEditEvent edit){
        table_model tableselect = table.getSelectionModel().getSelectedItem();
        tableselect.setNameGroup(edit.getNewValue().toString());
        newgroup = tableselect.getNameGroup();
        id= tableselect.getId();
        ArrayList<String> list = new ArrayList<>();
        Newinfo_model dataSlipt = new Newinfo_model();
        list.addAll(dataSlipt.getdataGroup(User.id));
        if(containsString(newgroup, list)){
            new table_model().UpdateGroup(newgroup, id);
        }
        else{
            new table_model().NewGroup(newgroup, User.id);
            new table_model().UpdateGroup(newgroup, id);
            load_listview();
        }
    }
    
    //export file
    public void export() throws ParserConfigurationException, TransformerException{
        FileChooser chooser  = new FileChooser();
        chooser.getExtensionFilters().addAll(new ExtensionFilter ("XML file","*.xml"));
        File selectedfile = chooser.showSaveDialog(null);
        if (selectedfile != null){
            String filename = selectedfile.getAbsolutePath();
            ArrayList<ArrayList<String>> data = new test().ExportData();
            new test().export_db(data, filename);
        }   
    }
    
    //import file
    public void import_file() throws ParserConfigurationException, SAXException, IOException{
        FileChooser chooser  = new FileChooser();
        chooser.getExtensionFilters().addAll(new ExtensionFilter ("XML file","*.xml"));
        File selectedfile = chooser.showOpenDialog(null);
        if (selectedfile != null){
            String filename = selectedfile.getAbsolutePath();
            ArrayList<ArrayList> data = new test().import_db(filename);
            try {
                new test().Insert(data);
                load_data();
            } catch (SQLException ex) {
                Logger.getLogger(home_control.class.getName()).log(Level.SEVERE, null, ex);
            }
        }   
    }
    
    //when click table row select menuitem
    public void contextmenu_main(){
        table.setRowFactory(
            new Callback<TableView<table_model>, TableRow<table_model>>() {
                @Override
                public TableRow<table_model> call(TableView<table_model> table_temp) {
                    final TableRow<table_model> row = new TableRow<>();
                    final ContextMenu rowMenu = new ContextMenu();
                    MenuItem openItem = new MenuItem("Open Web Browser");
                    openItem.setOnAction(e ->{
                        String url = row.getItem().getUrl();
                        Desktop d  = Desktop.getDesktop();
                        try {
                            d.browse(new URI(url));
                        } catch (URISyntaxException | IOException ex) {
                            Alert alert = new Alert(Alert.AlertType.WARNING);
                            alert.setContentText("Can't open");
                            alert.showAndWait();
                        }
                    });
                    MenuItem deleteItem = new MenuItem("Delete");
                    deleteItem.setOnAction(e -> {
                        // hidden item on table view
                        table.getItems().remove(row.getItem());
                        // delete item in database
                        String nameGroup = row.getItem().getNameGroup();
                        new table_model().deleterow(nameGroup);
                    });
                    
                    MenuItem copyItem = new MenuItem("Copy Password CTRL+C");
                    copyItem.setOnAction(e->{
                        table_model tb = new table_model();
                        int id= row.getItem().getId();
                        String pass = tb.getPass(id);
                        Copy(pass);
                    });
                    
                    // Add all items into context menu
                    rowMenu.getItems().addAll(openItem,copyItem,deleteItem);
                    // only display context menu for non-null items:
                    row.contextMenuProperty().bind(
                        Bindings.when(Bindings.isNotNull(row.itemProperty()))
                        .then(rowMenu)
                        .otherwise((ContextMenu)null)
                    );
                    return row;
                };
            }
        );
    }
   }
