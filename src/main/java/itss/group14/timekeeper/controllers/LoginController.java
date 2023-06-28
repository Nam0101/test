package itss.group14.timekeeper.controllers;

import itss.group14.timekeeper.contrains.FXMLconstrains;
import itss.group14.timekeeper.ultis.Ultils;
import itss.group14.timekeeper.ultis.ViewChangeUltils;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.*;
import java.util.prefs.Preferences;

public class LoginController {
    public PasswordField passwordField;
    public Button loginButton;
    public TextField usernameField;
    private Connection connection;
    public LoginController() {
        // Khởi tạo kết nối đến cơ sở dữ liệu SQLite
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:timekeeperdb.sqlite");
        } catch (SQLException e) {
            e.printStackTrace();
        }
}

    public String login(String username, String password) {
    // Tạo câu lệnh truy vấn
    String query = "SELECT role FROM account WHERE username = ? AND password = ?";
    try (PreparedStatement statement = connection.prepareStatement(query)) {
        // Điền giá trị cho các tham số trong câu lệnh truy vấn
        statement.setString(1, username);
        statement.setString(2, password);
        // Thực thi câu lệnh truy vấn
        ResultSet resultSet = statement.executeQuery();
        // Kiểm tra kết quả truy vấn
        if (resultSet.next()) {
            // Nếu có kết quả truy vấn, tức là thông tin đăng nhập hợp lệ
            // Trả về vai trò của tài khoản
            return resultSet.getString("role");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
        // Nếu không có kết quả truy vấn hoặc xảy ra lỗi, tức là thông tin đăng nhập không hợp lệ
        return null;
    }

    public void loginClick(ActionEvent event) throws Exception {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = login(username, password);
        if (role != null) {
            Preferences userPreferences = Preferences.userRoot();
            userPreferences.put("username", username);
            userPreferences.put("role", role);
            System.out.println("Đăng nhập thành công với vai trò: " + role);
            if (role.equals("admin")) {
                ViewChangeUltils changeScene = new ViewChangeUltils();
                changeScene.changeView(event, FXMLconstrains.adminHomeFXML);
            } else if (role.equals("worker")) {
                // Xử lý cho vai trò user
            } else {
                // Xử lý cho các vai trò khác
            }
        } else {
            Ultils.createDialog(Alert.AlertType.ERROR, "Đăng nhập thất bại", "Sai tên đăng nhập hoặc mật khẩu", "OK");
    }
}
}

