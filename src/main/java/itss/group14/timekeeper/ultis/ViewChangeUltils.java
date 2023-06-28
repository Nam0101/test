package itss.group14.timekeeper.ultis;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ViewChangeUltils {

    public ViewChangeUltils() {
    }

    public void changeView(ActionEvent event, String viewPath) throws Exception {
        Stage stage;
        Scene scene;
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
        root = loader.load();
        scene = new Scene(root);
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void changeView(Scene scene, String viewPath) throws Exception {
        Stage stage;
        Parent root;
        FXMLLoader loader = new FXMLLoader(getClass().getResource(viewPath));
        root = loader.load();
        stage = (Stage) scene.getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }


}
