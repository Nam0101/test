package itss.group14.timekeeper.dbservices;

import itss.group14.timekeeper.model.record.OfficerAttendanceRecord;

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

    public static void updateOfficerAttendanceRecord(Connection connection, OfficerAttendanceRecord officerAttendanceRecord) {
        String query = "UPDATE OfficerAttendanceRecord SET morningSession = ?, afternoonSession = ?, hoursLate = ?, hoursEarlyLeave =? WHERE employee_id  = ? AND date = ?";
        try {
            java.sql.PreparedStatement statement = connection.prepareStatement(query);
            statement.setBoolean(1, officerAttendanceRecord.isMorningSession());
            statement.setBoolean(2, officerAttendanceRecord.isAfternoonSession());
            statement.setDouble(3, officerAttendanceRecord.getHoursLate());
            statement.setDouble(4, officerAttendanceRecord.getHoursEarlyLeave());
            statement.setString(5, officerAttendanceRecord.getEmployeeId());
            statement.setString(6, officerAttendanceRecord.getDate());
            statement.executeUpdate();
        } catch (java.sql.SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static void insertOfficerAttendanceRecord(Connection connection, OfficerAttendanceRecord officerAttendanceRecord) {
        String query = "INSERT INTO OfficerAttendanceRecord (employee_id, date, morningSession, afternoonSession, hoursLate, hoursEarlyLeave) VALUES (?, ?, ?, ?, ?, ?)";
        try {
            java.sql.PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, officerAttendanceRecord.getEmployeeId());
            statement.setString(2, officerAttendanceRecord.getDate());
            statement.setBoolean(3, officerAttendanceRecord.isMorningSession());
            statement.setBoolean(4, officerAttendanceRecord.isAfternoonSession());
            statement.setDouble(5, officerAttendanceRecord.getHoursLate());
            statement.setDouble(6, officerAttendanceRecord.getHoursEarlyLeave());
            statement.executeUpdate();
        } catch (java.sql.SQLException throwables) {
            throwables.printStackTrace();
        }
    }


}
