package application;

// Necessary classes and libraries:
import java.io.IOException;
import java.sql.*;
import java.util.Date;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SearchForEmployeeController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TextField txtName;

    @FXML
    private TableView<Employee> employeeTable;

    @FXML
    private TableColumn<Employee, Integer> colEmployeeNumber;

    @FXML
    private TableColumn<Employee, String> colName;

    @FXML
    private TableColumn<Employee, Date> colStartDate;

    @FXML
    private TableColumn<Employee, Date> colEndDate;
    
    @FXML
    private TableColumn<Employee, String> E_P;

    @FXML
    private Button Back;

    @FXML
    private Button Search;

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
    void search(ActionEvent event) 
    {
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();
        String employeeName = txtName.getText().toUpperCase().trim();

        if (employeeName.isEmpty()) 
        {
            displayMessage("Please enter an employee name to search.", "info");
            return;
        }

        String SQL = "SELECT * FROM Employee WHERE E_Name LIKE ?;";

        try (Connection conn = JDBC.getConnection(); PreparedStatement stmt = conn.prepareStatement(SQL)) 
        {
            stmt.setString(1, "%" + employeeName + "%");

            try (ResultSet rs = stmt.executeQuery()) 
            {
                while (rs.next()) 
                {
                    int employeeNumber = rs.getInt("Employee_Number");
                    String name = rs.getString("E_Name");
                    Date startDate = rs.getDate("WorkingStart_Date");
                    Date endDate = rs.getDate("WorkingEnd_Date");
                    String password = rs.getString("E_Password") ;

                    Employee employee = new Employee(employeeNumber, name, startDate, endDate, password);
                    employeeList.add(employee);
                }
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            displayMessage("Database error while searching for employees!", "error");
        }

        employeeTable.setItems(employeeList);

        colEmployeeNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEmployeeNumber()).asObject());
        colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        colStartDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getWorkingStartDate()));
        colEndDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getWorkingEndDate()));
        E_P.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
    }
    
    public void setStage(Stage stage)  //this Method is to set the primary stage and called from the Main class   
    {
    	this.stage = stage ;  //Set the stage reference for this controller
    	stage.setTitle("LifeRental") ;  //Set the title of the application window
    }
}
