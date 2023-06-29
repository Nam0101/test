package itss.group14.timekeeper.controllers.importdata;

import itss.group14.timekeeper.contrains.FXMLconstrains;
import itss.group14.timekeeper.dbservices.dbconection.AbstractSQLConnection;
import itss.group14.timekeeper.dbservices.dbconection.SqliteConnection;
import itss.group14.timekeeper.ultis.Ultils;
import itss.group14.timekeeper.ultis.ViewChangeUltils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class SelectImportDataController implements Initializable {
    public final ViewChangeUltils viewChangeUltils = new ViewChangeUltils();
    public Button btnBack;
    Map<String, List<String>> employeeIdAndTimestamp = new HashMap<>();
    @FXML
    private Button importButton;
    private Connection connection;
    private final HashMap<String, String> employeeAndRole = new HashMap<>();
    private final Map<String, List<String>> employeeData = new HashMap<>();


    public void handleBack(ActionEvent event) throws Exception {
        try {
            connection.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        viewChangeUltils.changeView(event, FXMLconstrains.adminHomeFXML);
    }

    public void handleExcel(ActionEvent event) throws IOException, SQLException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        Stage stage = (Stage) importButton.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            importData(selectedFile);
        }

    }

    public void importData(File file) throws SQLException, IOException {
        try (FileInputStream excelFile = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(excelFile)) {

            Sheet sheet = workbook.getSheetAt(0);

            int firstDataRowIndex = 1;

            for (int rowIndex = firstDataRowIndex; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);

                if (row == null) {
                    continue;
                }
                Cell employeeIdCell = row.getCell(0);
                Cell timestampCell = row.getCell(1);

                if (employeeIdCell == null || timestampCell == null) {
                    continue;
                }

                String employeeId = employeeIdCell.getStringCellValue();
                String timestamp = timestampCell.getStringCellValue();

                if (timestamp == null || timestamp.isEmpty()) {
                    Ultils.createDialog(Alert.AlertType.ERROR, "Error", "Error importing data", "The timestamp in row " + (rowIndex + 1) + " is empty or missing.");
                    return;
                }

                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
                    LocalDateTime.parse(timestamp, formatter);
                } catch (DateTimeParseException e) {
                    Ultils.createDialog(Alert.AlertType.ERROR, "Error", "Error importing data", "The timestamp '" + timestamp + "' is not in the expected format 'HH:mm dd/MM/yyyy'.");
                    return;
                }

                if (employeeData.containsKey(employeeId)) {
                    List<String> timestamps = employeeData.get(employeeId);
                    timestamps.add(timestamp);
                } else {
                    List<String> timestamps = new ArrayList<>();
                    timestamps.add(timestamp);
                    employeeData.put(employeeId, timestamps);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException | IllegalArgumentException e) {
            Ultils.createDialog(Alert.AlertType.ERROR, "Error", "Error importing data", "The selected file is not in the expected format.");
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLconstrains.excelResultFXML));
            Parent root = loader.load();
            Stage currentStage = (Stage) importButton.getScene().getWindow();
            currentStage.hide();
            ExcelResultController controller = loader.getController();
            controller.setEmployeeData(employeeData);
            controller.setConnection(connection);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading excel result view");
        }

    }


    private void processData() throws SQLException {
        ProcessDataExcel processDataExcel = new ProcessDataExcel(employeeData, connection);
        processDataExcel.processEmployeeData();
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
