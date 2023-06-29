package itss.group14.timekeeper.controllers.importdata;

import itss.group14.timekeeper.dbservices.EmployeeService;
import itss.group14.timekeeper.dbservices.dbconection.AbstractSQLConnection;
import itss.group14.timekeeper.dbservices.dbconection.SqliteConnection;
import itss.group14.timekeeper.model.EmployeeShiftHours;
import itss.group14.timekeeper.model.OfficerShiftAttendance;
import itss.group14.timekeeper.ultis.Ultils;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessDataExcel {
    private Connection connection;
    private Map<String, List<String>> employeeData;
    private HashMap<String, String> employeeAndRole = new HashMap<>();
    private List<EmployeeShiftHours> employeeShiftHours = new ArrayList<>();
    private List<OfficerShiftAttendance> officerShiftAttendances = new ArrayList<>();


    public ProcessDataExcel(Map<String, List<String>> employeeData, Connection connection) {
        this.employeeData = employeeData;
        this.connection = connection;
    }

    public void PrintData() throws SQLException {
        for (Map.Entry<String, List<String>> entry : employeeData.entrySet()) {
            String employeeId = entry.getKey();
            List<String> timestamps = entry.getValue();

            System.out.println("Employee ID: " + employeeId);
            System.out.println("Timestamps: " + timestamps);
        }
        mapEmployeeIdAndRole();
    }

    public void mapEmployeeIdAndRole() throws SQLException {
        if(connection.isClosed() || connection == null){
            AbstractSQLConnection abstractSQLConnection = new SqliteConnection();
            connection = abstractSQLConnection.getConnection();
        }
        for (Map.Entry<String, List<String>> entry : employeeData.entrySet()) {
            String employeeId = entry.getKey();
            String resultSet = EmployeeService.getRoleEmployeeById(connection, employeeId);
            if (resultSet != null) {
                employeeAndRole.put(employeeId, resultSet);
            }
        }
    }

    public void processEmployeeData() throws SQLException {
        mapEmployeeIdAndRole();
        for (Map.Entry<String, List<String>> entry : employeeData.entrySet()) {
            String employeeId = entry.getKey();
            List<String> timestamps = entry.getValue();
            String role = employeeAndRole.get(employeeId);
            if (role != null) {
                try {
                    if (role.equals("worker")) {
                        employeeShiftHours = processWorkerData(employeeId, timestamps);
                    } else if (role.equals("officer")) {
                        officerShiftAttendances = processOfficerData(employeeId, timestamps);
                    }
                    ProcessSaveDataBase processSaveDataBase = new ProcessSaveDataBase(employeeShiftHours, officerShiftAttendances, connection);
                    processSaveDataBase.saveData();
                } catch (Exception e) {
                    Ultils.createDialog(Alert.AlertType.ERROR, "Error", "Error processing data", "An error occurred while processing data for employee ID: " + employeeId + ".");
                }
            } else {
                Ultils.createDialog(Alert.AlertType.ERROR, "Error", "Error processing data", "An error occurred while processing data for employee ID: " + employeeId + "." + "Employee ID not found in database.");
            }
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private List<OfficerShiftAttendance> processOfficerData(String employeeId, List<String> timestamps) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        Map<LocalDate, Boolean> shift1Attended = new HashMap<>();
        Map<LocalDate, Boolean> shift2Attended = new HashMap<>();
        Map<LocalDate, Double> shift1Hours = new HashMap<>();
        Map<LocalDate, Double> shift2Hours = new HashMap<>();

        for (int i = 0; i < timestamps.size() - 1; i += 2) {
            LocalDateTime start = LocalDateTime.parse(timestamps.get(i), formatter);
            LocalDateTime end = LocalDateTime.parse(timestamps.get(i + 1), formatter);
            double hours = (double) (end.toLocalTime().toSecondOfDay() - start.toLocalTime().toSecondOfDay()) / 3600;

            if (start.getHour() >= 8 && start.getHour() < 12) {
                shift1Attended.put(start.toLocalDate(), true);
                shift1Hours.merge(start.toLocalDate(), hours, Double::sum);
            } else if (start.getHour() >= 13 && start.getHour() < 17) {
                shift2Attended.put(start.toLocalDate(), true);
                shift2Hours.merge(start.toLocalDate(), hours, Double::sum);
            }
        }

        List<OfficerShiftAttendance> result = new ArrayList<>();
        for (Map.Entry<LocalDate, Boolean> entry : shift1Attended.entrySet()) {
            LocalDate date = entry.getKey();
            boolean isShift1Attended = entry.getValue();
            boolean isShift2Attended = shift2Attended.getOrDefault(date, false);
            double hoursLate = 0;
            double hoursEarlyLeave = 0;
            if (!isShift1Attended) {
                hoursLate += 4;
            }
            if (!isShift2Attended) {
                hoursEarlyLeave += 4;
            }
            double shift1Hour = shift1Hours.getOrDefault(date, 0.0);
            double shift2Hour = shift2Hours.getOrDefault(date, 0.0);
            if (isShift1Attended && shift1Hour < 4) {
                hoursEarlyLeave += 4 - shift1Hour;
            }
            if (isShift2Attended && shift2Hour < 4) {
                hoursEarlyLeave += 4 - shift2Hour;
            }
            result.add(new OfficerShiftAttendance(employeeId, date, isShift1Attended, isShift2Attended, hoursLate, hoursEarlyLeave));
        }
        return result;
    }


    private List<EmployeeShiftHours> processWorkerData(String employeeId, List<String> timestamps) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        Map<LocalDate, Double> shift1Hours = new HashMap<>();
        Map<LocalDate, Double> shift2Hours = new HashMap<>();
        Map<LocalDate, Double> shift3Hours = new HashMap<>();

        for (int i = 0; i < timestamps.size() - 1; i += 2) {
            LocalDateTime start = LocalDateTime.parse(timestamps.get(i), formatter);
            LocalDateTime end = LocalDateTime.parse(timestamps.get(i + 1), formatter);
            double hours = (double) (end.toLocalTime().toSecondOfDay() - start.toLocalTime().toSecondOfDay()) / 3600;

            if (start.getHour() >= 8 && start.getHour() < 12) {
                shift1Hours.merge(start.toLocalDate(), hours, Double::sum);
            } else if (start.getHour() >= 13 && start.getHour() < 17) {
                shift2Hours.merge(start.toLocalDate(), hours, Double::sum);
            } else if (start.getHour() >= 18 && start.getHour() < 22) {
                shift3Hours.merge(start.toLocalDate(), hours, Double::sum);
            }
        }

        List<EmployeeShiftHours> result = new ArrayList<>();
        for (LocalDate date : shift1Hours.keySet()) {
            result.add(new EmployeeShiftHours(employeeId, date,
                    shift1Hours.getOrDefault(date, 0.0),
                    shift2Hours.getOrDefault(date, 0.0),
                    shift3Hours.getOrDefault(date, 0.0)));
        }
        return result;
    }
}


/**
 * Called to initialize a controller after its root element has been
 * completely processed.
 *
 * @param location  The location used to resolve relative paths for the root object, or
 * {@code null} if the location is not known.
 * @param resources The resources used to localize the root object, or {@code null} if
 * the root object was not localized.
 */
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        AbstractSQLConnection sqliteConnection = new SqliteConnection();
//        try {
//            sqliteConnection.connect();
//            connection = sqliteConnection.getConnection();
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//    }

