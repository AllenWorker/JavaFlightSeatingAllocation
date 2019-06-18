/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightseating;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import flightseating.Customer;
import java.io.RandomAccessFile;


/**
 *
 * @author Trelia
 */
public class FXMLDocumentController implements Initializable {
    
  
    @FXML
    private TextField txtName;
    @FXML
    private ComboBox<String> AgeComboBox;
    @FXML
    private ComboBox<String> ClassComboBox;
    @FXML
    private ComboBox<String> SeatTypeCombo;
    @FXML
    private TableView<Seating> FlightSeatsGrid;
    @FXML
    private TableColumn<Seating, String> A;
    @FXML
    private TableColumn<Seating, String> B;
    @FXML
    private TableColumn<Seating, String> C;
    @FXML
    private TableColumn<Seating, String> D;
    @FXML
    private TableColumn<Seating, String> E;
    @FXML
    private TableColumn<Seating, String> F;
    @FXML
    private TableView<Customer> customerlist;
    @FXML
    private TableColumn<Customer, String> Names;
    @FXML
    private TableColumn<Customer, String> ageGroup;
    @FXML
    private TableColumn<Customer, String> flightClass;
    @FXML
    private TableColumn<Customer, String> perferedSeat;
    @FXML
    private TableColumn<Customer, String > row;
    @FXML
    private TableColumn<Customer, String> col;
    @FXML
    private TextField TxtSearch;
//String plane[][];
 String plane[][] ={
         {"*" ,"*" ,"*" ,"*" ,"*" , "*"},
          {"*" ,"*" ,"*" ,"*" ,"*" , "*"},
          {"*" ,"*" ,"*" ,"*" ,"*" , "*"},
         {"*" ,"*" ,"*" ,"*" ,"*" , "*"},
         {"*" ,"*" ,"*" ,"*" ,"*" , "*"},
         {"*" ,"*" ,"*" ,"*" ,"*" , "*"},
          {"*" ,"*" ,"*" ,"*" ,"*" , "*"},
         {"*" ,"*" ,"*" ,"*" ,"*" , "*"},
          {"*" ,"*" ,"*" ,"*" ,"*" , "*"},
          {"*" ,"*" ,"*" ,"*" ,"*" , "*"},
         {"*" ,"*" ,"*" ,"*" ,"*" , "*"},
          {"*" ,"*" ,"*" ,"*" ,"*" , "*"},
    };
  
