package application;

// Necessary classes and libraries:
import java.io.IOException;  // For handling IO exceptions
import javafx.event.ActionEvent;  // Represents an action event, such as button clicks
import javafx.fxml.*;  // For loading FXML files
import javafx.scene.Parent;  // Represents the root node of the scene graph
import javafx.scene.Scene;  // Represents the JavaFX scene
import javafx.scene.control.Alert;  // Provides the ability to show alert dialogs
import javafx.scene.control.Alert.AlertType;  // Specifies types of alerts (e.g., ERROR, INFORMATION)
import javafx.scene.control.*;  // Imports various JavaFX controls like Button, TextField, etc
import javafx.stage.Stage;  // Represents the JavaFX window or stage
import java.sql.*;  // Import the Java SQL package to handle database connections and operations
import java.time.LocalDate;
import java.util.Date;

import javafx.collections.FXCollections;  // For creating observable lists
import javafx.collections.ObservableList;  // For observable lists
import javafx.beans.property.SimpleIntegerProperty;  // For wrapping integer properties
import javafx.beans.property.SimpleStringProperty;  // For wrapping string properties

public class UpdateEmployeeController 
{
    private Stage stage;  // Reference to the main stage of the application
    private Scene scene;  // Reference to the current scene
    private Parent root;  // Reference to the root node of the FXML layout

    @FXML
    private TextField txtName;  // TextField to input employee name

    @FXML
    private Button Back;  // Button to switch back to the employee page

    @FXML
    private Button updateButton, View;

    @FXML
    private TableView<Employee> employeeTable;  // Table to display employee data

    @FXML
    private TableColumn<Employee, Integer> colEmployeeNumber;  // Table column for employee number

    @FXML
    private TableColumn<Employee, String> colName;  // Table column for employee name

    @FXML
    private TableColumn<Employee, String> colWorkingStartDate;  // Table column for employee working start date

    @FXML
    private TableColumn<Employee, String> colWorkingEndDate;  // Table column for employee working end date
    
    @FXML
    private TableColumn<Employee, String> passwordColumn;  // Table column for employee working end date

    @FXML
    private TextField U_Name, U_Password;
    
    @FXML 
    private DatePicker U_WorkingStartDate, U_WorkingEndDate ;

    @FXML
    public void initialize() 
    {
    	//when button View is clicked:
    	View.setOnAction(this::checkAndView);  //check if the employee exists and view the original record 
    }
    
    private void displayMessage(String message, String messageType) 
    {
        Alert alert = new Alert("error".equalsIgnoreCase(messageType) ? AlertType.ERROR : AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML 
    void GoBack(ActionEvent event)  //Method executed when the "Back!" button is clicked
    {
    	try
    	{
    		stage = (Stage) Back.getScene().getWindow() ;  //Get the current stage
            stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("Employee.fxml")) ;  //Load the new scene
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
    void update(ActionEvent event) 
    {
        String employeeName = txtName.getText().toUpperCase().trim();

        try (Connection conn = JDBC.getConnection()) 
        {
            String updateQuery = "UPDATE Employee SET E_Name = ?, WorkingStart_Date = ?, WorkingEnd_Date = ?, E_Password = ? WHERE E_Name = ?;";
            PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
            
            LocalDate startDate = U_WorkingStartDate.getValue() ;  //Get the selected date from the DatePicker
            LocalDate endDate = U_WorkingEndDate.getValue() ;
            
            updateStmt.setString(1, txtName.getText().trim());
            updateStmt.setDate(2, java.sql.Date.valueOf(startDate));
            updateStmt.setDate(3, java.sql.Date.valueOf(endDate));
            updateStmt.setString(4, U_Password.getText().trim());
            updateStmt.setString(5, employeeName);

            int rowsAffected = updateStmt.executeUpdate();
            if (rowsAffected > 0) {
                displayMessage("Employee updated successfully!", "info");
            } else {
                displayMessage("Update failed!", "error");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            displayMessage("Database error!", "error");
        }
    }

    private void checkAndView(ActionEvent event) 
    {
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();

        String employeeName = txtName.getText().toUpperCase().trim();

        if (employeeName.isEmpty()) 
        {
            displayMessage("Please enter an employee name!", "error");
            return;
        }

        String fetchQuery = "SELECT * FROM Employee WHERE E_Name = ?;";

        try (Connection conn = JDBC.getConnection() ; PreparedStatement fetchStmt = conn.prepareStatement(fetchQuery))
        {
            fetchStmt.setString(1, employeeName);
            ResultSet rs = fetchStmt.executeQuery();

            if (!rs.next()) 
            {
                displayMessage("Employee not found!", "error");
                return;
            }

            int employeeNumber = rs.getInt("Employee_Number");
            String name = rs.getString("E_Name");
            Date workingStartDate = rs.getDate("WorkingStart_Date");
            Date workingEndDate = rs.getDate("WorkingEnd_Date");
            String password = rs.getString("E_Password");

           Employee employee = new Employee(employeeNumber, name, workingStartDate, workingEndDate, password);
           employeeList.add(employee);

            employeeTable.setItems(employeeList);

            colEmployeeNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEmployeeNumber()).asObject());
            colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
            colWorkingStartDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWorkingStartDate() != null ? cellData.getValue().getWorkingStartDate().toString() : "-"));
            colWorkingEndDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWorkingEndDate() != null ? cellData.getValue().getWorkingEndDate().toString() : "-"));
            passwordColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            displayMessage("Database error!", "error");
        }
    }

    public void setStage(Stage stage) 
    {
        this.stage = stage;
        stage.setTitle("LifeRental");
    }
}
