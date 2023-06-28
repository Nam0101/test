package itss.group14.timekeeper.controllers.importdata;

import itss.group14.timekeeper.contrains.FXMLconstrains;
import itss.group14.timekeeper.dbservices.dbconection.AbstractSQLConnection;
import itss.group14.timekeeper.dbservices.dbconection.SqliteConnection;
import itss.group14.timekeeper.ultis.ViewChangeUltils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

public class SelectImportDataController implements Initializable {
    public final ViewChangeUltils viewChangeUltils = new ViewChangeUltils();
    public Button btnBack;
    Map<String, List<String>> employeeIdAndTimestamp = new HashMap<>();
    @FXML
    private Button importButton;
    private Connection connection;
    HashMap<String,String> employeeAndRole = new HashMap<>();

    public void handleBack(ActionEvent event) throws Exception {
        viewChangeUltils.changeView(event, FXMLconstrains.adminHomeFXML);
    }

    public void handleExcel(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        Stage stage = (Stage) importButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            importData(selectedFile);
        }

    }

    private void importData(File file) throws IOException {
        Map<String, List<String>> employeeData = new HashMap<>();

        try (FileInputStream excelFile = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(excelFile)) {

            Sheet sheet = workbook.getSheetAt(0); // Assuming the data is in the first sheet

            // Skip the first row (header row)
            int firstDataRowIndex = 1;

            for (int rowIndex = firstDataRowIndex; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);

                Cell employeeIdCell = row.getCell(0); // Employee_ID column
                Cell timestampCell = row.getCell(1); // Time_stamp column

                String employeeId = employeeIdCell.getStringCellValue();
                String timestamp = timestampCell.getStringCellValue();

                // Check if the employee already exists in the map
                if (employeeData.containsKey(employeeId)) {
                    // If yes, retrieve the list of timestamps and add the new timestamp
                    List<String> timestamps = employeeData.get(employeeId);
                    timestamps.add(timestamp);
                } else {
                    // If no, create a new list with the timestamp and add it to the map
                    List<String> timestamps = new ArrayList<>();
                    timestamps.add(timestamp);
                    employeeData.put(employeeId, timestamps);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // Print the stored data for testing
        for (Map.Entry<String, List<String>> entry : employeeData.entrySet()) {
            String employeeId = entry.getKey();
            List<String> timestamps = entry.getValue();

            System.out.println("Employee ID: " + employeeId);
            System.out.println("Timestamps: " + timestamps);
        }
    }




    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AbstractSQLConnection sqliteConnection = new SqliteConnection();
        try {
            sqliteConnection.connect();
            connection = sqliteConnection.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
