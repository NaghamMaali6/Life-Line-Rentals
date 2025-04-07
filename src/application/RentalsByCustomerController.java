package application;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.* ;
import javafx.stage.Stage;

public class RentalsByCustomerController 
{
	private Stage stage ;  //Reference to the main stage of the application
    private Scene scene ;  //Reference to the current scene
    private Parent root ;  //Reference to the root node of the FXML layout
	
    @FXML
    private TextField txtName ;  //TextField for entering the customer name
	
	@FXML
    private Button Back ;  //Button to switch back to the customr page
	
	@FXML
    private TableView<Rental> RentalHistoryTable ;
	
	@FXML
    private TableColumn<Rental , Integer> R_No ;  //Table column for rental number
	
	@FXML
    private TableColumn<Rental , Integer> C_No ; 
	
	@FXML
    private TableColumn<Rental , Integer> Em_No ; 
	
	@FXML
    private TableColumn<Rental , Integer> Eq_No ; 
	
	@FXML
    private TableColumn<Rental , Integer> I_No ;
	
	@FXML
    private TableColumn<Rental , Integer> E_D ; 
	
	@FXML
    private TableColumn<Rental , Integer> S_D ; 
	
	@FXML
    private TableColumn<Rental , String> contract ; 
	
	@FXML 
	private Button display ;
	
	@FXML 
	private ImageView CI ;
	
	private void loadImage(ImageView imageView , String imagePath)  //Method to load an image into an ImageView
    {
        File file = new File(imagePath) ;  //Create a File object for the image path
        if (file.exists()) 
        {
        	//If the file exists:
        	Image image = new Image(file.toURI().toString()) ;  //Load the image from the file
            imageView.setImage(image) ;  //Set the image in the ImageView
        } 
        else 
        {
            System.err.println("Image not found: " + imagePath) ;  //If the file does not exist, print an error message to the console
        }
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
    void RentalHistory(ActionEvent event)  //Display rental history for a specific customer
    {
    	String ContractPath = null ;
    	
    	String customerName = txtName.getText().trim();
        if (customerName.isEmpty())
        {
            displayMessage("Please enter a customer name.", "error");
            return;
        }
        
        ObservableList<Rental> RentalHistoryList = FXCollections.observableArrayList();
        
        String sql = "SELECT * FROM Rental WHERE Customer_Number = (SELECT Customer_Number FROM Customer WHERE Full_Name = ? ) ;" ;
        
        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {
        	stmt.setString(1, customerName);
        	try (ResultSet rs = stmt.executeQuery())
            {
        		while (rs.next())
                {
        			int R_N = rs.getInt("Rental_Number") ;
        			int C_N = rs.getInt("Customer_Number") ;
        			int Em_N = rs.getInt("Employee_Number") ;
        			int Eq_N = rs.getInt("Equipment_Number") ;
        			int I_N = rs.getInt("Invoice_Number") ;
        			Date SD = rs.getDate("Start_Date") ;
        			Date ED = rs.getDate("End_Date") ;
        			String c = rs.getString("Contract_Image") ;
        			
        			Rental R = new Rental(R_N , C_N , Em_N , Eq_N , I_N , SD , ED , c) ;
        			RentalHistoryList.add(R) ;
        			
        			ContractPath = c ;
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        
        RentalHistoryTable.setItems(RentalHistoryList) ;
        
        R_No.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRental_Number()).asObject()) ;
        C_No.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCustomer_Number()).asObject()) ;
        Em_No.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEmployee_Number()).asObject()) ;
        Eq_No.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEquipment_Number()).asObject()) ;
        I_No.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getInvoice_Number()).asObject()) ;
        S_D.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getStart_Date())) ;
        E_D.setCellValueFactory(cellData -> new SimpleObjectProperty(cellData.getValue().getEnd_Date())) ;
        
        loadImage(CI , ContractPath) ;  //load contract image
    }
	
	public void setStage(Stage stage)  //this Method is to set the primary stage and called from the Main class   
    {
    	this.stage = stage ;  //Set the stage reference for this controller
    	stage.setTitle("LifeRental") ;  //Set the title of the application window
    }
}
