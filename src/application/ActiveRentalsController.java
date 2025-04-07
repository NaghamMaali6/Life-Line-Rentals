package application ;

//necessary classes and libraries:
import javafx.event.ActionEvent ;  //Represents an action event, such as button clicks
import javafx.fxml.* ;  //For loading FXML files
import javafx.scene.Parent ;  //Represents the root node of the scene graph
import javafx.scene.Scene ;  //Represents the JavaFX scene
import javafx.scene.control.Alert.AlertType ;  //Specifies types of alerts (e.g., ERROR, INFORMATION)
//Provides the ability to show alert dialogs
import javafx.scene.control.* ;  //Imports various JavaFX controls like Button, TextField, PasswordField, etc
import javafx.stage.Stage ;  //Represents the JavaFX window or stage
import java.io.* ;  //For file handling and IO exceptions
import java.sql.* ;  //Import the Java SQL package to handle database connections and operations
import javafx.collections.FXCollections ;  //For creating observable lists
import javafx.collections.ObservableList ;  //For observable lists
import javafx.beans.property.SimpleIntegerProperty ;  //For wrapping integer properties
import javafx.beans.property.SimpleStringProperty ;  //For wrapping string properties

public class ActiveRentalsController 
{
	private Stage stage ;  //Reference to the main stage of the application
    private Scene scene ;  //Reference to the current scene
    private Parent root ;  //Reference to the root node of the FXML layout
	
	@FXML
    private TableView<Customer> customerTable ;  //Table to display customer data
    
	@FXML
    private TableColumn<Customer , Integer> colCustomerNumber ;  //Table column for customer number
    
    @FXML
    private TableColumn<Customer , String> colNationalID ;  //Table column for customer national ID
    
    @FXML
    private TableColumn<Customer , String> colFullName ;  //Table column for customer name
    
    @FXML
    private TableColumn<Customer , String> colAddress ;  //Table column for customer address
    
    @FXML
    private TableColumn<Customer , String> colPhoneNumber ;  //Table column for customer phone number
    
    @FXML
    private TableColumn<Customer , String> colHaveInsurance ;  //Table column for insurance status
    
    @FXML
    private Button Back ;  //Button to switch back to the Customer page
    
    @FXML
    public void initialize() 
    {
        ActiveRentalCustomers() ;  //view customers with active rentals
    }
    
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
    void ActiveRentalCustomers()
    {
    	ObservableList<Customer> customerList = FXCollections.observableArrayList() ;
    	
    	String sql = "SELECT * FROM Customer WHERE Customer_Number IN (SELECT Customer_Number FROM Rental WHERE Rental_Number IN (SELECT Rental_Number FROM Involve WHERE R_Status = 'active')) ;" ;  //SQL query
    	try (Connection conn = JDBC.getConnection() ; PreparedStatement stmt = conn.prepareStatement(sql) ; ResultSet rs = stmt.executeQuery())  //Establish connection to the database , Create a statement to execute SQL , and Execute query and retrieve results
        {
    		while (rs.next()) 
            {
    			//Retrieve data from each column in the current row:
                int customerNumber = rs.getInt("Customer_Number") ;
                String nationalID = rs.getString("National_ID_Number") ;
                String fullName = rs.getString("Full_Name") ;
                String address = rs.getString("Address") ;
                String phoneNumber = rs.getString("Phone_Number") ;
                String haveInsurance = rs.getString("Have_an_Insurance") ;

                //Create a new Customer object and add it to the list:
                Customer customer = new Customer(customerNumber , nationalID , fullName , address , phoneNumber , haveInsurance) ;
                customerList.add(customer) ;
            }
        }
    	catch (SQLException e) 
        {
            e.printStackTrace() ;  //Print stack trace in case of an SQL exception
        }
    	
    	customerTable.setItems(customerList) ;  //Populate the TableView with the customer data

        //Set cell value factories for each column to specify how data should be displayed:
        colCustomerNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCustomerNumber()).asObject()) ;
        colNationalID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNationalId())) ;
        colFullName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName())) ;
        colAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress())) ;
        colPhoneNumber.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber())) ;
        colHaveInsurance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHaveInsurance())) ;
    }
    
    public void setStage(Stage stage)  //this Method is to set the primary stage and called from the Main class   
    {
    	this.stage = stage ;  //Set the stage reference for this controller
    	stage.setTitle("LifeRental") ;  //Set the title of the application window
    }
    
}
