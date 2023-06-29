package itss.group14.timekeeper.controllers.worker;

import itss.group14.timekeeper.contrains.FXMLconstrains;
import itss.group14.timekeeper.dbservices.AccountService;
import itss.group14.timekeeper.dbservices.dbconection.AbstractSQLConnection;
import itss.group14.timekeeper.dbservices.dbconection.SqliteConnection;
import itss.group14.timekeeper.ultis.Ultils;
import itss.group14.timekeeper.ultis.ViewChangeUltils;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class WorkerHomeController implements Initializable {
    private static final Preferences pref = Preferences.userRoot();
    private static final String name = pref.get("username", "");
    private final ViewChangeUltils changeScene = new ViewChangeUltils();
    public Label shift1;
    public Label shift2;
    public Label shift3;
    public Label workerName;
    public Label workerID;
    public Label department;
    public TextField days;
    public TextField months;
    public TextField year;
    public Label Wellcome;
    public Button sendRequestButton;
    private String dayMonthYear;
    private Connection connection;

    public void suaThongTin(ActionEvent event) throws Exception {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLconstrains.sentRequestFXML));
        Parent root = loader.load();
        SentRequestController controller = loader.getController();
        controller.setDayMonthYear(dayMonthYear);
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }


    public void logout(ActionEvent event) throws Exception {
        changeScene.changeView(event, FXMLconstrains.loginFXML);
    }

    public void handleView(ActionEvent event) throws SQLException {
        String day = days.getText();
        String month = months.getText();
        String years = year.getText();
        if (day.trim().equals("") || month.trim().equals("") || years.trim().equals("")) {
            Ultils.createDialog(Alert.AlertType.ERROR, "Lỗi", "Vui lòng nhập đầy đủ thông tin", "OK");
            return;
        }
        dayMonthYear = Ultils.convertDayMonthYear(day, month, years);
        Preferences workerPreferences = Preferences.userRoot();
        workerPreferences.put("dayMonthYear", dayMonthYear);
        workerPreferences.put("employeeID", workerID.getText());
        workerPreferences.put("employeeName", workerName.getText());
        workerPreferences.put("department", department.getText());
        ResultSet resultSet = AccountService.getInformationAccountInDay(connection, name, dayMonthYear);
        if (!resultSet.next()) {
            Ultils.createDialog(Alert.AlertType.ERROR, "Lỗi", "Không tìm thấy thông tin", "OK");
            sendRequestButton.setDisable(true);
            return;
        }
        shift1.setText(resultSet.getString("shift1Hours") + " giờ");
        shift2.setText(resultSet.getString("shift2Hours") + " giờ");
        shift3.setText(resultSet.getString("shift3Hours") + " giờ");
        sendRequestButton.setDisable(false);
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
        try {
            ResultSet resultSet = AccountService.getInformationAccount(connection, name);
            while (resultSet.next()) {
                workerName.setText(resultSet.getString("name"));
                workerID.setText(resultSet.getString("id"));
                department.setText(resultSet.getString("department"));
                Wellcome.setText("Xin chào " + resultSet.getString("name") + "!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
