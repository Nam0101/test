package itss.group14.timekeeper.controllers;

import itss.group14.timekeeper.contrains.FXMLconstrains;
import itss.group14.timekeeper.dbservices.dbconection.AbstractSQLConnection;
import itss.group14.timekeeper.dbservices.dbconection.SqliteConnection;
import itss.group14.timekeeper.model.Request;
import itss.group14.timekeeper.ultis.ViewChangeUltils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class SuaThongTinOfficerController implements Initializable {
    public final ViewChangeUltils viewChangeUltils = new ViewChangeUltils();
    public CheckBox checkBoxSang;
    public CheckBox checkBoxChieu;
    public Button sentButton;
    public Label maNV;
    public Label tenNV;
    public TextField suaCa1;
    public Label NgaySua;
    public TextField suaCa2;
    public Button BackButton;
    public Label bophan;
    private Request selectedRequest;

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
            Connection connection = sqliteConnection.getConnection();
        } catch (Exception throwables) {
            throwables.printStackTrace();
        }
    }

    public void backAction(ActionEvent event) throws Exception {
        viewChangeUltils.changeView(event, FXMLconstrains.danhSachYCFXML);
    }

    public void setOfficerData(String employeeName, String employeeId, String Bophan, String date, double dimuon, double vesom, boolean sang, boolean chieu, Request selectedRequest) {
        this.tenNV.setText(employeeName);
        this.maNV.setText(employeeId);
        this.NgaySua.setText(date);
        this.bophan.setText(Bophan);
        this.suaCa1.setText(String.valueOf(dimuon));
        this.suaCa2.setText(String.valueOf(vesom));
        this.checkBoxSang.setSelected(sang);
        this.checkBoxChieu.setSelected(chieu);
        this.selectedRequest = selectedRequest;
    }

    public void suaTT(ActionEvent event) {
    }
}
