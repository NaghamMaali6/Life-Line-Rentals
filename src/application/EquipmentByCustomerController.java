package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EquipmentByCustomerController 
{	
	private Stage stage ;  //Reference to the main stage of the application
    private Scene scene ;  //Reference to the current scene
    private Parent root ;  //Reference to the root node of the FXML layout
	
    @FXML
    private TextField txtName;  //TextField to input customer name
    
    @FXML
    private TableView<Equipment> Equipment ;  //Table to display customer data
    
    @FXML
    private TableColumn<Equipment , String> EquipmentName ;  //Table column for Equipment Name
    
    @FXML
    private Button Back ;  //Button to switch back to the Customer page
    
    @FXML
    private Button View ;
    
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
    void viewEq(ActionEvent event)
    {
        String customerName = txtName.getText().trim();
        if (customerName.isEmpty())
        {
            displayMessage("Please enter a customer name.", "error");
            return;
        }

        ObservableList<Equipment> rentedEquipment = FXCollections.observableArrayList();

        String sql = "SELECT Equipment_Name FROM Equipment WHERE Equipment_Number IN (SELECT Equipment_Number FROM Rental WHERE Customer_Number = (SELECT Customer_Number FROM Customer WHERE Full_Name = ?))";

        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setString(1, customerName);
            try (ResultSet rs = stmt.executeQuery())
            {
                while (rs.next())
                {
                    String equipmentName = rs.getString("Equipment_Name");

                    Equipment eq = new Equipment(0, equipmentName, "", 0, 0, "");
                    rentedEquipment.add(eq);
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        Equipment.setItems(rentedEquipment);  //Populate the TableView with the equipment name
        
        EquipmentName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_Equipment_Name()));  //Set cell value factory the column
    }
    
    public void setStage(Stage stage)  //this Method is to set the primary stage and called from the Main class   
    {
    	this.stage = stage ;  //Set the stage reference for this controller
    	stage.setTitle("LifeRental") ;  //Set the title of the application window
    }
}
