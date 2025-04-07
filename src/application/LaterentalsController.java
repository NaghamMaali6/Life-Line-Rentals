package application;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LaterentalsController 
{
	@FXML
    private TableView<Involve> lateRentalTable;
    @FXML
    private TableColumn<Involve, Integer> rentalNumberColumn;
    @FXML
    private TableColumn<Involve, Integer> equipmentNumberColumn;
    @FXML
    private TableColumn<Involve, String> statusColumn;

    private final ObservableList<Involve> lateRentalList = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Initialize TableView columns
        rentalNumberColumn.setCellValueFactory(new PropertyValueFactory<>("rentalNumber"));
        equipmentNumberColumn.setCellValueFactory(new PropertyValueFactory<>("equipmentNumber"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("rStatus"));

        // Bind data to TableView
        lateRentalTable.setItems(lateRentalList);
    }

    @FXML
    private void handleLoadLateRentals() {
        loadLateRentals();
    }

    private void loadLateRentals() {
        // Clear the existing data
        lateRentalList.clear();

        // SQL query to fetch late rentals
        String sql = "SELECT R.R_Status, R.Equipment_Number,R. Rental_Number FROM Involve R WHERE R.R_Status = 'late'";

        // Connect to the database and execute query
        try (Connection conn = JDBC.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            ResultSet resultSet = pstmt.executeQuery();

            // Add results to the observable list
            while (resultSet.next()) {
                String rStatus = resultSet.getString("R_Status");
                int equipmentNumber = resultSet.getInt("Equipment_Number");
                int rentalNumber = resultSet.getInt("Rental_Number");

                lateRentalList.add(new Involve(rStatus, equipmentNumber, rentalNumber));
            }

            // Show an alert if no late rentals are found
            if (lateRentalList.isEmpty()) {
                showAlert("No Late Rentals", "No late rentals found.", Alert.AlertType.INFORMATION);
            }

        } catch (SQLException e) {
            showAlert("Database Error", "Error fetching late rentals: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
