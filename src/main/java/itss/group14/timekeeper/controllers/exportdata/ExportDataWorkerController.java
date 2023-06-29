package itss.group14.timekeeper.controllers.exportdata;

import itss.group14.timekeeper.contrains.FXMLconstrains;
import itss.group14.timekeeper.dbservices.WorkerAttendanceRecordService;
import itss.group14.timekeeper.dbservices.dbconection.AbstractSQLConnection;
import itss.group14.timekeeper.dbservices.dbconection.SqliteConnection;
import itss.group14.timekeeper.model.record.WorkerAttendanceRecord;
import itss.group14.timekeeper.ultis.ViewChangeUltils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

public class ExportDataWorkerController implements Initializable {
    private final ViewChangeUltils viewChangeUltils = new ViewChangeUltils();
    public TableView thongTinTable;
    public TextField month;
    public TextField year;
    public Connection connection;
    private HashMap<String, WorkerAttendanceRecord> workerAttendanceRecordHashMap = new HashMap<>();

    public void excelButton(ActionEvent event) {
    }

    public void csvButton(ActionEvent event) {
    }

    public void backButton(ActionEvent event) throws Exception {
        viewChangeUltils.changeView(event, FXMLconstrains.W_or_OexportDataFXML);
    }

    public void retrieveData() throws SQLException {
        String monthText = month.getText();
        String yearText = year.getText();
        if (monthText.isEmpty() || yearText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please enter a valid month and year.");
            alert.showAndWait();
            return;
        }
        if (Integer.parseInt(monthText) < 1 || Integer.parseInt(monthText) > 12) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please enter a valid month.");
            alert.showAndWait();
            return;
        }
        if (Integer.parseInt(yearText) < 2000 || Integer.parseInt(yearText) > 2023) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please enter a valid year.");
            alert.showAndWait();
            return;
        }
        ResultSet resultSet = WorkerAttendanceRecordService.getAllWorkerAttendanceRecordByMonthAndYear(connection, monthText, yearText);
        if (resultSet == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid input");
            alert.setContentText("Please enter a valid month and year.");
            alert.showAndWait();
            return;
        }
        while (resultSet.next()) {
            String workerId = resultSet.getString("employee_id");
            WorkerAttendanceRecord workerAttendanceRecord = new WorkerAttendanceRecord(workerId, resultSet.getDouble("shift1Hours"), resultSet.getDouble("shift2Hours"), resultSet.getDouble("shift3Hours"));
            workerAttendanceRecordHashMap.put(workerId, workerAttendanceRecord);
        }


    }

    public void viewButton(ActionEvent event) throws SQLException {
        retrieveData();
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
