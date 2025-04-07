package application;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TrackEmployeeController {

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
    private TextField rentalNumberField;
    @FXML
    private Label employeeInfo;

    @FXML
    public void initialize() 
    {

        loadAllRentals();
    }

    private void loadAllRentals() 
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
    private void handleFindEmployee() {
        String rentalNumber = rentalNumberField.getText();

        if (rentalNumber.isEmpty()) {
            employeeInfo.setText("Please enter a rental number.");
            return;
        }

        String query = "SELECT e.Employee_Number, e.E_Name, e.WorkingStart_Date, e.WorkingEnd_Date " +
                       "FROM Employee e JOIN Rental r ON e.Employee_Number = r.Employee_Number " +
                       "WHERE r.Rental_Number = ?";

        try (Connection connection = JDBC.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, Integer.parseInt(rentalNumber));
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int employeeNumber = resultSet.getInt("Employee_Number");
                String name = resultSet.getString("E_Name");
                String startDate = resultSet.getDate("WorkingStart_Date").toString();
                String endDate = resultSet.getDate("WorkingEnd_Date") != null
                        ? resultSet.getDate("WorkingEnd_Date").toString()
                        : "Present";

                employeeInfo.setText(String.format("Employee Number: %d%nName: %s%nWorking From: %s%nWorking Until: %s",
                        employeeNumber, name, startDate, endDate));
            } else {
                employeeInfo.setText("No employee found for the given rental number.");
            }
        } catch (SQLException | NumberFormatException e) {
            employeeInfo.setText("An error occurred while retrieving employee information.");
            e.printStackTrace();
        }
    }
}