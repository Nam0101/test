package itss.group14.timekeeper.model;

import java.time.LocalDate;

public class OfficerShiftAttendance {
    private String employeeId;
    private LocalDate date;
    private boolean shift1Attended;
    private boolean shift2Attended;
    private double hoursLate;
    private double hoursEarly;

    public OfficerShiftAttendance(String employeeId, LocalDate date, boolean shift1Attended, boolean shift2Attended, double hoursLate, double hoursEarly) {
        this.employeeId = employeeId;
        this.date = date;
        this.shift1Attended = shift1Attended;
        this.shift2Attended = shift2Attended;
        this.hoursLate = hoursLate;
        this.hoursEarly = hoursEarly;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isShift1Attended() {
        return shift1Attended;
    }

    public boolean isShift2Attended() {
        return shift2Attended;
    }

    public double getHoursLate() {
        return hoursLate;
    }

    public double getHoursEarly() {
        return hoursEarly;
    }

    public String toString() {
        return "Employee ID: " + employeeId + " Date: " + date + " Shift 1: " + shift1Attended + " Shift 2: " + shift2Attended + " Hours Late: " + hoursLate + " Hours Early: " + hoursEarly;
    }
}
