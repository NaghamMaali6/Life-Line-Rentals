package application ;

import javafx.event.ActionEvent ;  //Represents an action event, such as button clicks
import javafx.fxml.* ;  //For loading FXML files
import javafx.scene.Parent ;  //Represents the root node of the scene graph
import javafx.scene.Scene ;  //Represents the JavaFX scene
import javafx.scene.image.* ;  //JavaFX component for using and displaying images
import javafx.stage.Stage ;  //Represents the JavaFX window or stage
import java.io.* ;  //For file handling and IO exceptions
import javafx.scene.control.*;  //Imports various JavaFX controls like Button, TextField, PasswordField, etc
import javafx.scene.control.Alert ;  //Provides the ability to show alert dialogs
import javafx.scene.control.Alert.AlertType ;  //Specifies types of alerts (e.g., ERROR, INFORMATION)

public class TablesController 
{
	private Stage stage ;  //Reference to the main stage of the application
    private Scene scene ;  //Reference to the current scene
    private Parent root ;  //Reference to the root node of the FXML layout
	
	@FXML
    private ImageView Customer , Equipment , Rental , Payment , Invoice , Location , Employee , Supplier , Maintenance_Request , Workshop , Logout ;  //images
	@FXML
	private Button CustomerB , EquipmentB , RentalB , PaymentB , InvoiceB , LocationB , EmployeeB , SupplierB , Maintenance_RequestB , WorkshopB , LogoutB ;  //button
	
	@FXML
    public void initialize() 
	{
		//load images into their respective ImageViews:
		loadImage(Customer , "C:\\Users\\User\\Desktop\\DataBase\\Images\\Customer.png") ;
        loadImage(Equipment , "C:\\Users\\User\\Desktop\\DataBase\\Images\\Equipment.png") ;
        loadImage(Rental , "C:\\Users\\User\\Desktop\\DataBase\\Images\\Rental.png") ;
        loadImage(Payment , "C:\\Users\\User\\Desktop\\DataBase\\Images\\Payment.png") ;
        loadImage(Invoice , "C:\\Users\\User\\Desktop\\DataBase\\Images\\Invoice.jpg") ;
        loadImage(Location , "C:\\Users\\User\\Desktop\\DataBase\\Images\\Location.png") ;
        loadImage(Employee , "C:\\Users\\User\\Desktop\\DataBase\\Images\\Employee.png") ;
        loadImage(Supplier , "C:\\Users\\User\\Desktop\\DataBase\\Images\\Supplier.png") ;
        loadImage(Maintenance_Request , "C:\\Users\\User\\Desktop\\DataBase\\Images\\Maintenance_Request.png") ;
        loadImage(Workshop , "C:\\Users\\User\\Desktop\\DataBase\\Images\\Workshop.png") ;
        loadImage(Logout , "C:\\Users\\User\\Desktop\\DataBase\\Images\\Log-out.png") ;
    }

	private void loadImage(ImageView imageView , String imagePath)  //Method to load an image into an ImageView
    {
        File file = new File(imagePath) ;  //Create a File object for the image path
        if (file.exists()) 
        {
        	//If the file exists:
        	Image image = new Image(file.toURI().toString()) ;  //Load the image from the file
            imageView.setImage(image) ;  //Set the image in the ImageView
        } 
        else 
        {
            System.err.println("Image not found: " + imagePath) ;  //If the file does not exist, print an error message to the console
        }
    }
	
	private void displayMessage(String message , String messageType)  //Method to display a message alert to the user 
    {
        Alert alert = new Alert("error".equalsIgnoreCase(messageType) ? AlertType.ERROR : AlertType.INFORMATION) ;  //Determine alert type
        alert.setContentText(message) ;  //Set the alert message
        alert.showAndWait() ;  //Display the alert and wait for user acknowledgment
    }
	
