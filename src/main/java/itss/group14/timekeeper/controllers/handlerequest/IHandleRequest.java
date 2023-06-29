package itss.group14.timekeeper.controllers.handlerequest;

import itss.group14.timekeeper.model.Request;
import javafx.event.ActionEvent;

public interface IHandleRequest {

    void displayRequestDetails(Request request);

    void chapNhanClick(ActionEvent actionEvent) throws Exception;

    void tuChoiClick(ActionEvent actionEvent) throws Exception;
}
