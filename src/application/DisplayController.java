package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class DisplayController 
{
	private Stage stage ;  //Reference to the main stage of the application
    private Scene scene ;  //Reference to the current scene
    private Parent root ;  //Reference to the root node of the FXML layout
	
	@FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private TableView<Rental> rentalTable;

    @FXML
    private TableColumn<Rental, Integer> rentalNumberColumn;

    @FXML
    private TableColumn<Rental, Integer> customerNumberColumn;

    @FXML
    private TableColumn<Rental, Integer> employeeNumberColumn;

    @FXML
    private TableColumn<Rental, Integer> equipmentNumberColumn;

    @FXML
    private TableColumn<Rental, Integer> invoiceNumberColumn;

    @FXML
    private TableColumn<Rental, Date> startDateColumn;

    @FXML
    private TableColumn<Rental, Date> endDateColumn;

    @FXML
    private Text resultText;
    
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

    private Connection connection;

    public void FindRentalController() {
        try {
            connection = JDBC.getConnection();
        } catch (SQLException e) {
            System.err.println("Error connecting to the database.");
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        // Initialize TableView columns
        rentalNumberColumn.setCellValueFactory(new PropertyValueFactory<>("rentalNumber"));
        customerNumberColumn.setCellValueFactory(new PropertyValueFactory<>("customerNumber"));
        employeeNumberColumn.setCellValueFactory(new PropertyValueFactory<>("employeeNumber"));
        equipmentNumberColumn.setCellValueFactory(new PropertyValueFactory<>("equipmentNumber"));
        invoiceNumberColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
    }

    @FXML
    private void findRentals() {
        if (startDate.getValue() == null || endDate.getValue() == null) {
            resultText.setText("Please select both start and end dates.");
            return;
        }

        LocalDate start = startDate.getValue();
        LocalDate end = endDate.getValue();

        if (end.isBefore(start)) {
            resultText.setText("End date cannot be before start date.");
            return;
        }

        ObservableList<Rental> rentalList = FXCollections.observableArrayList();

        String query = "SELECT * FROM Rental WHERE Start_Date BETWEEN ? AND ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDate(1, Date.valueOf(start));
            statement.setDate(2, Date.valueOf(end));

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int rentalNumber = resultSet.getInt("Rental_Number");
                int customerNumber = resultSet.getInt("Customer_Number");
                int employeeNumber = resultSet.getInt("Employee_Number");
                int equipmentNumber = resultSet.getInt("Equipment_Number");
                int invoiceNumber = resultSet.getInt("Invoice_Number");
                Date rentalStartDate = resultSet.getDate("Start_Date");
                Date rentalEndDate = resultSet.getDate("End_Date");
                String ci = resultSet.getString("Contract_Image") ;

                Rental rental = new Rental(rentalNumber, customerNumber, employeeNumber, equipmentNumber,
                        invoiceNumber, rentalStartDate, rentalEndDate , ci);
                rentalList.add(rental);
            }

            rentalTable.setItems(rentalList);
            resultText.setText("Found " + rentalList.size() + " rentals in the specified period.");

        } catch (SQLException e) {
            e.printStackTrace();
            resultText.setText("Error occurred while fetching data.");
        }
    }
}
