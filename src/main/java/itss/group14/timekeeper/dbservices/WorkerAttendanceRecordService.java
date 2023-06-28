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
}