    @FXML
    void btnlog_out(ActionEvent event)  //Method to log-out(switch back to Sign-in/Sign-up page)
    {
    	try 
    	{
            stage = (Stage) Logout.getScene().getWindow() ;  //Get the current stage
            stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("S.fxml")) ;  //Load the new scene
            scene = new Scene(root , 700 , 500) ;  //Set the scene dimensions
            stage.setScene(scene) ;  //Apply the new scene to the stage
            stage.show() ;  //Show the stage with the new scene
        }
    	catch (IOException e) 
    	{
            displayMessage("An error occurred: " + e.getMessage() , "error") ;  //Show error If something goes wrong
        }
    }
    
    @FXML
    void btnCustomer(ActionEvent event)
    {
    	try
    	{
    		stage = (Stage) CustomerB.getScene().getWindow() ;  //Get the current stage
    		stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("Customer.fxml")) ;  //Load the new scene
            scene = new Scene(root , 1100 , 600) ;  //Set the scene dimensions
            stage.setScene(scene) ;  //Apply the new scene to the stage
            stage.show() ;  //Show the stage with the new scene
        }
    	catch (IOException e) 
    	{
            displayMessage("An error occurred: " + e.getMessage() , "error") ;  //Show error If something goes wrong
        }
    }
    
    @FXML
    void btnEquipment(ActionEvent event)
    {
    	try
    	{
    		stage = (Stage) EquipmentB.getScene().getWindow() ;  //Get the current stage
    		stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("Equipment.fxml")) ;  //Load the new scene
            scene = new Scene(root , 1100 , 600) ;  //Set the scene dimensions
            stage.setScene(scene) ;  //Apply the new scene to the stage
            stage.show() ;  //Show the stage with the new scene
        }
    	catch (IOException e) 
    	{
            displayMessage("An error occurred: " + e.getMessage() , "error") ;  //Show error If something goes wrong
        }
    }
    
    @FXML
    void btnRental(ActionEvent event)
    {
    	try
    	{
    		stage = (Stage) RentalB.getScene().getWindow() ;  //Get the current stage
    		stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("Rental.fxml")) ;  //Load the new scene
            scene = new Scene(root , 1100 , 600) ;  //Set the scene dimensions
            stage.setScene(scene) ;  //Apply the new scene to the stage
            stage.show() ;  //Show the stage with the new scene
        }
    	catch (IOException e) 
    	{
            displayMessage("An error occurred: " + e.getMessage() , "error") ;  //Show error If something goes wrong
        }
    }
    
    @FXML
    void btnPayment(ActionEvent event)
    {
    	try
    	{
    		stage = (Stage) PaymentB.getScene().getWindow() ;  //Get the current stage
    		stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("Payment.fxml")) ;  //Load the new scene
            scene = new Scene(root , 1100 , 600) ;  //Set the scene dimensions
            stage.setScene(scene) ;  //Apply the new scene to the stage
            stage.show() ;  //Show the stage with the new scene
        }
    	catch (IOException e) 
    	{
            displayMessage("An error occurred: " + e.getMessage() , "error") ;  //Show error If something goes wrong
        }
    }
    
    @FXML
    void btnInvoice(ActionEvent event)
    {
    	try
    	{
    		stage = (Stage) InvoiceB.getScene().getWindow() ;  //Get the current stage
    		stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("Invoice.fxml")) ;  //Load the new scene
            scene = new Scene(root , 1100 , 600) ;  //Set the scene dimensions
            stage.setScene(scene) ;  //Apply the new scene to the stage
            stage.show() ;  //Show the stage with the new scene
        }
    	catch (IOException e) 
    	{
            displayMessage("An error occurred: " + e.getMessage() , "error") ;  //Show error If something goes wrong
        }
    }
    
    @FXML
    void btnLocation(ActionEvent event)
    {
    	try
    	{
    		stage = (Stage) LocationB.getScene().getWindow() ;  //Get the current stage
    		stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("Location.fxml")) ;  //Load the new scene
            scene = new Scene(root , 1100 , 600) ;  //Set the scene dimensions
            stage.setScene(scene) ;  //Apply the new scene to the stage
            stage.show() ;  //Show the stage with the new scene
        }
    	catch (IOException e) 
    	{
            displayMessage("An error occurred: " + e.getMessage() , "error") ;  //Show error If something goes wrong
        }
    }
    
    @FXML
    void btnEmployee(ActionEvent event)
    {
    	try
    	{
    		stage = (Stage) EmployeeB.getScene().getWindow() ;  //Get the current stage
    		stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("Employee.fxml")) ;  //Load the new scene
            scene = new Scene(root , 1100 , 600) ;  //Set the scene dimensions
            stage.setScene(scene) ;  //Apply the new scene to the stage
            stage.show() ;  //Show the stage with the new scene
        }
    	catch (IOException e) 
    	{
            displayMessage("An error occurred: " + e.getMessage() , "error") ;  //Show error If something goes wrong
        }
    }
    
    @FXML
    void btnSupplier(ActionEvent event)
    {
    	try
    	{
    		stage = (Stage) SupplierB.getScene().getWindow() ;  //Get the current stage
    		stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("Supplier.fxml")) ;  //Load the new scene
            scene = new Scene(root , 1100 , 600) ;  //Set the scene dimensions
            stage.setScene(scene) ;  //Apply the new scene to the stage
            stage.show() ;  //Show the stage with the new scene
        }
    	catch (IOException e) 
    	{
            displayMessage("An error occurred: " + e.getMessage() , "error") ;  //Show error If something goes wrong
        }
    }
    
    @FXML
    void btnWorkshop(ActionEvent event)
    {
    	try
    	{
    		stage = (Stage) WorkshopB.getScene().getWindow() ;  //Get the current stage
    		stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("Workshop.fxml")) ;  //Load the new scene
            scene = new Scene(root , 1100 , 600) ;  //Set the scene dimensions
            stage.setScene(scene) ;  //Apply the new scene to the stage
            stage.show() ;  //Show the stage with the new scene
        }
    	catch (IOException e) 
    	{
            displayMessage("An error occurred: " + e.getMessage() , "error") ;  //Show error If something goes wrong
        }
    }
    
    @FXML
    void btnMaintenance_Request(ActionEvent event)
    {
    	try
    	{
    		stage = (Stage) Maintenance_RequestB.getScene().getWindow() ;  //Get the current stage
    		stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("Maintenance_Request.fxml")) ;  //Load the new scene
            scene = new Scene(root , 1100 , 600) ;  //Set the scene dimensions
            stage.setScene(scene) ;  //Apply the new scene to the stage
            stage.show() ;  //Show the stage with the new scene
        }
    	catch (IOException e) 
    	{
            displayMessage("An error occurred: " + e.getMessage() , "error") ;  //Show error If something goes wrong
        }
    }
    
    public void setStage(Stage stage)  //this Method is to set the primary stage and called from the Main class   
    {
    	this.stage = stage ;  //Set the stage reference for this controller
    	stage.setTitle("LifeRental") ;  //Set the title of the application window
    }

}
