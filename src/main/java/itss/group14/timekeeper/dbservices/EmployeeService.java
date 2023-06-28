package itss.group14.timekeeper.dbservices;

import itss.group14.timekeeper.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeService {
    private static final String TABLE_NAME = "employee";

    public static ResultSet getAllEmployees(Connection connection) throws SQLException {
        String query = "SELECT * FROM employee";
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeQuery();
    }

    public static ResultSet getEmployeeById(Connection connection, String employeeId) throws SQLException {
        String query = "SELECT * FROM employee WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, employeeId);
        return statement.executeQuery();
    }

    public static void addEmployee(Connection connection, Employee employee) throws SQLException {
        String query = "INSERT INTO employee (id, name, department,gender) VALUES (?, ?, ?,?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, employee.getId());
        statement.setString(2, employee.getName());
        statement.setString(3, employee.getDepartment());
        statement.setInt(4, employee.getGender());
        statement.executeUpdate();
    }
}
