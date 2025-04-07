package application;

import java.io.IOException;
import javafx.event.ActionEvent ;  //Represents an action event, such as button clicks
import javafx.fxml.* ;  //For loading FXML files
import javafx.scene.Parent ;  //Represents the root node of the scene graph
import javafx.scene.Scene ;  //Represents the JavaFX scene
import javafx.scene.control.Alert ;  //Provides the ability to show alert dialogs
import javafx.scene.control.Alert.AlertType ;  //Specifies types of alerts (e.g., ERROR, INFORMATION)
import javafx.scene.control.* ;  //Imports various JavaFX controls like Button, TextField, PasswordField, etc
import javafx.stage.Stage ;  //Represents the JavaFX window or stage
import java.sql.* ;  //Import the Java SQL package to handle database connections and operations

public class DeleteCustomerController 
{
	private Stage stage ;  //Reference to the main stage of the application
    private Scene scene ;  //Reference to the current scene
    private Parent root ;  //Reference to the root node of the FXML layout
	
	@FXML
    private TextField txtName ;  //TextField for entering the customer name
	
	@FXML
    private Button Back ;  //Button to switch back to the customr page
	
	@FXML 
	private Button deleteButton ;  //Button to delete the customer
	
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
            root = FXMLLoader.load(getClass().getResource("Customer.fxml")) ;  //Load the new scene
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
    
     //search for customer name or make sure it exist 
	//delete this customer record from the database
    @FXML 
    void Delete(ActionEvent event)  
    {
        String customerName = txtName.getText().toUpperCase().trim() ;  //Get and format the customer name(conver to uppercase and remove space after and before it) 

        if (customerName.isEmpty()) 
        {
            displayMessage("Please enter a customer name!" , "error") ;  //Show error if no name is entered
            return ;
        }

        try (Connection conn = JDBC.getConnection()) 
        {
            //Step 1: Get Customer_Number for the given Full_Name
            String fetchCustomerNumberQuery = "SELECT Customer_Number FROM Customer WHERE Full_Name = ? ;" ;
            try (PreparedStatement fetchStmt = conn.prepareStatement(fetchCustomerNumberQuery)) 
            {
                fetchStmt.setString(1, customerName) ;  //Set the customer name in the query
                ResultSet rs = fetchStmt.executeQuery() ;  //Execute the query to fetch the customer number
                if (!rs.next())
                {
                    displayMessage("Customer not found!", "error") ;  //Show error if customer is not found
                    return ;
                }

                int customerNumber = rs.getInt("Customer_Number") ;  //Retrieve customer number

                //Step 2: Delete associated records in the involve table (related to the rental)
                String deleteInvolveQuery = "DELETE FROM involve WHERE Rental_Number IN (SELECT Rental_Number FROM Rental WHERE Customer_Number = ?) ;";
                try (PreparedStatement deleteInvolveStmt = conn.prepareStatement(deleteInvolveQuery)) 
                {
                    deleteInvolveStmt.setInt(1, customerNumber) ;  //Set customer number in the query
                    deleteInvolveStmt.executeUpdate() ;  //Execute the delete operation
                }

                //Step 3: Delete associated records in Has table
                String deleteHasQuery = "DELETE FROM Has WHERE Rental_Number IN (SELECT Rental_Number FROM Rental WHERE Customer_Number = ?) ;" ;
                try (PreparedStatement deleteHasStmt = conn.prepareStatement(deleteHasQuery)) 
                {
                    deleteHasStmt.setInt(1, customerNumber) ;  //Set customer number in the query
                    deleteHasStmt.executeUpdate() ;  //Execute the delete operation
                }

                //Step 4: Delete associated records in Payment table
                String deletePaymentQuery = "DELETE FROM Payment WHERE Rental_Number IN (SELECT Rental_Number FROM Rental WHERE Customer_Number = ?) ;" ;
                try (PreparedStatement deletePaymentStmt = conn.prepareStatement(deletePaymentQuery)) 
                {
                    deletePaymentStmt.setInt(1, customerNumber) ;  //Set customer number in the query
                    deletePaymentStmt.executeUpdate() ;  //Execute the delete operation
                }

                //Step 5: Delete associated records in Rental table
                String deleteRentalQuery = "DELETE FROM Rental WHERE Customer_Number = ? ;" ;
                try (PreparedStatement deleteRentalStmt = conn.prepareStatement(deleteRentalQuery)) 
                {
                    deleteRentalStmt.setInt(1, customerNumber) ;  //Set customer number in the query
                    deleteRentalStmt.executeUpdate() ;  //Execute the delete operation
                }

                //Step 6: Delete the customer record
                String deleteCustomerQuery = "DELETE FROM Customer WHERE Customer_Number = ? ;" ;
                try (PreparedStatement deleteCustomerStmt = conn.prepareStatement(deleteCustomerQuery)) 
                {
                    deleteCustomerStmt.setInt(1, customerNumber) ;  //Set customer number in the query
                    int rowsDeleted = deleteCustomerStmt.executeUpdate() ;  //Execute the delete operation

                    if (rowsDeleted > 0) 
                    {
                        displayMessage("Customer and associated records deleted successfully." , "info") ;  //Show success message
                    } 
                    else 
                    {
                        displayMessage("Failed to delete the customer record.", "error");  //Show error if deletion fails
                    }
                }
            }
        }
        catch (SQLException e) 
        {
            e.printStackTrace() ;  //Print stack trace in case of an SQL exception
            displayMessage("Error with deleting customer!" , "error") ;  //Show error if an issue occurs
        }
    }

	
	public void setStage(Stage stage)  //this Method is to set the primary stage and called from the Main class   
    {
    	this.stage = stage ;  //Set the stage reference for this controller
    	stage.setTitle("LifeRental") ;  //Set the title of the application window
    }
}
