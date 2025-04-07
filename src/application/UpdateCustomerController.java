package application ;

//necessary classes and libraries:
import java.io.IOException;  //for handling IO exceptions
import javafx.event.ActionEvent ;  //Represents an action event, such as button clicks
import javafx.fxml.* ;  //For loading FXML files
import javafx.scene.Parent ;  //Represents the root node of the scene graph
import javafx.scene.Scene ;  //Represents the JavaFX scene
import javafx.scene.control.Alert ;  //Provides the ability to show alert dialogs
import javafx.scene.control.Alert.AlertType ;  //Specifies types of alerts (e.g., ERROR, INFORMATION)
import javafx.scene.control.* ;  //Imports various JavaFX controls like Button, TextField, PasswordField, etc
import javafx.stage.Stage ;  //Represents the JavaFX window or stage
import java.sql.* ;  //Import the Java SQL package to handle database connections and operations
import javafx.collections.FXCollections ;  //For creating observable lists
import javafx.collections.ObservableList ;  //For observable lists
import javafx.beans.property.SimpleIntegerProperty ;  //For wrapping integer properties
import javafx.beans.property.SimpleStringProperty ;  //For wrapping string properties

public class UpdateCustomerController 
{
	private Stage stage ;  //Reference to the main stage of the application
    private Scene scene ;  //Reference to the current scene
    private Parent root ;  //Reference to the root node of the FXML layout
    
    @FXML
    private TextField txtName;  // TextField to input customer name
    
    @FXML
    private Button Back ;  //Button to switch back to the customr page
    
    @FXML
    private Button updatebutton  , View ;
    
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
    private TextField U_NationalID , U_FullName , U_Address , U_PhoneNumber , U_HaveanInsurance ;
    
    //get the customer name from the user
	//make sure it exists
	//fetch and view the record of the customer if it exists
	//customer number can't be chaged
	//ask for the new values for the other attributes 
	//create connection and update to the database
    
    @FXML
    public void initialize() 
    {
    	//when button View is clicked:
    	View.setOnAction(this::CheckandView);  //check if the customer exists and view the original record of this customer
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
            root = FXMLLoader.load(getClass().getResource("Customer.fxml")) ;  //Load the new scene
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
    void Update(ActionEvent event) 
    {
        String customerName = txtName.getText().toUpperCase().trim();  //Retrieve the customer name from the text field, convert to uppercase and trim leading/trailing spaces
        
        try (Connection conn = JDBC.getConnection())  //Establish connection to the database
        {
            String updateQuery = "UPDATE Customer SET National_ID_Number = ? , Full_Name = ? , Address = ? , Phone_Number = ? , Have_an_Insurance = ? WHERE Full_Name = ? ;" ;  //SQL query to update customer details based on their full name
            PreparedStatement updateStmt = conn.prepareStatement(updateQuery) ;  //Prepare the SQL statement with the update query

            //Set the values for the placeholders in the update query:
            updateStmt.setString(1, U_NationalID.getText().trim()) ;
            updateStmt.setString(2, U_FullName.getText().trim()) ;
            updateStmt.setString(3, U_Address.getText().trim()) ;
            updateStmt.setString(4, U_PhoneNumber.getText().trim()) ;
            updateStmt.setString(5, U_HaveanInsurance.getText().trim()) ; 
            updateStmt.setString(6, customerName) ;

            int rowsAffected = updateStmt.executeUpdate() ;  //Execute the update query and capture the number of rows affected
            if (rowsAffected > 0) 
            {
                displayMessage("Customer updated successfully!", "info");  //If at least one row was affected, display a success message
            } 
            else 
            {
                displayMessage("Update failed!", "error");
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();  //Print stack trace in case of an SQL exception
            displayMessage("Database error!", "error");  //Show error if an issue occurs
        }
    }

    private void CheckandView(ActionEvent event)
    {
        ObservableList<Customer> customerList = FXCollections.observableArrayList();  //Create an observable list to hold customer data
        
        String customerName = txtName.getText().toUpperCase().trim();  //Retrieve the customer name from the text field, convert to uppercase and trim leading/trailing spaces

        if (customerName.isEmpty()) 
        {
            displayMessage("Please enter a customer name!", "error");
            return;
        }
        
        String fetchQuery = "SELECT * FROM Customer WHERE Full_Name = ? ;" ;  //SQL query to get the original record 

        try (Connection conn = JDBC.getConnection())  //Establish connection to the database
        {
            PreparedStatement fetchStmt = conn.prepareStatement(fetchQuery);  //Prepare the SQL statement with the fetch query
            fetchStmt.setString(1, customerName);  //Set the customer name parameter in the query
            ResultSet rs = fetchStmt.executeQuery();  //Execute the query and store the result in a ResultSet

            if (!rs.next()) 
            {
                displayMessage("Customer not found!", "error");  //Show error if customer is not found
                return;
            }

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

            customerTable.setItems(customerList) ;  //Populate the TableView with the customer data

            //Set cell value factories for each column to specify how data should be displayed:
            colCustomerNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCustomerNumber()).asObject()) ;
            colNationalID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNationalId())) ;
            colFullName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFullName())) ;
            colAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress())) ;
            colPhoneNumber.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber())) ;
            colHaveInsurance.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHaveInsurance())) ;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            displayMessage("Database error!", "error");  //Show error if an issue occurs
        }
    }
   
    public void setStage(Stage stage)  //this Method is to set the primary stage and called from the Main class   
    {
    	this.stage = stage ;  //Set the stage reference for this controller
    	stage.setTitle("LifeRental") ;  //Set the title of the application window
    }
}
