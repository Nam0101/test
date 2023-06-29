package itss.group14.timekeeper.dbservices;

import itss.group14.timekeeper.model.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeService implements IService {
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

    public static String getNameEmployeeById(Connection connection, String employeeId) throws SQLException {
        String query = "SELECT name FROM employee WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, employeeId);
        ResultSet resultSet = statement.executeQuery();
        return resultSet.getString("name");
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

    public static String getDepartmentEmployeeById(Connection connection, String employeeId) {
        String query = "SELECT department FROM employee WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, employeeId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getString("department");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public static String getRoleEmployeeById(Connection connection, String employeeId) {
        String query = "SELECT role FROM employee WHERE id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, employeeId);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.getString("role");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
