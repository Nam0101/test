package itss.group14.timekeeper.controllers;

import itss.group14.timekeeper.contrains.FXMLconstrains;
import itss.group14.timekeeper.controllers.infomationFix.SuaThongTinOfficerController;
import itss.group14.timekeeper.controllers.infomationFix.SuaThongTinWorkerController;
import itss.group14.timekeeper.dbservices.EmployeeService;
import itss.group14.timekeeper.dbservices.OfficerAttendanceRecordService;
import itss.group14.timekeeper.dbservices.RequestService;
import itss.group14.timekeeper.dbservices.WorkerAttendanceRecordService;
import itss.group14.timekeeper.dbservices.dbconection.AbstractSQLConnection;
import itss.group14.timekeeper.dbservices.dbconection.SqliteConnection;
import itss.group14.timekeeper.enums.Status;
import itss.group14.timekeeper.model.Request;
import itss.group14.timekeeper.ultis.ViewChangeUltils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class YeuCauChinhSuaController implements Initializable {
    private final ViewChangeUltils viewChangeUltils = new ViewChangeUltils();
    @FXML
    private Label hoTenLabel;
    @FXML
    private Label maNVLabel;
    @FXML
    private Label boPhanLabel;
    @FXML
    private Label ngaySuaLabel;
    @FXML
    private Text thongTinText;
    @FXML
    private Button chapNhanButton;
    @FXML
    private Button tuChoiButton;
    private Request selectedRequest;
    private Connection connection;

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

    public void displayRequestDetails(Request request) {
        selectedRequest = request;
        hoTenLabel.setText(request.getEmployee().getName());
        maNVLabel.setText(request.getEmployee().getId());
        boPhanLabel.setText(request.getEmployee().getDepartment());
        ngaySuaLabel.setText(request.getDate());
        thongTinText.setText(request.getReason());
    }

    public void chapNhanClick(ActionEvent actionEvent) throws Exception {
        if (selectedRequest != null) {
            String employeeId = selectedRequest.getEmployeeId();
            String date = selectedRequest.getDate();
            String role = EmployeeService.getRoleEmployeeById(connection, employeeId);
            assert role != null;
            if (role.equals("officer")) {
                handleOfficerRequest(actionEvent, employeeId, date);
            } else {
                handleWorkerRequest(actionEvent, employeeId, date);
            }
        }
    }

    private void handleWorkerRequest(ActionEvent actionEvent, String employeeId, String date) throws Exception {
        ResultSet res = WorkerAttendanceRecordService.getWorkerAttendanceRecordByEmployeeIdDate(connection, employeeId, date);

        if (res.next()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLconstrains.suaTTWorker));
            Parent suaTTWorkerRoot = loader.load();
            SuaThongTinWorkerController suaTTWorkerController = loader.getController();
            String EName = EmployeeService.getNameEmployeeById(connection, employeeId);
            String Ebophan = EmployeeService.getDepartmentEmployeeById(connection, employeeId);
            suaTTWorkerController.setWorkerData(EName, res.getString("employee_id"), Ebophan, res.getString("date"), res.getDouble("shift1Hours"), res.getDouble("shift2Hours"), res.getDouble("shift3Hours"), selectedRequest);
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.hide();
            Stage stage = new Stage();
            Scene scene = new Scene(suaTTWorkerRoot);
            stage.setScene(scene);
            stage.show();
            selectedRequest.setStatus(String.valueOf(Status.OK));
            connection.close();
        } else {
            selectedRequest.setStatus(String.valueOf(Status.OK));
            RequestService.updateRequestStatus(connection, selectedRequest);
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // Handle the exception if unable to close the connection
                    e.printStackTrace();
                }
            }
            viewChangeUltils.changeView(actionEvent, FXMLconstrains.danhSachYCFXML);
        }
    }
    private void handleOfficerRequest(ActionEvent actionEvent, String employeeId, String date) throws Exception {

        ResultSet res = OfficerAttendanceRecordService.getOfficerAttendanceRecordByEmployeeIdDate(connection, employeeId, date);
        assert res != null;
        if (res.next()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLconstrains.suaTTOfficer));
            Parent suaTTWorkerRoot = loader.load();
            SuaThongTinOfficerController suaTTWorkerController = loader.getController();
            String EName = EmployeeService.getNameEmployeeById(connection, employeeId);
            String Ebophan = EmployeeService.getDepartmentEmployeeById(connection, employeeId);
            suaTTWorkerController.setOfficerData(EName, res.getString("employee_id"), Ebophan, res.getString("date"), res.getDouble("hoursLate"),res.getDouble("afternoonSession"), res.getBoolean("morningSession"),res.getBoolean("afternoonSession"), selectedRequest);
            Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            currentStage.hide();
            Stage stage = new Stage();
            Scene scene = new Scene(suaTTWorkerRoot);
            stage.setScene(scene);
            stage.show();
            selectedRequest.setStatus(String.valueOf(Status.OK));
            connection.close();
        } else {
            selectedRequest.setStatus(String.valueOf(Status.OK));
            RequestService.updateRequestStatus(connection, selectedRequest);
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // Handle the exception if unable to close the connection
                    e.printStackTrace();
                }
            }
            viewChangeUltils.changeView(actionEvent, FXMLconstrains.danhSachYCFXML);
        }
    }

    public void tuChoiClick(ActionEvent actionEvent) throws Exception {
        if (selectedRequest != null) {
            selectedRequest.setStatus(String.valueOf(Status.REFUSE));

            RequestService.updateRequestStatus(connection, selectedRequest);
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    // Handle the exception if unable to close the connection
                    e.printStackTrace();
                }
            }
            viewChangeUltils.changeView(actionEvent, FXMLconstrains.danhSachYCFXML);
        }
    }

    public void backclick(ActionEvent actionEvent) throws Exception {
        viewChangeUltils.changeView(actionEvent, FXMLconstrains.danhSachYCFXML);
    }
}
