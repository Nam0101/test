package itss.group14.timekeeper.controllers.admin;

import itss.group14.timekeeper.contrains.FXMLconstrains;
import itss.group14.timekeeper.controllers.handlerequest.YeuCauChinhSuaController;
import itss.group14.timekeeper.dbservices.EmployeeService;
import itss.group14.timekeeper.dbservices.RequestService;
import itss.group14.timekeeper.dbservices.dbconection.AbstractSQLConnection;
import itss.group14.timekeeper.dbservices.dbconection.SqliteConnection;
import itss.group14.timekeeper.model.Request;
import itss.group14.timekeeper.ultis.ViewChangeUltils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;

public class DanhSachYCController implements Initializable {
    private final ViewChangeUltils viewChangeUltils = new ViewChangeUltils();
    private final ObservableList<Request> requests = FXCollections.observableArrayList();
    private Connection connection;
    @FXML
    public Button backButton;
    @FXML
    private TableView<Request> yctable;
    private YeuCauChinhSuaController yeucauChinhsuaController;

    private int getStatusPriority(String status) {
        return switch (status) {
            case "pending" -> 0;
            case "OK" -> 1;
            case "refuse" -> 2;
            default -> 3; // Các trạng thái khác có ưu tiên cao hơn
        };
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        AbstractSQLConnection sqliteConnection = new SqliteConnection();
        try {
            sqliteConnection.connect();
            connection = sqliteConnection.getConnection();
            ResultSet res = RequestService.getAllRequests(connection);
            while (res.next()) {
                ResultSet employeeRes = EmployeeService.getEmployeeById(connection, res.getString("employeeid"));
                requests.add(new Request(employeeRes.getString("id"), employeeRes.getString("name"), employeeRes.getString("department"), res.getString("date"), res.getString("status"), res.getString("reason")));
            }

            requests.sort(Comparator.comparing(Request::getStatus, Comparator.comparingInt(this::getStatusPriority)));

            yctable.setItems(requests);

            TableColumn<Request, String> maNVCol = (TableColumn<Request, String>) yctable.getColumns().get(0);
            TableColumn<Request, String> tenNVCol = (TableColumn<Request, String>) yctable.getColumns().get(1);
            TableColumn<Request, String> boPhanCol = (TableColumn<Request, String>) yctable.getColumns().get(2);
            TableColumn<Request, String> ngayCanChinhSuaCol = (TableColumn<Request, String>) yctable.getColumns().get(3);
            TableColumn<Request, String> trangThaiCol = (TableColumn<Request, String>) yctable.getColumns().get(4);
            TableColumn<Request, String> lyDoCol = (TableColumn<Request, String>) yctable.getColumns().get(5);

            maNVCol.setCellValueFactory(features -> new SimpleStringProperty(features.getValue().getEmployee().getId()));
            tenNVCol.setCellValueFactory(features -> new SimpleStringProperty(features.getValue().getEmployee().getName()));
            boPhanCol.setCellValueFactory(features -> new SimpleStringProperty(features.getValue().getEmployee().getDepartment()));
            ngayCanChinhSuaCol.setCellValueFactory(features -> new SimpleStringProperty(features.getValue().getDate()));
            trangThaiCol.setCellValueFactory(features -> new SimpleStringProperty(features.getValue().getStatus()));
            lyDoCol.setCellValueFactory(features -> new SimpleStringProperty(features.getValue().getReason()));

            yctable.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Request selectedRequest = yctable.getSelectionModel().getSelectedItem();
                    if (selectedRequest == null) return;

                    try {
                        showDetail(event, selectedRequest);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }


    public void backclick(ActionEvent actionEvent) throws Exception {
        viewChangeUltils.changeView(actionEvent, FXMLconstrains.adminHomeFXML);
    }

    private void showDetail(MouseEvent event, Request request) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLconstrains.yeuCauChinhSuaFXML));
        Parent root = loader.load();

        YeuCauChinhSuaController yeucauChinhsuaController = loader.getController();
        yeucauChinhsuaController.displayRequestDetails(request);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

}