package application;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class AddRentalController {
	
	private Stage stage ;  //Reference to the main stage of the application
    private Scene scene ;  //Reference to the current scene
    private Parent root ;  //Reference to the root node of the FXML layout

    @FXML
    private TextField customerNumberField, nationalIDField, fullNameField, addressField, phoneNumberField;
    @FXML
    private ComboBox<String> insuranceField;
    @FXML
    private TextField equipmentNumberField, employeeNumberField, startDateField, endDateField;
    @FXML
    private TextField invoiceNumberField, totalAmountField, discountField, paymentStatusField;
    @FXML
    private TextField pricePerDayField, paymentMethodField;
    @FXML
    private Label statusLabel;
    
    @FXML
    private Button Back;
    
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
    public void initialize() {
        // Initialize the ComboBox for the insurance field
        insuranceField.setItems(FXCollections.observableArrayList("Yes", "No"));
    }

    @FXML
    private void handleAddRental() {
        try (Connection connection = JDBC.getConnection()) {
            connection.setAutoCommit(false);

            // Add customer
            String customerSQL = """
                INSERT INTO Customer (Customer_Number, National_ID_Number, Full_Name, Address, Phone_Number, Have_an_Insurance)
                VALUES (?, ?, ?, ?, ?, ?)
            """;
            try (PreparedStatement customerStmt = connection.prepareStatement(customerSQL)) {
                customerStmt.setInt(1, Integer.parseInt(customerNumberField.getText()));
                customerStmt.setString(2, nationalIDField.getText());
                customerStmt.setString(3, fullNameField.getText());
                customerStmt.setString(4, addressField.getText());
                customerStmt.setString(5, phoneNumberField.getText());
                customerStmt.setString(6, insuranceField.getValue() != null ? insuranceField.getValue() : "No"); // Default to "No" if not selected
                customerStmt.executeUpdate();
            }

            // Add invoice
            String invoiceSQL = """
                INSERT INTO Invoice (Invoice_Number, Total_Amount, Discount, Payment_Status, Invoice_Date, Due_Date, Invoice_Type)
                VALUES (?, ?, ?, ?, CURRENT_DATE, ?, ?)
            """;
            try (PreparedStatement invoiceStmt = connection.prepareStatement(invoiceSQL)) {
                invoiceStmt.setInt(1, Integer.parseInt(invoiceNumberField.getText()));
                invoiceStmt.setDouble(2, Double.parseDouble(totalAmountField.getText()));
                invoiceStmt.setDouble(3, Double.parseDouble(discountField.getText()));
                invoiceStmt.setString(4, paymentStatusField.getText());
                invoiceStmt.setDate(5, java.sql.Date.valueOf(startDateField.getText())); // Assuming due date = start date
                invoiceStmt.setString(6, "Rental");
                invoiceStmt.executeUpdate();
            }

            // Add rental
            String rentalSQL = """
                INSERT INTO Rental (Rental_Number, Customer_Number, Employee_Number, Equipment_Number, Invoice_Number, Start_Date, End_Date)
                VALUES (?, ?, ?, ?, ?, ?, ?)
            """;
            try (PreparedStatement rentalStmt = connection.prepareStatement(rentalSQL)) {
                rentalStmt.setInt(1, Integer.parseInt(invoiceNumberField.getText())); // Assuming Rental_Number = Invoice_Number
                rentalStmt.setInt(2, Integer.parseInt(customerNumberField.getText()));
                rentalStmt.setInt(3, Integer.parseInt(employeeNumberField.getText()));
                rentalStmt.setInt(4, Integer.parseInt(equipmentNumberField.getText()));
                rentalStmt.setInt(5, Integer.parseInt(invoiceNumberField.getText()));
                rentalStmt.setDate(6, java.sql.Date.valueOf(startDateField.getText()));

                // Handle optional end date
                if (endDateField.getText().isEmpty()) {
                    rentalStmt.setNull(7, java.sql.Types.DATE);
                } else {
                    rentalStmt.setDate(7, java.sql.Date.valueOf(endDateField.getText()));
                }
                rentalStmt.executeUpdate();
            }

            // Add payment
            String paymentSQL = """ 
                INSERT INTO Payment (Payment_Number, Rental_Number, Number_of_Rental_Days, Price_per_Day, Payment_Method)
                VALUES (?, ?, ?, ?, ?) 
            """;
            try (PreparedStatement paymentStmt = connection.prepareStatement(paymentSQL)) {
                paymentStmt.setInt(1, Integer.parseInt(invoiceNumberField.getText())); // Assuming Payment_Number = Invoice_Number
                paymentStmt.setInt(2, Integer.parseInt(invoiceNumberField.getText())); // Assuming Rental_Number = Invoice_Number

                // Calculate rental days
                if (endDateField.getText().isEmpty()) {
                    paymentStmt.setNull(3, java.sql.Types.INTEGER);
                } else {
                    paymentStmt.setInt(3, calculateRentalDays(startDateField.getText(), endDateField.getText()));
                }

                paymentStmt.setDouble(4, Double.parseDouble(pricePerDayField.getText()));
                paymentStmt.setString(5, paymentMethodField.getText());
                paymentStmt.executeUpdate();
            }

            connection.commit();
            statusLabel.setText("Rental added successfully!");
            statusLabel.setStyle("-fx-text-fill: green;");
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
            statusLabel.setText("Error adding rental. Please check your inputs.");
            statusLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private int calculateRentalDays(String start, String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = end.isEmpty() ? LocalDate.now() : LocalDate.parse(end);
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }
}