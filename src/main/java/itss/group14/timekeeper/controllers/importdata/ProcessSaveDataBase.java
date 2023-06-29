package itss.group14.timekeeper.controllers.importdata;

import itss.group14.timekeeper.dbservices.OfficerAttendanceRecordService;
import itss.group14.timekeeper.dbservices.WorkerAttendanceRecordService;
import itss.group14.timekeeper.dbservices.dbconection.AbstractSQLConnection;
import itss.group14.timekeeper.dbservices.dbconection.SqliteConnection;
import itss.group14.timekeeper.model.EmployeeShiftHours;
import itss.group14.timekeeper.model.OfficerShiftAttendance;
import itss.group14.timekeeper.model.record.OfficerAttendanceRecord;
import itss.group14.timekeeper.model.record.WorkerAttendanceRecord;
import itss.group14.timekeeper.ultis.Ultils;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ProcessSaveDataBase {
    private List<EmployeeShiftHours> employeeShiftHours;
    private List<OfficerShiftAttendance> officerShiftAttendances;
    private Connection connection;

    public ProcessSaveDataBase(List<EmployeeShiftHours> employeeShiftHours, List<OfficerShiftAttendance> officerShiftAttendances, Connection connection) {
        this.employeeShiftHours = employeeShiftHours;
        this.officerShiftAttendances = officerShiftAttendances;
        this.connection = connection;
    }

    public void saveData() throws SQLException {
        if(connection.isClosed()){
            AbstractSQLConnection abstractSQLConnection = new SqliteConnection();
            connection = abstractSQLConnection.getConnection();
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            for (EmployeeShiftHours shiftHours : employeeShiftHours) {
                String date = shiftHours.getDate().format(formatter);
                ResultSet resultSet = WorkerAttendanceRecordService.getWorkerAttendanceRecordByEmployeeIdDate(connection, shiftHours.getEmployeeId(), date);
                assert resultSet != null;
                if (resultSet.next()) {
                    continue;
                } else {
                    WorkerAttendanceRecord workerAttendanceRecord = new WorkerAttendanceRecord(shiftHours.getEmployeeId(), date, shiftHours.getShift1Hours(), shiftHours.getShift2Hours(), shiftHours.getShift3Hours());
                    WorkerAttendanceRecordService.insertWorkerAttendanceRecord(connection, workerAttendanceRecord);
                }
            }
            for (OfficerShiftAttendance shiftAttendance : officerShiftAttendances) {
                String date = shiftAttendance.getDate().format(formatter);
                ResultSet resultSet = OfficerAttendanceRecordService.getOfficerAttendanceRecordByEmployeeIdDate(connection, shiftAttendance.getEmployeeId(), date);
                assert resultSet != null;
                if (resultSet.next()) {
                    continue;
                } else {
                    OfficerAttendanceRecord officerAttendanceRecord = new OfficerAttendanceRecord(shiftAttendance.getEmployeeId(), date, shiftAttendance.isShift1Attended(), shiftAttendance.isShift2Attended(), shiftAttendance.getHoursLate(), shiftAttendance.getHoursEarly());
                    OfficerAttendanceRecordService.insertOfficerAttendanceRecord(connection, officerAttendanceRecord);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Ultils.createDialog(Alert.AlertType.INFORMATION, "Success", "Data imported successfully", "Data imported successfully");
    }

    void closeDatabaseConnection() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}