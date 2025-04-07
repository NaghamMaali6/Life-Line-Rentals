package application;

import javafx.fxml.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.sql.*;

public class EquipmentController {

	private Stage stage;
    private Scene scene;
    private Parent root;
	
	@FXML
    private TableView<Equipment> EquipmentTable;
    
    @FXML
    private ImageView image;
    @FXML
    private TableColumn<Equipment, Integer> Equipment_Number;
    @FXML
    private TableColumn<Equipment, String> Equipment_Name;
    @FXML
    private TableColumn<Equipment, String> Availability;
    @FXML
    private TableColumn<Equipment, Integer> Location_Number;
    @FXML
    private TableColumn<Equipment, Integer> Supplier_Number;
    @FXML
    private TableColumn<Equipment, String> Equipment_Use;

    @FXML
    private Button close;
    @FXML
    private Button search;
    @FXML 
    private TextField textfield;
    @FXML 
    private Button check;
    @FXML
    private Label checklable;
    @FXML
    private TextField checktextfield;
    @FXML
    private TextField newName;
    @FXML
    private TextField newAvailability;
    @FXML
    private TextField newLocation_Number;
    @FXML
    private Button Update;
    @FXML
    private Label newname;
    @FXML
    private Label newAv;
   @FXML
   private Label newloc;
   @FXML
   private TextField sup_name_feild;
   @FXML
   private TextField sup_number_field;
   @FXML
   private TextField sup_phone_field;
   @FXML
   private TextField eq_sup_nameField;
   @FXML
   private TextField eq_sup_numberField;
   @FXML
   private TextField eq_sup_availabilityField;
   @FXML
   private TextField eq_sup_locationnumberField;
   @FXML
   private TextField eq_sup_equipmentuseField; 
   @FXML
   private TextField eq_sup_supnumberField; 
   @FXML
   private Button Add_supplier;
   @FXML
   private Label labl1_1;
   @FXML 
   private Label lable_2;
   @FXML
   private TextField location_phone_field;
   @FXML
   private TextField location_address_field;
   @FXML
   private Label location_lable;
   @FXML
   private TextField eq_name;
   @FXML
   private Button view_location;
  @FXML
  private TextField eq_de_name_field;
  @FXML
  private Button Delete;
  
  @FXML
  private Button Back;

  @FXML
  void GoBack(ActionEvent event) 
  {
	  Back.setVisible(true);
      try {
          stage = (Stage) Back.getScene().getWindow();
          stage.close();
          root = FXMLLoader.load(getClass().getResource("Tables.fxml"));
          scene = new Scene(root);
          stage.setScene(scene);
          stage.show();
      } catch (IOException e) {
          e.printStackTrace();
          displayMessage("Failed to load the tables view: " + e.getMessage(), "error");
      }
  }
  
    @FXML
    private void handleViewMenuItem() {
        EquipmentTable.setVisible(true);
        close.setVisible(true);
        handleShowEquipment();
    }
    @FXML
    private void handleUpdateEquipmentItem() {
        EquipmentTable.setVisible(true);
        close.setVisible(true);
        Update.setVisible(true);
        newName.setVisible(true);
        newAvailability.setVisible(true);
        newLocation_Number.setVisible(true);
        newname.setVisible(true);
        newAv.setVisible(true);
        newloc.setVisible(true);
        handleShowEquipment();
    }

