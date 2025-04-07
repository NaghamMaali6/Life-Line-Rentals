package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class FindRentalsNumberController 
{

	private Stage stage ;  //Reference to the main stage of the application
    private Scene scene ;  //Reference to the current scene
    private Parent root ;  //Reference to the root node of the FXML layout
	
	@FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private Text resultText;
    
    @FXML
    private Button Back;

    private Connection connection;
    
    private void displayMessage(String message, String messageType) 
    {
        Alert alert = new Alert("error".equalsIgnoreCase(messageType) ? AlertType.ERROR : AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void GoBack(ActionEvent event) 
    {
        try {
            stage = (Stage) Back.getScene().getWindow();
            stage.close();
            root = FXMLLoader.load(getClass().getResource("Rental.fxml"));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            displayMessage("Failed to load the tables view: " + e.getMessage(), "error");
        }
    }

    // Constructor to establish a connection to the MySQL database
    public FindRentalsNumberController() {
        try {
            // Get the database connection from the JDBC class
            connection = JDBC.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            resultText.setText("Error connecting to the database.");
        }
    }

    // Method to handle the button click event for searching rentals
    @FXML
    private void findRentals() {
        // Validate if both dates are selected
        if (startDate.getValue() == null || endDate.getValue() == null) {
            resultText.setText("Please select both start and end dates.");
            return;
        }

        // Get the start and end dates from the DatePickers
        LocalDate start = startDate.getValue();
        LocalDate end = endDate.getValue();

        // Validate that the end date is after the start date
        if (end.isBefore(start)) {
            resultText.setText("End date cannot be before start date.");
            return;
        }

        // Query to find the number of rentals that start within the selected period
        String query = "SELECT COUNT(*) AS rental_count FROM Rental R WHERE R.Start_Date BETWEEN ? AND ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            // Set the parameters for the query
            statement.setDate(1, Date.valueOf(start));  // Convert LocalDate to SQL Date
            statement.setDate(2, Date.valueOf(end));    // Convert LocalDate to SQL Date

            // Execute the query
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Get the rental count from the result set
                int rentalCount = resultSet.getInt("rental_count");
                resultText.setText("Number of rentals starting in the selected period: " + rentalCount);
            } else {
                resultText.setText("No rentals found in the selected period.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resultText.setText("Error occurred while fetching data.");
        }
    }
}