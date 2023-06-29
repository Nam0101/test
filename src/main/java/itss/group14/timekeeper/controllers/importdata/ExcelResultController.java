package itss.group14.timekeeper.controllers.importdata;

import itss.group14.timekeeper.contrains.FXMLconstrains;
import itss.group14.timekeeper.model.EmployeeData;
import itss.group14.timekeeper.ultis.ViewChangeUltils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ExcelResultController implements Initializable {
    public TableView ExcelData;
    public TableColumn employeeId;
    public TableColumn Date;
    public TableColumn Time;
    private Map<String, List<String>> employeeData;
    private Connection connection;
    private ViewChangeUltils viewChangeUltils = new ViewChangeUltils();

    public ExcelResultController(Map<String, List<String>> employeeData) {
        this.employeeData = employeeData;
    }

    public ExcelResultController() {
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void saveToDB(ActionEvent event) throws SQLException {
        ProcessDataExcel processDataExcel = new ProcessDataExcel(employeeData, connection);
        processDataExcel.processEmployeeData();
    }

    public void handleBack(ActionEvent event) throws Exception {
        viewChangeUltils.changeView(event, FXMLconstrains.importDataSelectFXML);
    }

    public void setEmployeeData(Map<String, List<String>> employeeData) {
        this.employeeData = employeeData;
        updateTableView();
    }

    private void updateTableView() {
        List<EmployeeData> tableData = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : employeeData.entrySet()) {
            String employeeId = entry.getKey();
            List<String> timestamps = entry.getValue();

            for (String timestamp : timestamps) {
                // Split the timestamp into date and time parts
                String[] parts = timestamp.split(" ");
                String date = parts[0];
                String time = parts[1];
                // Create an EmployeeData object and add it to the list
                EmployeeData data = new EmployeeData(employeeId, date, time);
                tableData.add(data);
            }
        }
        ExcelData.setItems(FXCollections.observableList(tableData));
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
        employeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        Date.setCellValueFactory(new PropertyValueFactory<>("date"));
        Time.setCellValueFactory(new PropertyValueFactory<>("time"));


    }


}
