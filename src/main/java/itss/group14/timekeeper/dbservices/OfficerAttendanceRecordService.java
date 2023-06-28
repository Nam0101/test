package itss.group14.timekeeper.dbservices;

import java.sql.Connection;
import java.sql.ResultSet;

public class OfficerAttendanceRecordService {
    public static ResultSet getOfficerAttendanceRecordByEmployeeIdDate(Connection connection, String employeeId, String date) {
        String query = "SELECT * FROM OfficerAttendanceRecord WHERE employee_id  = ? AND date = ?";
        try {
            java.sql.PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, employeeId);
            statement.setString(2, date);
            return statement.executeQuery();
        } catch (java.sql.SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
