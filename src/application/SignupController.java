package application ;

//necessary classes and libraries:
import javafx.event.ActionEvent ;  //Represents an action event, such as button clicks
import javafx.fxml.* ;  //For loading FXML files
import javafx.scene.Parent ;  //Represents the root node of the scene graph
import javafx.scene.Scene ;  //Represents the JavaFX scene
import javafx.scene.control.Alert.AlertType ;  //Specifies types of alerts (e.g., ERROR, INFORMATION)
import javafx.scene.control.* ;  //Imports various JavaFX controls like Button, TextField, PasswordField, etc
import javafx.scene.image.* ;  //JavaFX component for using and displaying images
import javafx.stage.Stage ;  //Represents the JavaFX window or stage
import java.io.* ;  //For file handling and IO exceptions
import java.sql.* ;  //Import the Java SQL package to handle database connections and operations
import java.time.LocalDate ;  //For handling date values

public class SignupController 
{

	private Stage stage ;  //Reference to the main stage of the application
    private Scene scene ;  //Reference to the current scene
    private Parent root ;  //Reference to the root node of the FXML layout

    @FXML
    private Button button_Signup ;  //Button to trigger the sign-up process
    
    @FXML
    private TextField txtName ;  //TextField for entering the username
    
    @FXML
    private PasswordField txtPassword ;  //PasswordField for entering the password
    
    @FXML
    private ImageView LogoImage , Phone , Facebook , Address , Signup ;  //ImageViews
    
    @FXML
    private DatePicker Date ;  //DatePicker for selecting the working start date
    
    @FXML
    private Button Back ;  //Button to switch back to the Employee Sign-in/Sign-up page

    @FXML
    public void initialize() 
    {
    	//load images into their respective ImageViews:
        loadImage(LogoImage , "C:\\Users\\User\\Desktop\\DataBase\\Images\\HospitalLogo.jpg") ;
        loadImage(Phone , "C:\\Users\\User\\Desktop\\DataBase\\Images\\Phone.png") ;
        loadImage(Facebook , "C:\\Users\\User\\Desktop\\DataBase\\Images\\Facebook.png") ;
        loadImage(Address , "C:\\Users\\User\\Desktop\\DataBase\\Images\\Address.png") ;
        loadImage(Signup , "C:\\Users\\User\\Desktop\\DataBase\\Images\\Sign-up.jpg") ;
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

    @FXML
    void btnSign_up(ActionEvent event)  //Method executed when the "Sign-up" button is clicked 
    {
        //Get the entered username, password, and date:
    	String name = txtName.getText().trim() ;  //Retrieve and trim the username
        String password = txtPassword.getText().trim() ;  //Retrieve and trim the password
        LocalDate startDate = Date.getValue() ;  //Get the selected date from the DatePicker
        /*.tirm() function is to Remove spaces before/after the user input*/

        if (name.isEmpty())  //Check if the username field is empty 
        {
            displayMessage("Please enter the username." , "error") ;
            return ;
        }

        if (password.isEmpty())  //Check if the password field is empty 
        {
            displayMessage("Please enter the password." , "error") ;
            return ;
        }

        if (startDate == null)  //Check if the Date field is empty 
        {
            displayMessage("Please select a working start date." , "error") ;
            return ;
        }

        String sqlCheck = "SELECT COUNT(*) FROM Employee WHERE E_Name = ? ;" ;  //Query to check for existing username
        String sqlInsert = "INSERT INTO Employee (Employee_Number , E_Name , WorkingStart_Date , WorkingEnd_Date , E_Password) VALUES (? , ? , ? , ? , ?) ;" ;  //Query to insert new employee record

        try (Connection conn = JDBC.getConnection() ;  //Establish database connection
             PreparedStatement checkStmt = conn.prepareStatement(sqlCheck) ;  //Prepare the check statement
             PreparedStatement insertStmt = conn.prepareStatement(sqlInsert))  //Prepare the insert statement
        {
        	//Check for duplicate user(Check if username already exists):
            checkStmt.setString(1 , name) ;  //Replace the first '?' with the username
            ResultSet rs = checkStmt.executeQuery() ;  //Execute the query
            if (rs.next() && rs.getInt(1) > 0) 
            {
                displayMessage("This username already exists. Please choose another one." , "error") ;
                return ;
            }

            //Insert new employee record:
            insertStmt.setInt(1 , generateEmployeeNumber(conn)) ;  //Generate and set unique Employee_Number
            insertStmt.setString(2 , name) ;  //Set the username
            insertStmt.setDate(3 , java.sql.Date.valueOf(startDate)) ;  //Convert LocalDate to SQL Date(a database-friendly format)
            insertStmt.setNull(4 , java.sql.Types.DATE) ;  //Set WorkingEnd_Date as null
            insertStmt.setString(5 , password) ;  //Set the password

            int rowsAffected = insertStmt.executeUpdate() ;  //Execute the insert statement
            if (rowsAffected > 0) 
            {
                displayMessage("Signup successful!" , "information") ;  //Show success message
                
                //printAllEmployees() ; //Print all employees after successful signup to test signing-up process

                //Transition to the Sign-in page:
                stage = (Stage) button_Signup.getScene().getWindow() ;  //Get the current scene
                stage.close() ;  //Close the current scene
                root = FXMLLoader.load(getClass().getResource("Signin.fxml")) ;  //Load the new scene
                scene = new Scene(root) ;  //Set the scene
                stage.setScene(scene) ;  //Apply the new scene to the stage
                stage.show() ;  //Show the stage with the new scene
            }
        } 
        catch (SQLException | IOException e) 
        {
            displayMessage("An error occurred: " + e.getMessage() , "error") ;  //Show error If something goes wrong
        }
    }

    private int generateEmployeeNumber(Connection conn) throws SQLException  //Method to generate a unique employee number
    {
        String sql = "SELECT MAX(Employee_Number) FROM Employee ;" ;  //Get the highest employee number
        try (Statement stmt = conn.createStatement() ; ResultSet rs = stmt.executeQuery(sql)) 
        {
            if (rs.next()) 
            {
                return (rs.getInt(1) + 1) ; //Increment the highest Employee_Number by 1 for the new employee
            }
        }
        return 1 ; //Start at 1 if there are no employee
    }
    
    /* 
    method to test signing-up process
    public void printAllEmployees() 
    {
        String sql = "SELECT * FROM Employee" ; //Query to fetch all employee records

        try (Connection conn = JDBC.getConnection() ; Statement stmt = conn.createStatement() ; ResultSet rs = stmt.executeQuery(sql)) 
        {
            System.out.println("Employee Table:") ;
            System.out.println("=========================") ;
            while (rs.next()) 
            {
                Employee employee = new Employee(rs.getInt("Employee_Number") , rs.getString("E_Name") , rs.getDate("WorkingStart_Date") , rs.getDate("WorkingEnd_Date") , rs.getString("E_Password")) ;  //Create an Employee object for each record
                employee.printDetails() ;  //Print details using the Employee class method
            }
        } 
        catch (SQLException e) 
        {
            System.err.println("Error fetching employees: " + e.getMessage()) ;
        }
    }
    */


    public void setStage(Stage stage)  //this Method is to set the primary stage and called from the Main class   
    {
    	this.stage = stage ;  //Set the stage reference for this controller
    	stage.setTitle("LifeRental") ;  //Set the title of the application window
    }
}
