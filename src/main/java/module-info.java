module itss.group14.timekeeper {
    requires javafx.controls;
    requires javafx.fxml;

        requires org.controlsfx.controls;
            requires com.dlsc.formsfx;
                    requires org.kordamp.bootstrapfx.core;
                    requires org.xerial.sqlitejdbc;

    opens itss.group14.timekeeper to javafx.fxml;
    exports itss.group14.timekeeper;
    exports itss.group14.timekeeper.controllers;
    opens itss.group14.timekeeper.controllers to javafx.fxml;
}