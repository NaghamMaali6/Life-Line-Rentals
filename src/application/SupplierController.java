package application;

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

public class SupplierController 
{
	private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private TableView<Supplier> SupplierTable;

    @FXML
    private TableColumn<Supplier, Integer> colSupplierNumber;

    @FXML
    private TableColumn<Supplier, String> colSName;

    @FXML
    private TableColumn<Supplier, String> colPhoneNumber;

    @FXML
    private Button Back;

    @FXML
    public void initialize() 
    {
        viewSuppliers();
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
    
    private void viewSuppliers() 
    {
        ObservableList<Supplier> supplierList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Supplier";

        try (Connection conn = JDBC.getConnection() ; PreparedStatement stmt = conn.prepareStatement(sql) ; ResultSet rs = stmt.executeQuery()) 
        {

            while (rs.next()) 
            {
                int supplierNumber = rs.getInt("Supplier_Number");
                String name = rs.getString("S_Name");
                String phoneNumber = rs.getString("Phone_Number");

                Supplier supplier = new Supplier(supplierNumber, name, phoneNumber);
                supplierList.add(supplier);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        SupplierTable.setItems(supplierList);

        colSupplierNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSupplierNumber()).asObject());
        colSName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        colPhoneNumber.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));
    }
    
    public void setStage(Stage stage)  //this Method is to set the primary stage and called from the Main class   
    {
    	this.stage = stage ;  //Set the stage reference for this controller
    	stage.setTitle("LifeRental") ;  //Set the title of the application window
    }
}
