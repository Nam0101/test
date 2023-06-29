package itss.group14.timekeeper.model;

import java.time.LocalDate;

public class EmployeeShiftHours {
    private String employeeId;
    private LocalDate date;
    private double shift1Hours;
    private double shift2Hours;
    private double shift3Hours;

    public EmployeeShiftHours(String employeeId, LocalDate date, double shift1Hours, double shift2Hours, double shift3Hours) {
        this.employeeId = employeeId;
        this.date = date;
        this.shift1Hours = shift1Hours;
        this.shift2Hours = shift2Hours;
        this.shift3Hours = shift3Hours;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getShift1Hours() {
        return shift1Hours;
    }

    public double getShift2Hours() {
        return shift2Hours;
    }

    public double getShift3Hours() {
        return shift3Hours;
    }

    public String toString() {
        return "Employee ID: " + employeeId + " Date: " + date + " Shift 1: " + shift1Hours + " Shift 2: " + shift2Hours + " Shift 3: " + shift3Hours;
    }
}