package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RentalswithinsuranceController 
{
	private Stage stage ;  //Reference to the main stage of the application
    private Scene scene ;  //Reference to the current scene
    private Parent root ;  //Reference to the root node of the FXML layout
	
	@FXML
    private TextField percentageTextField;

    @FXML
    private Button calculateButton;

    @FXML
    private TableView<Invoice> insuranceTableView;

    @FXML
    private TableColumn<Invoice, Integer> invoiceNumberColumn;

    @FXML
    private TableColumn<Invoice, Double> totalAmountColumn;

    @FXML
    private TableColumn<Invoice, Double> discountColumn;

    @FXML
    private TableColumn<Invoice, String> invoiceDateColumn;

    @FXML
    private TableColumn<Invoice, String> paymentStatusColumn;
    
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

    private ObservableList<Invoice> insuranceList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Set up the TableView columns
        invoiceNumberColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));
        totalAmountColumn.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
        invoiceDateColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceDate"));
        paymentStatusColumn.setCellValueFactory(new PropertyValueFactory<>("paymentStatus"));

        // Bind the TableView to the observable list
        insuranceTableView.setItems(insuranceList);
    }

    @FXML
    private void calculateInsuranceCoverage(ActionEvent event) {
        String percentageQuery = "SELECT (COUNT(*) * 100.0 / (SELECT COUNT(*) FROM Invoice)) AS insurancePercentage FROM Invoice WHERE Discount != 0";
        String insuranceListQuery = "SELECT * FROM Invoice I WHERE I.Discount != 0";

        try (Connection connection = JDBC.getConnection()) {
            // Calculate the percentage of rentals with insurance
            try (PreparedStatement percentageStatement = connection.prepareStatement(percentageQuery);
                 ResultSet percentageResultSet = percentageStatement.executeQuery()) {

                if (percentageResultSet.next()) {
                    double percentage = percentageResultSet.getDouble("insurancePercentage");
                    percentageTextField.setText(String.format("%.2f%%", percentage));
                }
            }

            // Fetch the list of invoices with insurance
            try (PreparedStatement listStatement = connection.prepareStatement(insuranceListQuery);
                 ResultSet listResultSet = listStatement.executeQuery()) {

                insuranceList.clear();
                while (listResultSet.next()) {
                    int invoiceNumber = listResultSet.getInt("Invoice_Number");
                    double totalAmount = listResultSet.getDouble("Total_Amount");
                    double discount = listResultSet.getDouble("Discount");
                    String paymentStatus = listResultSet.getString("Payment_Status");
                    String invoiceDate = listResultSet.getDate("Invoice_Date").toString();

                    insuranceList.add(new Invoice(invoiceNumber, totalAmount, discount, paymentStatus,
                            java.sql.Date.valueOf(invoiceDate), null, null));
                }
            }
        } catch (Exception e) {
            percentageTextField.setText("Error: " + e.getMessage());
        }
    }
}
