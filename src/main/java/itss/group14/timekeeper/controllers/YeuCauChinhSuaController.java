package itss.group14.timekeeper.controllers;

import itss.group14.timekeeper.contrains.FXMLconstrains;
import itss.group14.timekeeper.dbservices.RequestService;
import itss.group14.timekeeper.dbservices.WorkerAttendanceRecordService;
import itss.group14.timekeeper.enums.Status;
import itss.group14.timekeeper.model.Request;
import itss.group14.timekeeper.ultis.ViewChangeUltils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
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
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:timekeeperdb.sqlite");
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

    public void chapNhanClick(ActionEvent actionEvent) throws Exception{
        if(selectedRequest!=null){
            String employeeId = selectedRequest.getEmployeeId();
            String date = selectedRequest.getDate();
            System.out.println(employeeId);
            System.out.println(date);
            ResultSet res =WorkerAttendanceRecordService.getWorkerAttendanceRecordByEmployeeIdDate(connection, employeeId, date);
            if (res.next()) {
                viewChangeUltils.changeView(actionEvent, FXMLconstrains.suaTTWorker);
            } else {
                selectedRequest.setStatus(String.valueOf(Status.OK));
                RequestService.updateRequestStatus(connection, selectedRequest);
                if (connection != null) {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        // Handle the exception if unable to close the connection
                    }
                }
                viewChangeUltils.changeView(actionEvent, FXMLconstrains.danhSachYCFXML);
            }

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
                }
            }
            viewChangeUltils.changeView(actionEvent, FXMLconstrains.danhSachYCFXML);
        }
    }


    public void backclick(ActionEvent actionEvent) throws Exception {
        viewChangeUltils.changeView(actionEvent, FXMLconstrains.danhSachYCFXML);
    }
}
