package itss.group14.timekeeper.controllers.exportdata;

import itss.group14.timekeeper.model.export.WorkerExportInfor;
import itss.group14.timekeeper.ultis.Ultils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;

public class ExportExcelProcessController {
    private ObservableList<WorkerExportInfor> records = FXCollections.observableArrayList();

    public ExportExcelProcessController(ObservableList<WorkerExportInfor> records) {
        this.records = records;
    }


    public void exportToExcelWorker(String fileName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Data");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Họ và Tên");
        headerRow.createCell(1).setCellValue("Đơn vị");
        headerRow.createCell(2).setCellValue("Mã NV");
        headerRow.createCell(3).setCellValue("Tháng");
        headerRow.createCell(4).setCellValue("Thời gian làm việc");
        headerRow.createCell(5).setCellValue("Thời gian tăng ca");

        int rowNum = 1;
        for (WorkerExportInfor record : records) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(record.getName());
            row.createCell(1).setCellValue(record.getDepartment());
            row.createCell(2).setCellValue(record.getEmployee_id());
            row.createCell(3).setCellValue(record.getMonth());
            row.createCell(4).setCellValue(record.getWorkingTime());
            row.createCell(5).setCellValue(record.getOvertime());
        }

        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
            workbook.write(fileOut);
        }
        Ultils.createDialog(Alert.AlertType.INFORMATION, "Thành công", "Xuất thành công", "Xuất thành công file " + fileName);
    }

}

