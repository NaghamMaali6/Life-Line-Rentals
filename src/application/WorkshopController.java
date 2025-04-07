package application ;

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

public class WorkshopController 
{
	private Stage stage ;  //Reference to the main stage of the application
    private Scene scene ;  //Reference to the current scene
    private Parent root ;  //Reference to the root node of the FXML layout
    
    @FXML
    private TableView<Workshop> WorkshopTable;

    @FXML
    private TableColumn<Workshop, Integer> colWorkshopNumber;

    @FXML
    private TableColumn<Workshop, String> colWorkshopName;

    @FXML
    private TableColumn<Workshop, String> colAddress;

    @FXML
    private TableColumn<Workshop, String> colPhoneNumber;

    @FXML
    private Button Back;
    
    @FXML
    public void initialize() 
    {
        ViewWorkshops();
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
    
    private void ViewWorkshops() 
    {
        ObservableList<Workshop> workshopList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Workshop";

        try (Connection conn = JDBC.getConnection() ; PreparedStatement stmt = conn.prepareStatement(sql) ; ResultSet rs = stmt.executeQuery()) 
        {

            while (rs.next())
            {
                int workshopNumber = rs.getInt("Workshop_Number");
                String workshopName = rs.getString("Workshop_Name");
                String address = rs.getString("Address");
                String phoneNumber = rs.getString("Phone_Number");

                Workshop workshop = new Workshop(workshopNumber, workshopName, address, phoneNumber);
                workshopList.add(workshop);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        WorkshopTable.setItems(workshopList);

        colWorkshopNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getWorkshop_Number()).asObject());
        colWorkshopName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getWorkshop_Name()));
        colAddress.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
        colPhoneNumber.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone_Number()));
    }
    
    public void setStage(Stage stage)  //this Method is to set the primary stage and called from the Main class   
    {
    	this.stage = stage ;  //Set the stage reference for this controller
    	stage.setTitle("LifeRental") ;  //Set the title of the application window
    }
}
