package application;

import java.io.IOException ;
import java.sql.Connection ;
import java.sql.PreparedStatement ;
import java.sql.SQLException ;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AddEmployeeController 
{
	private Stage stage ;  //Reference to the main stage of the application
    private Scene scene ;  //Reference to the current scene
    private Parent root ;  //Reference to the root node of the FXML layout
    
    @FXML
    private TextField nameField , employeeNumberField;

    @FXML
    private DatePicker startDatePicker , endDatePicker;
    
    @FXML
    private Button saveButton;
    
    @FXML
    private Button Back ;  //Button to switch back to the employee page
    
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
    private void handleSaveButtonAction() {
        try {
            // Retrieve and validate input data
            String employeeNumberText = employeeNumberField.getText();
            String name = nameField.getText();
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();

            if (employeeNumberText.isBlank() || name.isBlank()) {
            	displayMessage("Employee Number and Name must not be empty!", "error");
                return;
            }

            int employeeNumber;
            try {
                employeeNumber = Integer.parseInt(employeeNumberText);
            } catch (NumberFormatException e) {
            	displayMessage("Employee Number must be a valid integer!","error");
                return;
            }

            if (startDate == null) {
            	displayMessage("Start Date must be selected!", "error");
                return;
            }

            if (endDate != null && endDate.isBefore(startDate)) {
            	displayMessage("End Date cannot be before Start Date!", "error");
                return;
            }

            // Save employee to the database
            saveToDatabase(employeeNumber, name, startDate, endDate);

            // Notify user of success and clear fields
            displayMessage("Employee data saved successfully!", "info");
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
            displayMessage("Error!!!" , "error") ;  //Show error if adding fails
        }
    }

    private void saveToDatabase(int employeeNumber, String name, LocalDate startDate, LocalDate endDate) 
    {
    	
    	String sql = "INSERT INTO Employee (Employee_Number, E_Name, WorkingStart_Date, WorkingEnd_Date) VALUES (?, ?, ?, ?)";

        try (Connection connection = JDBC.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(sql))
        {

            preparedStatement.setInt(1, employeeNumber);
            preparedStatement.setString(2, name);
            preparedStatement.setDate(3, java.sql.Date.valueOf(startDate));
            preparedStatement.setDate(4, endDate != null ? java.sql.Date.valueOf(endDate) : null);

            preparedStatement.executeUpdate();
        } 
        catch (SQLException e) 
        {
        	e.printStackTrace();
        	displayMessage("Failed to save data to the database! " , "error");
            
        }
        
        
    }
    
    public void setStage(Stage stage)  //this Method is to set the primary stage and called from the Main class   
    {
    	this.stage = stage ;  //Set the stage reference for this controller
    	stage.setTitle("LifeRental") ;  //Set the title of the application window
    }
}
