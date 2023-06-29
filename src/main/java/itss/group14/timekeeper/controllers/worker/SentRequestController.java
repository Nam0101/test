package itss.group14.timekeeper.controllers.worker;

import itss.group14.timekeeper.contrains.FXMLconstrains;
import itss.group14.timekeeper.dbservices.RequestService;
import itss.group14.timekeeper.dbservices.dbconection.AbstractSQLConnection;
import itss.group14.timekeeper.dbservices.dbconection.SqliteConnection;
import itss.group14.timekeeper.enums.Status;
import itss.group14.timekeeper.model.Request;
import itss.group14.timekeeper.ultis.Ultils;
import itss.group14.timekeeper.ultis.ViewChangeUltils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class SentRequestController implements Initializable {
    private static final Preferences pref = Preferences.userRoot();
    private static final String employeeName = pref.get("employeeName", "");
    private static final String employeeID = pref.get("employeeID", "");
    private static final String employeeDepartment = pref.get("department", "");
    private static String dayMonthYear = pref.get("dayMonthYear", "");
    public Label hoTenLabel;
    public Label maNVLabel;
    public Label boPhanLabel;
    public Label ngaySuaLabel;
    public TextArea lydo;
    ViewChangeUltils changeScene = new ViewChangeUltils();
    private Connection connection;

    public void backButton(ActionEvent event) throws Exception {
        dayMonthYear = pref.get("dayMonthYear", "");
        ngaySuaLabel.setText(dayMonthYear);
        changeScene.changeView(event, FXMLconstrains.workerHomeFXML);
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
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
        hoTenLabel.setText(employeeName);
        maNVLabel.setText(employeeID);
        boPhanLabel.setText(employeeDepartment);
        ngaySuaLabel.setText(dayMonthYear);
    }

    public void sentRequest(ActionEvent event) throws SQLException {
        String reason = lydo.getText();
        if (reason.equals("")) {
            Ultils.createDialog(Alert.AlertType.ERROR, "Lỗi", "Lỗi", "Vui lòng nhập lý do");
        }
        Request request = new Request(employeeID, dayMonthYear, reason, String.valueOf(Status.PENDING));
        try {
            RequestService.addRequest(connection, request);
            Ultils.createDialog(Alert.AlertType.INFORMATION, "Thông báo", "Thông báo", "Gửi yêu cầu thành công");
        } catch (SQLException throwables) {
            Ultils.createDialog(Alert.AlertType.ERROR, "Lỗi", "Lỗi", "Gửi yêu cầu thất bại");
            throwables.printStackTrace();
        }
    }

    public void setDayMonthYear(String ngay) {
        dayMonthYear = ngay;
        ngaySuaLabel.setText(dayMonthYear);
    }
}