    @FXML
    private void handleCloseButton() {
        EquipmentTable.setVisible(false);
        close.setVisible(false);
        textfield.setVisible(false);
    	search.setVisible(false);
    	check.setVisible(false);
    	checktextfield.setVisible(false);
    	checklable.setVisible(false);
    	 Update.setVisible(false);
         newName.setVisible(false);
         newAvailability.setVisible(false);
         newLocation_Number.setVisible(false);
         newname.setVisible(false);
         newAv.setVisible(false);
         newloc.setVisible(false);
         sup_name_feild.setVisible(false);
  	   sup_number_field.setVisible(false);
  	   sup_phone_field.setVisible(false);
  	   eq_sup_numberField.setVisible(false);
  	   eq_sup_nameField.setVisible(false);
  	   eq_sup_locationnumberField.setVisible(false);
  	   labl1_1.setVisible(false);
  	   lable_2.setVisible(false);
  	   eq_sup_equipmentuseField.setVisible(false);
  	   eq_sup_supnumberField.setVisible(false);
  	   eq_sup_availabilityField.setVisible(false);
  	   Add_supplier.setVisible(false);
  	 location_phone_field.setVisible(false);
  	location_address_field.setVisible(false);
  	eq_name.setVisible(false);
  	view_location.setVisible(false);
  	location_lable.setVisible(false);
  	Delete.setVisible(false);
	eq_de_name_field.setVisible(false);

           }
    
   

