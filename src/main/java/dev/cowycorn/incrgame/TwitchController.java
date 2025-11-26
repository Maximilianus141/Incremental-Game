package dev.cowycorn.incrgame;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class TwitchController implements Initializable {


    @FXML
    private void goBack(ActionEvent event){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main.fxml"));
        try {
            Parent root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        Scene scene = new Scene(fxmlLoader.getRoot());
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
