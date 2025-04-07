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
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty ;  //For wrapping integer properties
import javafx.beans.property.SimpleStringProperty ;  //For wrapping string properties

public class PaymentController
{
	private Stage stage ;  //Reference to the main stage of the application
    private Scene scene ;  //Reference to the current scene
    private Parent root ;  //Reference to the root node of the FXML layout
    
    @FXML
    private TableView<Payment> PaymentTable;

    @FXML
    private TableColumn<Payment, Integer> colPaymentNumber;

    @FXML
    private TableColumn<Payment, Integer> colRentalNumber;

    @FXML
    private TableColumn<Payment, Integer> colNumberOfRentalDays;

    @FXML
    private TableColumn<Payment, Double> colPricePerDay;

    @FXML
    private TableColumn<Payment, String> colPaymentMethod;

    @FXML
    private Button Back;

    @FXML
    public void initialize() 
    {
        ViewPayments();
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
    
    private void ViewPayments() 
    {
        ObservableList<Payment> paymentList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM Payment";

        try (Connection conn = JDBC.getConnection() ; PreparedStatement stmt = conn.prepareStatement(sql) ; ResultSet rs = stmt.executeQuery()) 
        {

            while (rs.next()) 
            {
                int paymentNumber = rs.getInt("Payment_Number");
                int rentalNumber = rs.getInt("Rental_Number");
                int numberOfRentalDays = rs.getInt("Number_of_Rental_Days");
                double pricePerDay = rs.getDouble("Price_per_Day");
                String paymentMethod = rs.getString("Payment_Method");

                Payment payment = new Payment(paymentNumber, rentalNumber, numberOfRentalDays, pricePerDay, paymentMethod);
                paymentList.add(payment);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }

        PaymentTable.setItems(paymentList);

        colPaymentNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPayment_Number()).asObject());
        colRentalNumber.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRental_Number()).asObject());
        colNumberOfRentalDays.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getNumber_of_Rental_Days()).asObject());
        colPricePerDay.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice_per_Day()).asObject());
        colPaymentMethod.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPayment_Method()));
    }
    
    public void setStage(Stage stage)  //this Method is to set the primary stage and called from the Main class   
    {
    	this.stage = stage ;  //Set the stage reference for this controller
    	stage.setTitle("LifeRental") ;  //Set the title of the application window
    }
}
