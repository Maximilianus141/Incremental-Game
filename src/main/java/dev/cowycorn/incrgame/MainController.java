package dev.cowycorn.incrgame;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    boolean pressed = false;


    @FXML
    private Label coolLabel;
    @FXML
    private Label titleLabel;
    @FXML
    private Button actionButton;

    @FXML
    private void handleButtonClick(ActionEvent event){
        if (!pressed) {
            titleLabel.setText("Hello World!");
            pressed = true;
        } else {
            titleLabel.setText("Goodbye World!");
            pressed = false;
        }

    }

    @FXML
    private void twitch(ActionEvent event){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/twitch.fxml"));
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
