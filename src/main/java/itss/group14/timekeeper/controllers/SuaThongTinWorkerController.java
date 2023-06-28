package itss.group14.timekeeper.controllers;

import itss.group14.timekeeper.contrains.FXMLconstrains;
import itss.group14.timekeeper.dbservices.RequestService;
import itss.group14.timekeeper.dbservices.WorkerAttendanceRecordService;
import itss.group14.timekeeper.dbservices.dbconection.AbstractSQLConnection;
import itss.group14.timekeeper.dbservices.dbconection.SqliteConnection;
import itss.group14.timekeeper.enums.Status;
import itss.group14.timekeeper.model.Request;
import itss.group14.timekeeper.model.record.WorkerAttendanceRecord;
import itss.group14.timekeeper.ultis.Ultils;
import itss.group14.timekeeper.ultis.ViewChangeUltils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SuaThongTinWorkerController implements Initializable {

    private final ViewChangeUltils viewChangeUltils = new ViewChangeUltils();
    public Label bophan;
    public Button sentButton;
    public Label maNV;
    public Label tenNV;
    public Label NgaySua;
    public TextField suaCa1;
    public TextField suaCa2;
    public TextField suaCa3;
    public Button BackButton;
    private WorkerAttendanceRecord workerAttendanceRecord;
    ;
    private Connection connection;
    private Request selectedRequest;

    public SuaThongTinWorkerController() throws SQLException {
    }

    public void suaTT(ActionEvent event) throws Exception {
        String employeeId = maNV.getText();
        String date = NgaySua.getText();
        double shift1Hours = Double.parseDouble(suaCa1.getText());
        double shift2Hours = Double.parseDouble(suaCa2.getText());
        double shift3Hours = Double.parseDouble(suaCa3.getText());
        workerAttendanceRecord = new WorkerAttendanceRecord(employeeId, date, shift1Hours, shift2Hours, shift3Hours);
        if (shift1Hours < 0 || shift1Hours > 4 || shift2Hours < 0 || shift2Hours > 4 || shift3Hours < 0 || shift3Hours > 4) {
            Ultils.createDialog(Alert.AlertType.ERROR, "Lỗi", "Số giờ làm không hợp lệ.", "Đóng");
            return;
        }
        try {
            WorkerAttendanceRecordService.updateWorkerAttendanceRecordByEmployeeIdDate(connection, employeeId, date, shift1Hours, shift2Hours, shift3Hours);
            if (selectedRequest != null) {
                selectedRequest.setStatus(String.valueOf(Status.OK));
                RequestService.updateRequestStatus(connection, selectedRequest);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            connection.close();
        }
        Ultils.createDialog(Alert.AlertType.INFORMATION, "Thành công", "Sửa thành công.", "Đóng");
        viewChangeUltils.changeView(event, FXMLconstrains.danhSachYCFXML);
    }


    public void setWorkerData(String employeeName, String employeeId, String Bophan, String date, double shift1Hours, double shift2Hours, double shift3Hours, Request request) {
        tenNV.setText(employeeName);
        maNV.setText(employeeId);
        NgaySua.setText(date);
        bophan.setText(Bophan);
        suaCa1.setText(String.valueOf(shift1Hours));
        suaCa2.setText(String.valueOf(shift2Hours));
        suaCa3.setText(String.valueOf(shift3Hours));
        selectedRequest = request;
    }

    public void backAction(ActionEvent event) throws Exception {
        viewChangeUltils.changeView(event, FXMLconstrains.danhSachYCFXML);
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
