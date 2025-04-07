package application ;

//necessary classes and libraries:
import javafx.event.ActionEvent ;  //Represents an action event, such as button clicks
import javafx.fxml.* ;  //For loading FXML files
import javafx.scene.Parent ;  //Represents the root node of the scene graph
import javafx.scene.Scene ;  //Represents the JavaFX scene
import javafx.scene.control.Alert ;  //Provides the ability to show alert dialogs
import javafx.scene.control.Alert.AlertType ;  //Specifies types of alerts (e.g., ERROR, INFORMATION)
import javafx.scene.control.* ;  //Imports various JavaFX controls like Button, TextField, PasswordField, etc
import javafx.scene.image.* ;  //JavaFX component for using and displaying images
import javafx.stage.Stage ;  //Represents the JavaFX window or stage
import java.io.* ;  //For file handling and IO exceptions
import java.sql.* ;  //Import the Java SQL package to handle database connections and operations

public class SigninController 
{
	private Stage stage ;  //Reference to the main stage of the application
    private Scene scene ;  //Reference to the current scene
    private Parent root ;  //Reference to the root node of the FXML layout
    
    public static int Employee_Number ;  //Static variable to store employee number
    public static String E_Name ;  //Static variable to store employee name
    public static String E_Password ;  //Static variable to store employee password
    public static Date WorkingStart_Date ;  //Static variable to store employee start date
    public static Date WorkingEnd_Date ;  //Static variable to store employee end date
    
    @FXML
    private Button button_Login ;  //Button to trigger the login process
    
    @FXML
    private Button Back ;  //Button to switch back to the Employee Sign-in/Sign-up page
    
    @FXML
    private TextField txtName ;  //TextField for entering the username
    
    @FXML
    private PasswordField txtPassword ;  //PasswordField for entering the password
    
    @FXML
    private ImageView LogoImage , Phone , Facebook , Address , Signin ;  //ImageViews
    
    @FXML
    public void initialize() 
    {
        //load images into their respective ImageViews:
    	loadImage(LogoImage , "C:\\Users\\User\\Desktop\\DataBase\\Images\\HospitalLogo.jpg") ;
        loadImage(Phone , "C:\\Users\\User\\Desktop\\DataBase\\Images\\Phone.png") ;
        loadImage(Facebook , "C:\\Users\\User\\Desktop\\DataBase\\Images\\Facebook.png") ;
        loadImage(Address , "C:\\Users\\User\\Desktop\\DataBase\\Images\\Address.png") ;
        loadImage(Signin , "C:\\Users\\User\\Desktop\\DataBase\\Images\\Sign-in.jpg") ;
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
    void btnSign_in(ActionEvent event)  //Method executed when the "Sign-in" button is clicked
    {
        if (txtName.getText().isEmpty())  //Check if the username field is empty
        {
            displayMessage("Please enter the user name" , "error") ;
            return ;
        }

        if (txtPassword.getText().isEmpty())  //Check if the password field is empty
        {
            displayMessage("Please enter the password" , "error") ;
            return ;
        }

        String sql = "SELECT * FROM Employee WHERE E_Name = ? AND E_Password = ? ;" ;  //SQL query to authenticate the user
        try (Connection conn = JDBC.getConnection() ; PreparedStatement stmt = conn.prepareStatement(sql))  //Establish a connection to the database and Prepare the SQL statement 
        {
            //Set parameters for the SQL query:
        	stmt.setString(1 , txtName.getText()) ;
            stmt.setString(2 , txtPassword.getText()) ;
            
            //Execute the query and process the results:
            try (ResultSet rs = stmt.executeQuery())
            {
                if (rs.next())  //Check if a matching record was found
                {
                    //Retrieve employee details from the ResultSet:
                	Employee_Number = rs.getInt("Employee_Number") ;
                    E_Name = rs.getString("E_Name") ;
                    E_Password = rs.getString("E_Password") ;
                    WorkingStart_Date = rs.getDate("WorkingStart_Date") ;
                    WorkingEnd_Date = rs.getDate("WorkingEnd_Date") ;
                    
                    //Transition to the next scene:
                    stage = (Stage) button_Login.getScene().getWindow() ;  //Get the current stage
                    stage.close() ;  //Close the current stage
                    root = FXMLLoader.load(getClass().getResource("Tables.fxml")) ;  //Load the new scene
                    scene = new Scene(root) ;  //Set the scene
                    stage.setScene(scene) ;  //Apply the new scene to the stage
                    stage.show() ;  //Show the stage with the new scene
                } 
                else 
                {
                    displayMessage("Incorrect Name or Password! Try again" , "error") ;  //Show error if Name or Password is uncorrect 
                }
            }
        } 
        catch (SQLException | IOException e) 
        {
            e.printStackTrace() ;  //Print any exceptions that occur during the process
            displayMessage("Error during login process. Please try again later." , "error") ;  //Show error if login fails
        }
    }
    
    @FXML 
    void GoBack(ActionEvent event)  //Method executed when the "Back!" button is clicked
    {
    	try
    	{
    		stage = (Stage) Back.getScene().getWindow() ;  //Get the current stage
            stage.close() ;  //Close the current stage
            root = FXMLLoader.load(getClass().getResource("S.fxml")) ;  //Load the new scene
            scene = new Scene(root , 700 , 500) ;  //Set the scene
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
