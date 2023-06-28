package itss.group14.timekeeper.controllers;

import itss.group14.timekeeper.contrains.FXMLconstrains;
import itss.group14.timekeeper.dbservices.dbconection.AbstractSQLConnection;
import itss.group14.timekeeper.dbservices.dbconection.SqliteConnection;
import itss.group14.timekeeper.ultis.Ultils;
import itss.group14.timekeeper.ultis.ViewChangeUltils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class LoginController implements Initializable {
    public PasswordField passwordField;
    public Button loginButton;
    public TextField usernameField;
    private Connection connection;

    public String login(String username, String password) {
        String query = "SELECT role FROM account WHERE username = ? AND password = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("role");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    public void loginClick(ActionEvent event) throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();
        if (username.trim().equals("") || password.trim().equals("")) {
            Ultils.createDialog(Alert.AlertType.ERROR, "Đăng nhập thất bại", "Tên đăng nhập hoặc mật khẩu không được để trống", "OK");
            return;
        }
        String role = login(username, password);
        if (role != null) {
            Preferences userPreferences = Preferences.userRoot();
            userPreferences.put("username", username);
            userPreferences.put("role", role);
            if (role.equals("admin")) {
                ViewChangeUltils changeScene = new ViewChangeUltils();
                changeScene.changeView(event, FXMLconstrains.adminHomeFXML);
            } else if (role.equals("worker")) {
            } else {
            }
        } else {
            Ultils.createDialog(Alert.AlertType.ERROR, "Đăng nhập thất bại", "Sai tên đăng nhập hoặc mật khẩu", "OK");
        }
    }

    /**
     * Called to initialize a controller after its root element has been
     * completely processed.
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AbstractSQLConnection sqliteConnection = new SqliteConnection();
        try {
            sqliteConnection.connect();
            connection = sqliteConnection.getConnection();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

