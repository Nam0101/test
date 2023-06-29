package itss.group14.timekeeper.controllers.exportdata;

import itss.group14.timekeeper.contrains.FXMLconstrains;
import itss.group14.timekeeper.dbservices.OfficerAttendanceRecordService;
import itss.group14.timekeeper.dbservices.dbconection.AbstractSQLConnection;
import itss.group14.timekeeper.dbservices.dbconection.SqliteConnection;
import itss.group14.timekeeper.model.Employee;
import itss.group14.timekeeper.model.export.OfficerExportInfor;
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

public class ExportDataOfficerController implements Initializable {
    private final ViewChangeUltils viewChangeUltils = new ViewChangeUltils();
    public TableColumn NameCol;
    public TableColumn idCol;
    public TableColumn DepartmentCol;
    public TableColumn MonthCol;
    public TableColumn WorkingTimeCol;
    public TableColumn LateTimeCol;
    public TableColumn EarlyTimeCol;
    public TextField Month;
    public TextField Year;
    public TableView TTTable;
    private Connection connection;
    private ExportExcelOfficerController exportExcelOfficerController;

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
        NameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        DepartmentCol.setCellValueFactory(new PropertyValueFactory<>("department"));
        MonthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        WorkingTimeCol.setCellValueFactory(new PropertyValueFactory<>("totalSessions"));
        LateTimeCol.setCellValueFactory(new PropertyValueFactory<>("totalHoursLate"));
        EarlyTimeCol.setCellValueFactory(new PropertyValueFactory<>("totalHoursEarly"));

    }

    public void excelProcess(ActionEvent event) throws IOException {
        exportExcelOfficerController.exportExcel("Officer_data.xlsx");
    }

    public void cvsProcess(ActionEvent event) {
    }

    public void ViewProces(ActionEvent event) throws SQLException {
        retrieveData();
    }

    private void retrieveData() throws SQLException {
        String monthText = Month.getText();
        String yearText = Year.getText();
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
        ResultSet ResultSet = OfficerAttendanceRecordService.getSumOfficerWorkTimeGroupByWorkerIDandMonthYear(connection, monthText, yearText);
        if (ResultSet == null) {
            Ultils.createDialog(Alert.AlertType.ERROR, "Error", "Invalid input", "Please enter a valid month and year.");
            return;
        }
        ObservableList<OfficerExportInfor> officerExportInfors = FXCollections.observableArrayList();
        while (ResultSet.next()) {
            String name = ResultSet.getString("name");
            String department = ResultSet.getString("department");
            String employeeId = ResultSet.getString("employee_id");
            int totalSessions = ResultSet.getInt("totalSessions");
            double totalHoursLate = ResultSet.getDouble("totalHoursLate");
            double totalHoursEarly = ResultSet.getDouble("totalHoursEarly");
            Employee employee = new Employee(employeeId, name, department);
            String monthYear = Ultils.convertMonthYear(monthText, yearText);
            OfficerExportInfor officerExportInfor = new OfficerExportInfor(employee, totalSessions, totalHoursLate, totalHoursEarly, monthYear);
            officerExportInfors.add(officerExportInfor);
        }
        TTTable.setItems(officerExportInfors);
        exportExcelOfficerController = new ExportExcelOfficerController(officerExportInfors);

    }

    public void handldeBack(ActionEvent event) throws Exception {
        viewChangeUltils.changeView(event, FXMLconstrains.W_or_OexportDataFXML);
    }
}
