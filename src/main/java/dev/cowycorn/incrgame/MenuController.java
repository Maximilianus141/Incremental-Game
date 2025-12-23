package dev.cowycorn.incrgame;

import dev.cowycorn.incrgame.util.FX;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    public Button playButton;
    @FXML
    public Button loadButton;
    @FXML
    public Button settingsButton;
    @FXML
    public Button exitButton;
    public Label title;

    @FXML
    private void playButton(ActionEvent event){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/main.fxml"));
        FX.loadToNewScene(fxmlLoader, playButton.getScene(), "/styles.css");
    }



    @FXML
    private void loadButton(ActionEvent event){
        
    }
    @FXML
    private void settingsButton(ActionEvent event){

    }
    @FXML
    private void exitButton(ActionEvent event){

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
