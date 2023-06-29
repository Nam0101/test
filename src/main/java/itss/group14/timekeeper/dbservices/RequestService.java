package itss.group14.timekeeper.dbservices;

import itss.group14.timekeeper.model.Request;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RequestService implements IService {
    public static ResultSet getAllRequests(Connection connection) throws SQLException {
        String query = "SELECT * FROM request";
        PreparedStatement statement = connection.prepareStatement(query);
        return statement.executeQuery();
    }

    public static ResultSet getRequestsByEmployeeId(Connection connection, String employeeId) throws SQLException {
        String query = "SELECT * FROM request WHERE employeeid  = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, employeeId);
        return statement.executeQuery();
    }

    public static void addRequest(Connection connection, Request request) throws SQLException {
        String query = "INSERT INTO request (employeeid , date, status, reason) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, request.getEmployee().getId());
        statement.setString(2, request.getDate());
        statement.setString(3, request.getStatus());
        statement.setString(4, request.getReason());
        statement.executeUpdate();
    }

    public static void updateStatus(Connection connection, Request request) throws SQLException {
        String query = "UPDATE request SET status = ? WHERE employeeid  = ? AND date = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, request.getStatus());
        statement.setString(2, request.getEmployee().getId());
        statement.setString(3, request.getDate());
        statement.executeUpdate();
    }

    public static void updateRequest(Connection connection, Request request) throws SQLException {
        String query = "UPDATE request SET status = ? WHERE employeeid  = ? AND date = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, request.getStatus());
        statement.setString(2, request.getEmployee().getId());
        statement.setString(3, request.getDate());
        statement.executeUpdate();
    }

    public static void deleteRequest(Connection connection, Request request) throws SQLException {
        String query = "DELETE FROM request WHERE employeeid  = ? AND date = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, request.getEmployee().getId());
        statement.setString(2, request.getDate());
        statement.executeUpdate();
    }

    public static void updateRequestStatus(Connection connection, Request selectedRequest) throws SQLException {
        String query = "UPDATE request SET status = ? WHERE employeeid  = ? AND date = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, selectedRequest.getStatus());
        statement.setString(2, selectedRequest.getEmployee().getId());
        statement.setString(3, selectedRequest.getDate());
        statement.executeUpdate();
    }
}