    private void handleShowEquipment() {
    	
    	    ObservableList<Equipment> EquipmentList = FXCollections.observableArrayList();
      
        String sql = "SELECT * FROM Equipment";

        try (Connection conn = JDBC.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int EquipmentNumber = rs.getInt("Equipment_Number");
                String EquipmentName = rs.getString("Equipment_Name");
                String Availability = rs.getString("Availability");
                int LocationNumber = rs.getInt("Location_Number");
                int SupplierNumber = rs.getInt("Supplier_Number");
                String EquipmentUse = rs.getString("Equipment_Use");

                Equipment eq = new Equipment(EquipmentNumber, EquipmentName, Availability, LocationNumber, SupplierNumber, EquipmentUse);
                EquipmentList.add(eq);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Optionally, add user-friendly error handling
            Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load equipment data.", ButtonType.OK);
            alert.showAndWait();
        }

        EquipmentTable.setItems(EquipmentList);

        // Set up correct CellValueFactories
        Equipment_Number.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getid()).asObject());
        Equipment_Name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_Equipment_Name()));
        Availability.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_Availability()));
        Location_Number.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getLocationNumber()).asObject());
        Supplier_Number.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSupplierNumber()).asObject());
        Equipment_Use.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEquipmentUse()));
    }
    
    @FXML
    private void handleSearchForEquipment()
    {
    	textfield.setVisible(true);
    	search.setVisible(true);
    	handleShowEquipment();
    	EquipmentTable.setVisible(true);
        close.setVisible(true);
    	
    }
    
    @FXML
    private void handleSearchForEquipment_buton()
    {
    	
    	    ObservableList<Equipment> EquipmentList_2 = FXCollections.observableArrayList();
    	    String sql = "SELECT * FROM Equipment";
    	    handleViewMenuItem();

            try (Connection conn = JDBC.getConnection();
                 Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                while (rs.next()) {
                    int EquipmentNumber = rs.getInt("Equipment_Number");
                    String EquipmentName = rs.getString("Equipment_Name");
                    String Availability = rs.getString("Availability");
                    int LocationNumber = rs.getInt("Location_Number");
                    int SupplierNumber = rs.getInt("Supplier_Number");
                    String EquipmentUse = rs.getString("Equipment_Use");

                    Equipment eq = new Equipment(EquipmentNumber, EquipmentName, Availability, LocationNumber, SupplierNumber, EquipmentUse);
                    EquipmentList_2.add(eq);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                // Optionally, add user-friendly error handling
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to load equipment data.", ButtonType.OK);
                alert.showAndWait();
            }

            EquipmentTable.setItems(EquipmentList_2);

            // Set up correct CellValueFactories
            Equipment_Number.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getid()).asObject());
            Equipment_Name.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_Equipment_Name()));
            Availability.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get_Availability()));
            Location_Number.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getLocationNumber()).asObject());
            Supplier_Number.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getSupplierNumber()).asObject());
            Equipment_Use.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEquipmentUse()));
    	    
    	    
    	String text = textfield.getText().toLowerCase();// convert the text interd to a lower case
    	ObservableList<Equipment> output = FXCollections.observableArrayList();
    	for(int i = 0; i < EquipmentList_2.size(); i++)
    	{
    		Equipment equipment = EquipmentList_2.get(i);
    	if( equipment.get_Equipment_Name().toLowerCase().contains(text) || String.valueOf(equipment.getLocationNumber()).contains(text) || equipment.get_Availability().toLowerCase().contains(text) )
    	{
    		output.add(equipment);
    	}
    	}
    	
    	EquipmentTable.setItems(output);
    	
    }
   
    @FXML
    private void handelCheckAvailability()
    {
    	check.setVisible(true);
    	checktextfield.setVisible(true);
    	checklable.setVisible(true);
    	close.setVisible(true);
    }
    
   @FXML
   private void CheckAvailability()
   {
	   //get the  equipment name, .trim() to remove spaces from the name
	   String EQname = checktextfield.getText().trim();
	  
	   //get the  availability status from the Database
	   String availabilQuery = "SELECT Availability FROM Equipment WHERE Equipment_Name = '" + EQname + "'";
	   //to access the Database
	   try(Connection conn = JDBC.getConnection();
			   Statement availabl_st = conn.createStatement();//to sent the SQL to the database
			   ResultSet available_rs = availabl_st.executeQuery(availabilQuery)//store the sql result in the resultset
			   ){
		   if(available_rs.next())//loop through the result set
		   {
			   String availability = available_rs.getString("Availability");
			   checklable.setText(availability);
		   }
		   else
		   {
			   checklable.setText("Equipment not found");
		   }
	   }
	   catch (SQLException e) {
	        e.printStackTrace();
	        checklable.setText("Error checking availability.");
	    }
   }
   @FXML
   private void handleUpdateEquipment() {
       // Get the selected equipment from the table
       Equipment equipment = EquipmentTable.getSelectionModel().getSelectedItem();

       // If no equipment is selected, show an error alert
       if (equipment == null) {
           Alert error = new Alert(Alert.AlertType.ERROR, "Select an Equipment to Update...", ButtonType.OK);
           error.showAndWait();
           return; // Stop further execution
       }

       // Define new attributes
       String new_name = newName.getText().trim();
       String new_Availability = newAvailability.getText().trim();
       String new_Location = newLocation_Number.getText().trim();

       // Check if any text field is empty
       if (new_name.isEmpty() || new_Availability.isEmpty() || new_Location.isEmpty()) {
           Alert error = new Alert(Alert.AlertType.ERROR, "Make sure all text fields are filled.", ButtonType.OK);
           error.showAndWait();
           return; // Stop further execution
       }

       try {
           // Validate and parse the location number
           int newLocationNumber = Integer.parseInt(new_Location);

           // SQL query for updating the equipment
           String updateSQL = "UPDATE Equipment SET Equipment_Name = ?, Availability = ?, Location_Number = ? WHERE Equipment_Number = ?";

           try (Connection conn = JDBC.getConnection();
                PreparedStatement stmt = conn.prepareStatement(updateSQL)) {

               // Set parameters for the query
               stmt.setString(1, new_name);
               stmt.setString(2, new_Availability);
               stmt.setInt(3, newLocationNumber);
               stmt.setInt(4, equipment.getid()); // Update based on the selected equipment's ID

               // Execute update
               int rowsUpdated = stmt.executeUpdate();

               if (rowsUpdated > 0) {
                   Alert success = new Alert(Alert.AlertType.INFORMATION, "Equipment updated successfully.", ButtonType.OK);
                   success.showAndWait();
               } else {
                   Alert error = new Alert(Alert.AlertType.ERROR, "Failed to update equipment.", ButtonType.OK);
                   error.showAndWait();
               }
           } catch (SQLException e) {
               e.printStackTrace();
               Alert error = new Alert(Alert.AlertType.ERROR, "Error updating the database.", ButtonType.OK);
               error.showAndWait();
           }

       } catch (NumberFormatException e) {
           // Handle invalid location number input
           Alert error = new Alert(Alert.AlertType.ERROR, "Location number must be a valid integer.", ButtonType.OK);
           error.showAndWait();
       }
   }
   @FXML
   public void handel_AddSupplier()
   {
	   sup_name_feild.setVisible(true);
	   sup_number_field.setVisible(true);
	   sup_phone_field.setVisible(true);
	   eq_sup_numberField.setVisible(true);
	   eq_sup_nameField.setVisible(true);
	   eq_sup_locationnumberField.setVisible(true);
	   eq_sup_equipmentuseField.setVisible(true);
	   eq_sup_availabilityField.setVisible(true);
	   eq_sup_supnumberField.setVisible(true);
	   Add_supplier.setVisible(true);
	   lable_2.setVisible(true);
	   labl1_1.setVisible(true);
	   location_phone_field.setVisible(true);
	   location_address_field.setVisible(true);
	   close.setVisible(true);
   }
   @FXML
   public void handle_AddSupplier() throws SQLException {
       // Input retrieval and validation
       String supplier_Name = sup_name_feild.getText().trim();
       String supplier_number = sup_number_field.getText().trim();
       String supplier_phone = sup_phone_field.getText().trim();
       String eq_supNumber = eq_sup_numberField.getText().trim();
       String eq_supName = eq_sup_nameField.getText().trim();
       String eq_supAV = eq_sup_availabilityField.getText().trim();
       String eq_sup_supnumber = eq_sup_supnumberField.getText().trim();
       String eq_supequipmentuse = eq_sup_equipmentuseField.getText().trim();
       String eq_suplocationNumber = eq_sup_locationnumberField.getText().trim();
       String location_address = location_address_field.getText().trim(); // New field
       String location_phone = location_phone_field.getText().trim();   // New field

       // Check for empty fields
       if (supplier_Name.isEmpty() || supplier_number.isEmpty() || supplier_phone.isEmpty() ||
           eq_supNumber.isEmpty() || eq_supName.isEmpty() || eq_suplocationNumber.isEmpty() ||
           eq_supequipmentuse.isEmpty() || eq_supAV.isEmpty() || location_address.isEmpty() || location_phone.isEmpty()) {
           Alert error = new Alert(Alert.AlertType.ERROR, "Fill in all fields...", ButtonType.OK);
           error.showAndWait();
           return;
       }

       // Validate numeric fields
       try {
           int supplierNum = Integer.parseInt(supplier_number);
           int equipmentNum = Integer.parseInt(eq_supNumber);
           int locationNum = Integer.parseInt(eq_suplocationNumber);
           int supplierNumEq = Integer.parseInt(eq_sup_supnumber); // Supplier number in Equipment table
       } catch (NumberFormatException e) {
           Alert error = new Alert(Alert.AlertType.ERROR, "Ensure numeric fields contain valid numbers.", ButtonType.OK);
           error.showAndWait();
           return;
       }

       try (Connection conn = JDBC.getConnection()) {
           if (conn == null) {
               Alert error = new Alert(Alert.AlertType.ERROR, "Failed to establish database connection.", ButtonType.OK);
               error.showAndWait();
               return;
           }

           conn.setAutoCommit(false); // Start transaction

           try {
               // Check if Location exists
               String locationCheckSQL = "SELECT Location_Number FROM Location WHERE Address = ?";
               int locationNumber;

               try (PreparedStatement locationCheckStmt = conn.prepareStatement(locationCheckSQL)) {
                   locationCheckStmt.setString(1, location_address);
                   ResultSet rs = locationCheckStmt.executeQuery();

                   if (rs.next()) {
                       locationNumber = rs.getInt("Location_Number");
                   } else {
                       // Insert new Location
                       String locationInsertSQL = "INSERT INTO Location(Location_Number, Address, Phone_Number) VALUES(?, ?, ?)";
                       try (PreparedStatement locationInsertStmt = conn.prepareStatement(locationInsertSQL)) {
                           locationInsertStmt.setInt(1, Integer.parseInt(eq_suplocationNumber));
                           locationInsertStmt.setString(2, location_address);
                           locationInsertStmt.setString(3, location_phone);
                           locationInsertStmt.executeUpdate();
                       }
                       locationNumber = Integer.parseInt(eq_suplocationNumber);
                   }
               }

               // Insert Supplier
               String sup_sql = "INSERT INTO Supplier(Supplier_Number, S_Name, Phone_Number) VALUES(?, ?, ?)";
               try (PreparedStatement supplier_st = conn.prepareStatement(sup_sql)) {
                   supplier_st.setInt(1, Integer.parseInt(supplier_number));
                   supplier_st.setString(2, supplier_Name);
                   supplier_st.setString(3, supplier_phone);
                   supplier_st.executeUpdate();
               }

               // Insert Equipment
               String eq_sql = "INSERT INTO Equipment(Equipment_Number, Equipment_Name, Availability, Location_Number, Supplier_Number, Equipment_Use) VALUES(?, ?, ?, ?, ?, ?)";
               try (PreparedStatement eq_st = conn.prepareStatement(eq_sql)) {
                   eq_st.setInt(1, Integer.parseInt(eq_supNumber));
                   eq_st.setString(2, eq_supName);
                   eq_st.setString(3, eq_supAV);
                   eq_st.setInt(4, locationNumber);
                   eq_st.setInt(5, Integer.parseInt(eq_sup_supnumber));
                   eq_st.setString(6, eq_supequipmentuse);
                   eq_st.executeUpdate();
               }

               conn.commit(); // Commit transaction, to save all changes made during the current transaction to the database.
               //When performing multiple operations (like deleting the equipment and related records), it’s important to ensure that all operations are executed successfully. 
               Alert success = new Alert(Alert.AlertType.INFORMATION, "Supplier, location, and equipment added successfully.", ButtonType.OK);
               success.showAndWait();

           } catch (SQLException e) {
               conn.rollback(); // Rollback transaction
               e.printStackTrace();
               Alert error = new Alert(Alert.AlertType.ERROR, "Database error: " + e.getMessage(), ButtonType.OK);
               error.showAndWait();
           } finally {
               conn.setAutoCommit(true); // Reset auto-commit
           }

       } catch (SQLException e) {
           e.printStackTrace();
           Alert error = new Alert(Alert.AlertType.ERROR, "Database connection error. Please try again.", ButtonType.OK);
           error.showAndWait();
       }
   }
   
   @FXML
   private void handleViewEquipmentLocationItem() {
       close.setVisible(true);
       view_location.setVisible(true);
       eq_name.setVisible(true);
       location_lable.setVisible(true);
   }

   @FXML
   private void handelLocationButton() {
       String NameField = eq_name.getText().trim(); // Trim to remove extra spaces
       if (NameField == null || NameField.isEmpty()) {
           Alert error = new Alert(Alert.AlertType.ERROR, "Enter the Equipment name...", ButtonType.OK);
           error.showAndWait();
           return;
       }

       // Query to join Equipment and Location tables
       String Query = "SELECT l.Address " +
                      "FROM Equipment e, Location l " +
                      "WHERE e.Equipment_Name = ? " +
                      "AND e.Location_Number = l.Location_Number;";

       try (Connection conn = JDBC.getConnection();
            PreparedStatement location_st = conn.prepareStatement(Query)) {

           // Set the parameter for the query
           location_st.setString(1, NameField);

           // Execute the query
           try (ResultSet location_rs = location_st.executeQuery()) {
               if (location_rs.next()) {
                   // Fetch and display the address
                   String location = location_rs.getString("Address");
                   location_lable.setText(location);
               } else {
                   // No location found for the given equipment
                   location_lable.setText("No Location found");
               }
           }
       } catch (SQLException ex) {
           // Log the exception (optional)
           ex.printStackTrace();

           // Show an error message to the user
           Alert error = new Alert(Alert.AlertType.ERROR, "Database error: " + ex.getMessage(), ButtonType.OK);
           error.showAndWait();
       }
   }
   
   @FXML
   private void handelDeleteMenuItem()
   {
	   Delete.setVisible(true);
	   eq_de_name_field.setVisible(true);
	   close.setVisible(true);
	   
   }
   
   private void displayMessage(String message, String type) {
	    Alert alert;
	    if (type.equalsIgnoreCase("info")) {
	        alert = new Alert(Alert.AlertType.INFORMATION);
	    } else if (type.equalsIgnoreCase("error")) {
	        alert = new Alert(Alert.AlertType.ERROR);
	    } else {
	        alert = new Alert(Alert.AlertType.WARNING);
	    }
	    alert.setContentText(message);
	    alert.showAndWait();
	}
   //delete function
   @FXML
   private void handleDeleteEquipment() {
       String equipmentName = eq_de_name_field.getText().toUpperCase().trim();

       if (equipmentName.isEmpty()) {
           displayMessage("Please enter the equipment name!", "error");
           return;
       }

       try (Connection conn = JDBC.getConnection()) {
           // Step 1: Fetch Equipment_Number
           String fetchEquipmentQuery = "SELECT Equipment_Number FROM Equipment WHERE Equipment_Name = ?";
           try (PreparedStatement fetchStmt = conn.prepareStatement(fetchEquipmentQuery)) {
               fetchStmt.setString(1, equipmentName);
               try (ResultSet rs = fetchStmt.executeQuery()) {
                   if (!rs.next()) {
                       displayMessage("Equipment not found!", "error");
                       return;
                   }

                   int equipmentNumber = rs.getInt("Equipment_Number");

                   // Step 2: Delete related records using subqueries
                   conn.setAutoCommit(false); // Start transaction

                   String deleteInvolveQuery = "DELETE FROM Involve WHERE Equipment_Number = ?";
                   String deleteRentalQuery = "DELETE FROM Rental WHERE Equipment_Number = ?";
                   String deleteMaintenanceQuery = "DELETE FROM Maintenance_Request WHERE Equipment_Number = ?";
                   String deleteEquipmentQuery = "DELETE FROM Equipment WHERE Equipment_Number = ?";

                   try (
                       PreparedStatement deleteInvolveStmt = conn.prepareStatement(deleteInvolveQuery);
                       PreparedStatement deleteRentalStmt = conn.prepareStatement(deleteRentalQuery);
                       PreparedStatement deleteMaintenanceStmt = conn.prepareStatement(deleteMaintenanceQuery);
                       PreparedStatement deleteEquipmentStmt = conn.prepareStatement(deleteEquipmentQuery)
                   ) {
                       deleteInvolveStmt.setInt(1, equipmentNumber);
                       deleteRentalStmt.setInt(1, equipmentNumber);
                       deleteMaintenanceStmt.setInt(1, equipmentNumber);
                       deleteEquipmentStmt.setInt(1, equipmentNumber);

                       deleteInvolveStmt.executeUpdate();
                       deleteRentalStmt.executeUpdate();
                       deleteMaintenanceStmt.executeUpdate();
                       deleteEquipmentStmt.executeUpdate();

                       conn.commit(); // Commit transaction
                       displayMessage("Equipment and associated records deleted successfully.", "info");

                       // Clear the input field
                       eq_de_name_field.clear();
                   } catch (SQLException ex) {
                       conn.rollback(); // Rollback on failure
                       displayMessage("Error occurred while deleting equipment records! Transaction rolled back.", "error");
                   }
               }
           }
       } catch (SQLException e) {
           e.printStackTrace();
           displayMessage("Error connecting to the database!", "error");
       }
   }

}