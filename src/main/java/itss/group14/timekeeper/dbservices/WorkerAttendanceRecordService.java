package itss.group14.timekeeper.dbservices;

import itss.group14.timekeeper.model.record.WorkerAttendanceRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WorkerAttendanceRecordService implements IService {


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

    public static void insertWorkerAttendanceRecord(Connection connection, WorkerAttendanceRecord workerAttendanceRecord) throws SQLException {
        String query = "INSERT INTO WorkerAttendanceRecord (employee_id, date, shift1Hours, shift2Hours, shift3Hours) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, workerAttendanceRecord.getEmployeeId());
        statement.setString(2, workerAttendanceRecord.getDate());
        statement.setDouble(3, workerAttendanceRecord.getShiftHours1());
        statement.setDouble(4, workerAttendanceRecord.getShiftHours2());
        statement.setDouble(5, workerAttendanceRecord.getShiftHours3());
        statement.executeUpdate();
    }

    public static ResultSet getAllWorkerAttendanceRecordByMonthAndYear(Connection connection, String Month, String Year) throws SQLException {
        if (Month.length() == 1) {
            Month = "0" + Month;
        }
        String monthYear = "%/" + Month + "/" + Year;
        String query = "SELECT * FROM WorkerAttendanceRecord WHERE date LIKE ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, monthYear);
        return statement.executeQuery();
    }

    public static ResultSet getSumWorkerWorkTimeGroupByWorkerIDandMonthYear(Connection connection, String month, String year) throws SQLException {
        if (month.length() == 1) {
            month = "0" + month;
        }
        String monthYear = "%/" + month + "/" + year + "%";
        String query = "SELECT e.name, e.department, w.employee_id, SUM(w.shift1Hours + w.shift2Hours) AS workingTime, SUM(w.shift3Hours) AS overtime FROM WorkerAttendanceRecord w JOIN employee e ON w.employee_id = e.id WHERE w.date LIKE ? GROUP BY w.employee_id ORDER BY w.employee_id";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, monthYear);
        return statement.executeQuery();
    }

}