    ArrayList<Customer> customer = new ArrayList<Customer>();
          ObservableList<Customer> person;
   int RowNumber;
          
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setSeatingTableView(); //sets up the seating view
        setComboBox(); //Sets up all combo boxes
    }  
 
     /**
     * Closes the program but also will call the write to file method
     * @param event 
     */
    @FXML
    private void Close_Click(ActionEvent event) {
       System.exit(0);
    }
    
    /**
     * Delete customer and removes the seat
     * @param event 
     */
    @FXML
    private void DeleteSeat_Click(ActionEvent event) {
       int I = customerlist.getSelectionModel().getSelectedIndex();
       int col = customer.get(I).getCol();
       int row = customer.get(I).getRow();
         plane[row][col] = "*";
         customer.remove(I);
      
     setSeatingTableView();
     setCustomerTableView();
    }

    /**
     * redirect to add customer method
     * @param event 
     */
    @FXML
    private void AddCustomer_click(ActionEvent event) {
        addCustomer();
    }
    
    /**
     * Search Function for customers
     * @param event 
     */
     @FXML
    private void Search_click(ActionEvent event) {
    }
    
 /***
     * Adds information to a table view
     */
    public void setSeatingTableView(){
        ArrayList<Seating> list = new ArrayList<Seating>();
        for(int i = 0; i < plane.length; i++){
            list.add(new Seating(plane[i][0], plane[i][1],plane[i][2],plane[i][3],plane[i][4],plane[i][5]));
          
        }
           
       ObservableList<Seating> list2 = FXCollections.observableArrayList(list);    
        A.setCellValueFactory(data -> data.getValue().PropertyA());
        B.setCellValueFactory(data -> data.getValue().PropertyB()); 
        C.setCellValueFactory(data -> data.getValue().PropertyC()); 
        D.setCellValueFactory(data -> data.getValue().PropertyD()); 
        E.setCellValueFactory(data -> data.getValue().PropertyE()); 
        F.setCellValueFactory(data -> data.getValue().PropertyF()); 
        FlightSeatsGrid.setItems(list2);
    }
 
    /**
     * Sets the customer table view()
     */
    public void setCustomerTableView(){
    
          ObservableList<Customer> person = FXCollections.observableArrayList(customer);
         Names.setCellValueFactory(data -> data.getValue().PropertyName());
        ageGroup.setCellValueFactory(data -> data.getValue().PropertyAgeGroup());
        flightClass.setCellValueFactory(data -> data.getValue().PropertyFlightClass());
        perferedSeat.setCellValueFactory(data -> data.getValue().PropertyPerferedSeat());
        row.setCellValueFactory(data -> data.getValue().PropertyRowNum());
        col.setCellValueFactory(data -> data.getValue().PropertyColLetter());
        customerlist.setItems(person);
    }
    
    /**
     * Sets all combo boxes required in this program
     */
    public void setComboBox(){
      String AgeCombo[] = {"Adult" , "Child"};
      String SeatType[] = {"Window", "Middle", "Aisle"}; 
      String ClassCombo[] = {"First", "Business", "Economy"};
        AgeComboBox.getItems().addAll(AgeCombo);
        SeatTypeCombo.getItems().addAll(SeatType);
        ClassComboBox.getItems().addAll(ClassCombo);
    } 
    

    /***
     * gets what seat type the customer would like
     */
    public void addCustomer(){
        switch(SeatTypeCombo.getValue()) {
        case "Window":
              getSeat(1, 6);
        break;
        case "Middle":
            getSeat(2, 5);
        break;
        case "Aisle":
            getSeat(3, 4);
        break;
        default:
            JOptionPane.showMessageDialog(null,"Please select SeatType");
        }
    }
    
    /**
     * Gets seat 
     * @param cRight
     * @param cLeft
     */
    public void getSeat(int cRight, int cLeft){
        for (int i = 0; i < plane.length; i++){
            if("*".equals(plane[i][cRight])){
                 AddCustomer(i,cRight);
                 break;
            }else if("*".equals(plane[i][cLeft])){
              AddCustomer(i,cLeft);
                 break;
            }else{
                 JOptionPane.showMessageDialog(null,"Sorry your SeatType isn't free");
            }
        }
    }
    
    /**
     * Gets the letter of the column then adds the customer 
     * @param row
     * @param Column
     */
    public void AddCustomer(int row, int Column){
        String A = "none";
          switch(Column) {
        case 1:
              A = "A";
        break;
        case 2:
               A = "B";
        break;
        case 3:
               A = "C";
        break;
          case 4:
                A = "E";
        break;
          case 5:
             A = "F";
        break;
          case 6:
           A = "G";
        break;
        default:
            JOptionPane.showMessageDialog(null,"Error");
        }
         
   customer.add(new Customer(txtName.getText(), AgeComboBox.getValue(), ClassComboBox.getValue(),SeatTypeCombo.getValue(), row , Column , "Row " + Integer.toString(row + 1), A));
           
           addSeat(row,Column);
        }
        
    /**
     * Adds the seat to the plane array 
     * @param row
     * @param Column
     */
    public void addSeat(int row, int Column){
        if("Adult".equals(AgeComboBox.getValue())){
            plane[row][Column] = "A"; 
          
        }else{
            plane[row][Column] = "C";
        }
        setSeatingTableView();
        setCustomerTableView();
    }
    
    
}


