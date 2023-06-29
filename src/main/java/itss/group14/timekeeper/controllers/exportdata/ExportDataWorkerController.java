package itss.group14.timekeeper.controllers.exportdata;

import itss.group14.timekeeper.contrains.FXMLconstrains;
import itss.group14.timekeeper.dbservices.WorkerAttendanceRecordService;
import itss.group14.timekeeper.dbservices.dbconection.AbstractSQLConnection;
import itss.group14.timekeeper.dbservices.dbconection.SqliteConnection;
import itss.group14.timekeeper.model.Employee;
import itss.group14.timekeeper.model.export.WorkerExportInfor;
import itss.group14.timekeeper.ultis.Ultils;
import itss.group14.timekeeper.ultis.ViewChangeUltils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ExportDataWorkerController implements Initializable {
    private final ViewChangeUltils viewChangeUltils = new ViewChangeUltils();
    public TableView<WorkerExportInfor> thongTinTable;
    public TextField month;
    public TextField year;
    public Connection connection;
    public TableColumn name;
    public TableColumn id;
    public TableColumn department;
    public TableColumn MonthYear;
    public TableColumn WorkingTime;
    public TableColumn OverTime;
    private ExportExcelProcessController exportExcelProcessController;

    public void excelButton(ActionEvent event) throws IOException {
        exportExcelProcessController.exportToExcelWorker("Worker.xlsx");
    }

    public void csvButton(ActionEvent event) {
    }

    public void backButton(ActionEvent event) throws Exception {
        viewChangeUltils.changeView(event, FXMLconstrains.W_or_OexportDataFXML);
    }

    public void retrieveData() throws SQLException {
        String monthText = month.getText();
        String yearText = year.getText();
        if (monthText.isEmpty() || yearText.isEmpty() || !Ultils.isNumeric(monthText) || !Ultils.isNumeric(yearText)) {
            Ultils.createDialog(Alert.AlertType.ERROR, "Error", "Invalid input", "Please enter a valid month and year.");
            return;
        }
        if (Integer.parseInt(monthText) < 1 || Integer.parseInt(monthText) > 12) {
            Ultils.createDialog(Alert.AlertType.ERROR, "Error", "Invalid input", "Please enter a valid month.");
            return;
        }
        if (Integer.parseInt(yearText) < 2000 || Integer.parseInt(yearText) > 2023) {
            Ultils.createDialog(Alert.AlertType.ERROR, "Error", "Invalid input", "Please enter a valid year.");
            return;
        }
        ResultSet resultSet = WorkerAttendanceRecordService.getSumWorkerWorkTimeGroupByWorkerIDandMonthYear(connection, monthText, yearText);
        if (resultSet == null) {
            Ultils.createDialog(Alert.AlertType.ERROR, "Error", "Invalid input", "Please enter a valid month and year.");
            return;
        }
        ObservableList<WorkerExportInfor> records = FXCollections.observableArrayList();
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            String department = resultSet.getString("department");
            String employeeId = resultSet.getString("employee_id");
            double workingTime = resultSet.getDouble("workingTime");
            double overtime = resultSet.getDouble("overtime");

            Employee employee = new Employee(employeeId, name, department);
            String monthYear = Ultils.convertMonthYear(monthText, yearText);
            WorkerExportInfor workerExportInfor = new WorkerExportInfor(employee, workingTime, overtime, monthYear);
            records.add(workerExportInfor);
        }
        thongTinTable.setItems(records);
        exportExcelProcessController = new ExportExcelProcessController(records);
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

        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        department.setCellValueFactory(new PropertyValueFactory<>("department"));
        id.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        MonthYear.setCellValueFactory(new PropertyValueFactory<>("month"));
        WorkingTime.setCellValueFactory(new PropertyValueFactory<>("workingTime"));
        OverTime.setCellValueFactory(new PropertyValueFactory<>("overtime"));
    }


}
