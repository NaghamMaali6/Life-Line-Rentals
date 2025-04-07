package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class FindactiverentalsController 
{
	@FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private TableView<Involve> rentalTable;
    @FXML
    private TableColumn<Involve, Integer> rentalNumberColumn;
    @FXML
    private TableColumn<Involve, Integer> equipmentNumberColumn;
    @FXML
    private TableColumn<Involve, String> statusColumn;

    private final ObservableList<Involve> rentalList = FXCollections.observableArrayList();
    
    @FXML
    private Button Back;
    
    private Stage stage ;  //Reference to the main stage of the application
    private Scene scene ;  //Reference to the current scene
    private Parent root ;  //Reference to the root node of the FXML layout
    
    
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
            root = FXMLLoader.load(getClass().getResource("Rental.fxml")) ;  //Load the new scene
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
    private void initialize() {
        // Initialize TableView columns
        rentalNumberColumn.setCellValueFactory(new PropertyValueFactory<>("rentalNumber"));
        equipmentNumberColumn.setCellValueFactory(new PropertyValueFactory<>("equipmentNumber"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("rStatus"));

        // Bind data to TableView
        rentalTable.setItems(rentalList);
    }

    @FXML
    private void handleSearchButtonAction() {
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        // Validate date inputs
        if (startDate == null || endDate == null) {
            showAlert("Validation Error", "Both start date and end date must be selected!", Alert.AlertType.WARNING);
            return;
        }

        if (endDate.isBefore(startDate)) {
            showAlert("Validation Error", "End date cannot be before the start date!", Alert.AlertType.WARNING);
            return;
        }

        // Load active rentals
        loadActiveRentals(startDate, endDate);
    }

    private void loadActiveRentals(LocalDate startDate, LocalDate endDate) {
        // Clear existing data from TableView
        rentalList.clear();

        // SQL query to fetch active rentals
        String sql = """
                     SELECT I.R_Status, I.Equipment_Number, I.Rental_Number
                     FROM Involve I
                     WHERE I.R_Status = 'active'
                       AND I.Rental_Number IN (
                           SELECT R.Rental_Number
                           FROM Rental R
                           WHERE R.Start_Date BETWEEN ? AND ?
                       )
                     """;

        int activeRentalsCount = 0; // Variable to count the number of active rentals

        // Connect to the database and execute query
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set query parameters
            pstmt.setDate(1, Date.valueOf(startDate));
            pstmt.setDate(2, Date.valueOf(endDate));

            ResultSet resultSet = pstmt.executeQuery();

            // Add results to the observable list and count the active rentals
            while (resultSet.next()) {
                String rStatus = resultSet.getString("R_Status");
                int equipmentNumber = resultSet.getInt("Equipment_Number");
                int rentalNumber = resultSet.getInt("Rental_Number");

                rentalList.add(new Involve(rStatus, equipmentNumber, rentalNumber));
                activeRentalsCount++;
            }

            // Display the total count of active rentals in an alert
            showAlert("Active Rentals", "Total number of active rentals: " + activeRentalsCount, Alert.AlertType.INFORMATION);

        } catch (SQLException e) {
            showAlert("Database Error", "Error fetching active rentals: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
