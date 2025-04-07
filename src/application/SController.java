package application;

//necessary classes and libraries:
import javafx.fxml.* ;  //For loading FXML files
import javafx.scene.Parent ;  //Represents the root node of the scene graph
import javafx.scene.Scene ;  //Represents the JavaFX scene
import javafx.scene.control.Button ;  //JavaFX button component
import javafx.scene.image.* ;  //JavaFX component for using and displaying images
import javafx.stage.Stage ;  //Represents the JavaFX window or stage
import javafx.event.ActionEvent ;  //Represents an action event, such as button clicks
import java.io.File ;  //Represents file and directory paths

public class SController 
{

	private Stage stage ;  //Reference to the main primary stage of the application
    private Scene scene ;  //Reference to the current scene
    private Parent root ;  //Reference to the root node of the FXML layout

    @FXML
    private ImageView logo ;

    @FXML
    private Button signInButton ;  //Sign-In button

    @FXML
    private Button signUpButton ;  //Sign-Up button

    @FXML
    public void initialize() 
    { 
        String imagePath = "C:\\Users\\User\\Desktop\\DataBase\\Images\\HospitalLogo.jpg" ;  //Define the file path for the logo image 

      //Load the image and set it to the ImageView:
        File file = new File(imagePath) ;  //Create a File object for the image
        
        if (file.exists())  //Check if the image file exists at the given path
        {
        	//If the file exists: 
        	Image image = new Image(file.toURI().toString()) ;  //create an Image object from the file and set it to the ImageView  
            logo.setImage(image) ;  //Display the logo image in the ImageView
        } 
        else 
        {
            System.err.println("Image not found: " + imagePath) ;  //If the file does not exist, print an error message to the console
        }
    }

    @FXML
    private void handleSignInAction(ActionEvent event)  //Method to handle the Sign In button click 
    {
        try 
        {
        	stage = (Stage) signInButton.getScene().getWindow() ;  //Get the current stage
        	stage.close() ;  //Close the current stage
        	root = FXMLLoader.load(getClass().getResource("Signin.fxml")) ;  //Load the new scene
        	scene = new Scene(root) ;  //Set the scene dimensions
            stage.setScene(scene) ;  //Apply the new scene to the stage
            stage.show() ;  //Show the stage with the new scene
        } 
        catch (Exception e) 
        {
            e.printStackTrace() ;  //Print any exceptions that occur during the process
        }
    }

    @FXML
    private void handleSignUpAction(ActionEvent event)  //Method to handle the Sign Up button click 
    {
        try 
        {
        	stage = (Stage) signUpButton.getScene().getWindow() ;  //Get the current stage
        	stage.close() ;  //Close the current stage
        	root = FXMLLoader.load(getClass().getResource("Signup.fxml")) ;  //Load the new scene
        	scene = new Scene(root) ;  //Set the scene dimensions
            stage.setScene(scene) ;  //Apply the new scene to the stage
            stage.show() ;  //Show the stage with the new scene
        } 
        catch (Exception e) 
        {
            e.printStackTrace() ;  //Print any exceptions that occur during the process
        }
    }

    public void setStage(Stage stage)  //this Method is to set the primary stage and called from the Main class   
    {
    	this.stage = stage ;  //Set the stage reference for this controller
    	stage.setTitle("LifeRental") ;  //Set the title of the application window
    }
}
