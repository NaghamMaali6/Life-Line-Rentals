package application;

//necessary classes and libraries:
import javafx.event.ActionEvent ;  //Represents an action event, such as button clicks
import javafx.fxml.* ;  //For loading FXML files
import javafx.scene.Parent ;  //Represents the root node of the scene graph
import javafx.scene.Scene ;  //Represents the JavaFX scene
import javafx.scene.control.Alert.AlertType ;  //Specifies types of alerts (e.g., ERROR, INFORMATION)
import javafx.scene.control.* ;  //Imports various JavaFX controls like Button, TextField, PasswordField, etc
import javafx.stage.Stage ;  //Represents the JavaFX window or stage
import java.io.* ;  //For file handling and IO exceptions
import java.sql.* ;  //Import the Java SQL package to handle database connections and operations
import javafx.collections.FXCollections ;  //For creating observable lists
import javafx.collections.ObservableList ;  //For observable lists
import javafx.beans.property.SimpleIntegerProperty ;  //For wrapping integer properties
import javafx.beans.property.SimpleStringProperty ;  //For wrapping string properties

public class RentalController 
{
	private Stage stage ;  //Reference to the main stage of the application
    private Scene scene ;  //Reference to the current scene
    private Parent root ;  //Reference to the root node of the FXML layout
    
    @FXML
    private TableView<Rental> RentalTable;

    @FXML
    private TableColumn<Rental, Integer> colRentalNumber;

    @FXML
    private TableColumn<Rental, Integer> colCustomerNumber;

    @FXML
    private TableColumn<Rental, Integer> colEmployeeNumber;

    @FXML
    private TableColumn<Rental, Integer> colEquipmentNumber;

    @FXML
    private TableColumn<Rental, Integer> colInvoiceNumber;

    @FXML
    private TableColumn<Rental, String> colStartDate;

    @FXML
    private TableColumn<Rental, String> colEndDate;

    @FXML
    private TableColumn<Rental, String> colContractImage;
    
    @FXML
    private Button add , track , findRentalsNumber , findactiverentals , laterentals , display , rentalswithinsurance ;

    @FXML
    private Button Back;
    
    @FXML
    public void initialize() 
    {
        ViewRentals();
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
            root = FXMLLoader.load(getClass().getResource("Tables.fxml")) ;  //Load the new scene
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
    
    private void ViewRentals() 
    {
        ObservableList<Rental> rentalList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Rental";

        try (Connection conn = JDBC.getConnection() ; PreparedStatement stmt = conn.prepareStatement(sql) ; ResultSet rs = stmt.executeQuery()) 
        {

            while (rs.next())
            {
                int rentalNumber = rs.getInt("Rental_Number");
                int customerNumber = rs.getInt("Customer_Number");
                int employeeNumber = rs.getInt("Employee_Number");
                int equipmentNumber = rs.getInt("Equipment_Number");
                int invoiceNumber = rs.getInt("Invoice_Number");
                Date startDate = rs.getDate("Start_Date");
                Date endDate = rs.getDate("End_Date");
                String contractImage = rs.getString("Contract_Image");

                Rental rental = new Rental(rentalNumber, customerNumber, employeeNumber, equipmentNumber, invoiceNumber, startDate, endDate, contractImage);
                rentalList.add(rental);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        RentalTable.setItems(rentalList);

        colRentalNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRental_Number()).asObject());
        colCustomerNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCustomer_Number()).asObject());
        colEmployeeNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEmployee_Number()).asObject());
        colEquipmentNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEquipment_Number()).asObject());
        colInvoiceNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getInvoice_Number()).asObject());
        colStartDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStart_Date().toString()));
        colEndDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEnd_Date().toString()));
        colContractImage.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContract_Image()));
    }
    
    @FXML
    void AddRental(ActionEvent event)
    {
    	try
    	{
    		stage = (Stage) add.getScene().getWindow() ;  //Get the current stage
            stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("AddRental.fxml")) ;  //Load the new scene
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
    void Track(ActionEvent event)
    {
    	try
    	{
    		stage = (Stage) track.getScene().getWindow() ;  //Get the current stage
            stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("TrackEmployee.fxml")) ;  //Load the new scene
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
    void findRentalsNumber(ActionEvent event)
    {
    	try
    	{
    		stage = (Stage) findRentalsNumber.getScene().getWindow() ;  //Get the current stage
            stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("FindRentalsNumber.fxml")) ;  //Load the new scene
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
    void findactiverentals(ActionEvent event)
    {
    	try
    	{
    		stage = (Stage) findactiverentals.getScene().getWindow() ;  //Get the current stage
            stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("Findactiverentals.fxml")) ;  //Load the new scene
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
    void laterentals(ActionEvent event)
    {
    	try
    	{
    		stage = (Stage) laterentals.getScene().getWindow() ;  //Get the current stage
            stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("Laterentals.fxml")) ;  //Load the new scene
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
    void Display(ActionEvent event)
    {
    	try
    	{
    		stage = (Stage) display.getScene().getWindow() ;  //Get the current stage
            stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("Display.fxml")) ;  //Load the new scene
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
    void rentalswithinsurance(ActionEvent event)  
    {
    	try
    	{
    		stage = (Stage) rentalswithinsurance.getScene().getWindow() ;  //Get the current stage
            stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("Rentalswithinsurance.fxml")) ;  //Load the new scene
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
