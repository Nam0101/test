package itss.group14.timekeeper.dbservices;

import itss.group14.timekeeper.model.record.WorkerAttendanceRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkerAttendanceRecordService {


    public static ResultSet getAllWorkerAttendanceRecord(Connection connection) throws SQLException {
        String query = "SELECT * FROM WorkerAttendanceRecord";
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeQuery();
    }

    public static ResultSet getWorkerAttendanceRecordByEmployeeIdDate(Connection connection, String employeeId, String date) throws SQLException {
        String query = "SELECT * FROM WorkerAttendanceRecord WHERE employee_id  = ? AND date = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, employeeId);
        statement.setString(2, date);
        return statement.executeQuery();
    }

    public static void updateWorkerAttendanceRecordByEmployeeIdDate(Connection connection, WorkerAttendanceRecord workerAttendanceRecord) throws SQLException {
        String query = "UPDATE WorkerAttendanceRecord SET shift1Hours = ?, shift2Hours = ?, shift3Hours = ? WHERE employee_id  = ? AND date = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setDouble(1, workerAttendanceRecord.getShiftHours1());
        statement.setDouble(2, workerAttendanceRecord.getShiftHours2());
        statement.setDouble(3, workerAttendanceRecord.getShiftHours3());
        statement.setString(4, workerAttendanceRecord.getEmployeeId());
        statement.setString(5, workerAttendanceRecord.getDate());
        statement.executeUpdate();
    }
}
