package itss.group14.timekeeper.ultis;

import javafx.scene.control.Alert;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Ultils {
    public static LocalDate LOCAL_DATE(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(dateString, formatter);
    }

    public static void createDialog(Alert.AlertType type, String title, String headerText, String contentText) {
        Alert warning = new Alert(type);
        warning.setTitle(title);
        warning.setHeaderText(headerText);
        warning.setContentText(contentText);
        warning.showAndWait();
    }
    public static void convertDate(String date){
        String[] dateSplit = date.split("-");
        String newDate = dateSplit[2] + "/" + dateSplit[1] + "/" + dateSplit[0];
        System.out.println(newDate);
    }

}
