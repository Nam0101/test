module itss.group14.timekeeper {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.xerial.sqlitejdbc;
    requires java.prefs;
    requires org.apache.poi.ooxml;

    opens itss.group14.timekeeper to javafx.fxml;
    exports itss.group14.timekeeper;
    exports itss.group14.timekeeper.controllers;
    exports itss.group14.timekeeper.model;
    exports itss.group14.timekeeper.ultis;
    opens itss.group14.timekeeper.controllers to javafx.fxml;
    exports itss.group14.timekeeper.model.record;
    exports itss.group14.timekeeper.model.details;
    exports itss.group14.timekeeper.controllers.importdata;
    opens itss.group14.timekeeper.controllers.importdata to javafx.fxml;
    exports itss.group14.timekeeper.controllers.exportdata;
    exports itss.group14.timekeeper.controllers.infomationFix;
    opens itss.group14.timekeeper.controllers.infomationFix to javafx.fxml;
}
