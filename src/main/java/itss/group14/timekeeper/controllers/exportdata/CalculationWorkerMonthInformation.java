package itss.group14.timekeeper.controllers.exportdata;

import itss.group14.timekeeper.model.record.WorkerAttendanceRecord;

import java.util.HashMap;
import java.util.Map;

public class CalculationWorkerMonthInformation {
    private HashMap<String, WorkerAttendanceRecord> workerAttendanceRecordHashMap = new HashMap<>();

    public CalculationWorkerMonthInformation(HashMap<String, WorkerAttendanceRecord> workerAttendanceRecordHashMap) {
        this.workerAttendanceRecordHashMap = workerAttendanceRecordHashMap;
    }

    public HashMap<String, WorkerAttendanceRecord> getWorkerAttendanceRecordHashMap() {
        return workerAttendanceRecordHashMap;
    }
    public HashMap<String, Object[]> calculate(HashMap<String, WorkerAttendanceRecord> workerAttendanceRecordHashMap) {
    HashMap<String, Object[]> result = new HashMap<>();
    for (Map.Entry<String, WorkerAttendanceRecord> entry : workerAttendanceRecordHashMap.entrySet()) {
        String employeeId = entry.getKey();
        WorkerAttendanceRecord record = entry.getValue();
        double shift1Hours = record.getShiftHours1();
        double shift2Hours = record.getShiftHours2();
        double shift3Hours = record.getShiftHours3();
        double totalHours = shift1Hours + shift2Hours;
        double overtimeHours = shift3Hours;
        String name = record.get();
        String department = record.getDepartment();
        result.put(employeeId, new Object[]{name, department, totalHours, overtimeHours});
    }
    return result;
}
}
