package itss.group14.timekeeper.controllers.admin;

import itss.group14.timekeeper.contrains.FXMLconstrains;
import itss.group14.timekeeper.ultis.ViewChangeUltils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class AdminHomeController implements Initializable {
    private static final Preferences pref = Preferences.userRoot();
    private static final String name = pref.get("username", "");
    private final ViewChangeUltils viewChangeUltils = new ViewChangeUltils();
    @FXML
    public Button exportInforButton;
    @FXML
    public Button insertInforButton;
    @FXML
    public Button fixInforButton;
    @FXML
    public Button viewDataButton;
    ;
    @FXML
    public Text welcomeText;


    public AdminHomeController() throws SQLException {
    }

    public void fixInfor(ActionEvent event) throws Exception {
        viewChangeUltils.changeView(event, FXMLconstrains.danhSachYCFXML);
    }

    public void importInfor(ActionEvent event) throws Exception {
        viewChangeUltils.changeView(event, FXMLconstrains.importDataSelectFXML);
    }

    public void exportInfor(ActionEvent event) throws Exception {
        viewChangeUltils.changeView(event, FXMLconstrains.W_or_OexportDataFXML);
    }

    public void viewInfor(ActionEvent event) {
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
        welcomeText.setText("Xin chào " + name + "!");

    }

    public void logout(ActionEvent event) throws Exception {
        viewChangeUltils.changeView(event, FXMLconstrains.loginFXML);
    }
}
