package itss.group14.timekeeper.dbservices;

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

    public static void updateWorkerAttendanceRecordByEmployeeIdDate(Connection connection, String employeeId, String date, double shift1Hours, double shift2Hours, double shift3Hours) throws SQLException {
        String query = "UPDATE WorkerAttendanceRecord SET shift1Hours = ?, shift2Hours = ?, shift3Hours = ? WHERE employee_id  = ? AND date = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setDouble(1, shift1Hours);
        statement.setDouble(2, shift2Hours);
        statement.setDouble(3, shift3Hours);
        statement.setString(4, employeeId);
        statement.setString(5, date);
        statement.executeUpdate();
    }
}
