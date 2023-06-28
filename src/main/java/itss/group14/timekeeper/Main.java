package itss.group14.timekeeper;

import itss.group14.timekeeper.contrains.FXMLconstrains;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(FXMLconstrains.loginFXML));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Phần mềm chấm công");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}