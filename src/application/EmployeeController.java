package application;

import java.io.IOException ;
import java.sql.Connection ;
import java.sql.PreparedStatement ;
import java.sql.ResultSet ;
import java.sql.SQLException ;
import java.util.Date ;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class EmployeeController 
{
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableView<Employee> EmployeeTable;

    @FXML
    private TableColumn<Employee, Integer> colEmployeeNumber;

    @FXML
    private TableColumn<Employee, String> colEName;

    @FXML
    private TableColumn<Employee, String> colWorkingStartDate;

    @FXML
    private TableColumn<Employee, String> colWorkingEndDate;

    @FXML
    private TableColumn<Employee, String> colEPassword;

    @FXML
    private Button Back;
    
    @FXML
    private Button add , search , update ;

    @FXML
    public void initialize() 
    {
        ViewEmployees();
    }

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
            root = FXMLLoader.load(getClass().getResource("Tables.fxml"));
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            displayMessage("Failed to load the tables view: " + e.getMessage(), "error");
        }
    }

    private void ViewEmployees() 
    {
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Employee";

        try (Connection conn = JDBC.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) 
        {

            while (rs.next()) 
            {
                int employeeNumber = rs.getInt("Employee_Number");
                String name = rs.getString("E_Name");
                Date workingStartDate = rs.getDate("WorkingStart_Date");
                Date workingEndDate = rs.getDate("WorkingEnd_Date");
                String password = rs.getString("E_Password");

                Employee employee = new Employee(employeeNumber, name, workingStartDate, workingEndDate, password);
                employeeList.add(employee);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        EmployeeTable.setItems(employeeList);

        colEmployeeNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEmployeeNumber()).asObject());
        colEName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        colWorkingStartDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWorkingStartDate() != null ? cellData.getValue().getWorkingStartDate().toString() : "-"));
        colWorkingEndDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWorkingEndDate() != null ? cellData.getValue().getWorkingEndDate().toString() : "-"));
        colEPassword.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
    }
    
    @FXML
    void AddEmployee(ActionEvent event)
    {
    	try
    	{
    		stage = (Stage) add.getScene().getWindow() ;  //Get the current stage
            stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("AddEmployee.fxml")) ;  //Load the new scene
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
    void UpdateEmployee(ActionEvent event)
    {
    	try
    	{
    		stage = (Stage) update.getScene().getWindow() ;  //Get the current stage
            stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("UpdateEmployee.fxml")) ;  //Load the new scene
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
    void SearchForEmployee(ActionEvent event)
    {
    	try
    	{
    		stage = (Stage) search.getScene().getWindow() ;  //Get the current stage
            stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("SearchForEmployee.fxml")) ;  //Load the new scene
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
    
    public void setStage(Stage stage)  //this Method is to set the primary stage and called from the Main class   
    {
    	this.stage = stage ;  //Set the stage reference for this controller
    	stage.setTitle("LifeRental") ;  //Set the title of the application window
    }
}
