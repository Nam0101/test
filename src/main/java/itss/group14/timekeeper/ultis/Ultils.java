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

    public Ultils() {
    }

    public static String convertMonthYear(String month, String year) {
        if (month.length() == 1) {
            month = "0" + month;
        }
        return month + "/" + year;
    }

    public static String convertDayMonthYear(String day, String month, String year) {
        if (month.length() == 1) {
            month = "0" + month;
        }
        if (day.length() == 1) {
            day = "0" + day;
        }
        return day + "/" + month + "/" + year;
    }

    public static boolean isNumber(String num) {
        try {
            Double.parseDouble(num);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    public static boolean isNumeric(String monthText) {
        try {
            Integer.parseInt(monthText);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
