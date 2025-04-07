package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class InvoiceController 
{
	private Stage stage ;  //Reference to the main stage of the application
    private Scene scene ;  //Reference to the current scene
    private Parent root ;  //Reference to the root node of the FXML layout
    
    @FXML
    private TableView<Invoice> InvoiceTable ;  //Table to display Invoice data
    
    @FXML
    private TableColumn<Invoice , Integer> I_No ;  
    
    @FXML
    private TableColumn<Invoice , Double> totalAmount ;  
    
    @FXML
    private TableColumn<Invoice , Double> discount ;  
    
    @FXML
    private TableColumn<Invoice , String> status ;  
    
    @FXML
    private TableColumn<Invoice , String> I_Date ;  
    
    @FXML
    private TableColumn<Invoice , String> Due_Date ;  
    
    @FXML
    private TableColumn<Invoice , String> type ;  
    
    @FXML
    private Button Back ;  //Button to switch back to the Tables page
    
    @FXML
    public void initialize() 
    {
        ViewInvoice() ;  //view all customers when the customer scene is opened
    }
    
    private void displayMessage(String message , String messageType)  //Method to display a message alert to the user 
    {
        Alert alert = new Alert("error".equalsIgnoreCase(messageType) ? AlertType.ERROR : AlertType.INFORMATION) ;  //Determine alert type
        alert.setContentText(message) ;  //Set the alert message
        alert.showAndWait() ;  //Display the alert and wait for user acknowledgment
    }
    
    @FXML 
    void GoBack(ActionEvent event)  //Method executed when the "Back!" button is clicked
    {
    	try
    	{
    		stage = (Stage) Back.getScene().getWindow() ;  //Get the current stage
            stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("Tables.fxml")) ;  //Load the new scene
            scene = new Scene(root) ;  //Set the scene
            stage.setScene(scene) ;  //Apply the new scene to the stage
            stage.show() ;  //Show the stage with the new scene
    	} 
    	catch (IOException e) 
        {
            e.printStackTrace() ;  //Print any exceptions that occur during the process
            displayMessage("Error!!!" , "error") ;  //Show error if login fails
        }
    }
    
    @FXML
    void ViewInvoice()
    {
    	ObservableList<Invoice> InvoiceList = FXCollections.observableArrayList() ;  //Create an observable list to hold customer data
    	
    	String sql = "SELECT * FROM Invoice" ;  //SQL query
    	
    	try (Connection conn = JDBC.getConnection() ; PreparedStatement stmt = conn.prepareStatement(sql) ; ResultSet rs = stmt.executeQuery())  //Establish connection to the database , Create a statement to execute SQL , and Execute query and retrieve results
        {
    		while(rs.next()) 
            {
    			int invoiceNumber = rs.getInt("Invoice_Number") ;
    			double TotalAmount = rs.getDouble("Total_Amount") ;
    			double Discount = rs.getDouble("Discount") ;
    			String status = rs.getString("Payment_Status") ;
    			Date InvoiceDate = rs.getDate("Invoice_Date") ;
    			Date dueDate = rs.getDate("Due_Date") ;
    			String Type = rs.getString("Invoice_Type") ;
    			
    			//create invoice object and add to the list:
    			Invoice I = new Invoice(invoiceNumber , TotalAmount , Discount , status , InvoiceDate , dueDate , Type) ;
    			InvoiceList.add(I) ;
            }
        }
    	catch (SQLException e) 
        {
            e.printStackTrace() ;  //Print stack trace in case of an SQL exception
        }
    	
    	InvoiceTable.setItems(InvoiceList);
    	
    	I_No.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getInvoiceNumber()).asObject()) ;
    	totalAmount.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getTotalAmount()).asObject());
    	discount.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getDiscount()).asObject());
    	status.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaymentStatus())) ;
    	I_Date.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInvoiceDate().toString())) ;
    	Due_Date.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDueDate().toString())) ;
    	type.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getInvoiceType())) ;
    }
    
    public void setStage(Stage stage)  //this Method is to set the primary stage and called from the Main class   
    {
    	this.stage = stage ;  //Set the stage reference for this controller
    	stage.setTitle("LifeRental") ;  //Set the title of the application window
    }
}
