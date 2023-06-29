package itss.group14.timekeeper.controllers.exportdata;

import itss.group14.timekeeper.contrains.FXMLconstrains;
import itss.group14.timekeeper.ultis.ViewChangeUltils;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

public class ChoseO_WexportDataController {
    private final ViewChangeUltils viewChangeUltils = new ViewChangeUltils();
    public Button backButton;

    public void handleBack(ActionEvent event) throws Exception {
        viewChangeUltils.changeView(event, FXMLconstrains.adminHomeFXML);
    }

    public void handleWorker(ActionEvent event) throws Exception {
        viewChangeUltils.changeView(event, FXMLconstrains.exportDataWorkerFXML);
    }

    public void handleOffice(ActionEvent event) throws Exception {
        viewChangeUltils.changeView(event, FXMLconstrains.exportDataOfficerFXML);
    }
}
