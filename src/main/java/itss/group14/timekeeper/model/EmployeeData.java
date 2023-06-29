package itss.group14.timekeeper.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class EmployeeData {
    private final StringProperty employeeId;
    private final StringProperty date;
    private final StringProperty time;

    public EmployeeData(String employeeId, String date, String time) {
        this.employeeId = new SimpleStringProperty(employeeId);
        this.date = new SimpleStringProperty(date);
        this.time = new SimpleStringProperty(time);
    }

    public String getEmployeeId() {
        return employeeId.get();
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId.set(employeeId);
    }

    public StringProperty employeeIdProperty() {
        return employeeId;
    }


    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public StringProperty dateProperty() {
        return date;
    }

    public String getTime() {
        return time.get();
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public StringProperty timeProperty() {
        return time;
    }
}
