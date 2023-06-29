package itss.group14.timekeeper.dbservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountService {
    public static String getRole(Connection connection, String username, String password) throws SQLException {
        String query = "SELECT role FROM account WHERE username = ? AND password = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, password);
        return statement.executeQuery().getString("role");
    }

    public static ResultSet getInformationAccountInDay(Connection connection, String username, String day) throws SQLException {
        String query = "SELECT employee.name, employee.department, WorkerAttendanceRecord.shift1Hours, WorkerAttendanceRecord.shift2Hours, WorkerAttendanceRecord.shift3Hours " +
                "FROM account " +
                "JOIN employee ON account.employee_id = employee.id " +
                "JOIN WorkerAttendanceRecord ON employee.id = WorkerAttendanceRecord.employee_id " +
                "WHERE account.username = ? AND WorkerAttendanceRecord.date = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, username);
        statement.setString(2, day);
        return statement.executeQuery();
    }

    public static ResultSet getInformationAccount(Connection connection, String userName) throws SQLException {
        String query = "SELECT employee.name, employee.id, employee.department " +
                "FROM employee " +
                "JOIN account ON employee.id = account.employee_id " +
                "WHERE account.username = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, userName);
        return statement.executeQuery();
    }


}
