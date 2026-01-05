package dev.cowycorn.incrgame;

import dev.cowycorn.NumberFormatter;
import dev.cowycorn.incrgame.util.FX;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    private Logger log = LoggerFactory.getLogger(this.getClass());


    @FXML
    public Button playButton;
    @FXML
    public Button loadButton;
    @FXML
    public Button settingsButton;
    @FXML
    public Button exitButton;
    public Label title;

    @FXML private VBox settingsPanel;
    @FXML private ComboBox<String> notationCombo;
    @FXML private ComboBox<String> autoSaveCombo;


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
        notationCombo.getItems().addAll("Standard", "Scientific", "Engineering");
        notationCombo.getSelectionModel().selectFirst(); // Default to first

        notationCombo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Standard" ->{
                    Config.get().setNumberFormatter(new NumberFormatter(NumberFormatter.NotationStyle.STANDARD));
                    notationCombo.getSelectionModel().select(0);
                }
                case "Scientific" ->{
                    Config.get().setNumberFormatter(new NumberFormatter(NumberFormatter.NotationStyle.SCIENTIFIC));
                    notationCombo.getSelectionModel().select(1);
                }
                case "Engineering" ->{
                    Config.get().setNumberFormatter(new NumberFormatter(NumberFormatter.NotationStyle.ENGINEERING));
                    notationCombo.getSelectionModel().select(2);
                }
            }
        });

        autoSaveCombo.getItems().addAll("10s", "30s", "60s", "Off");
        autoSaveCombo.getSelectionModel().select("30s");
    }

    public void toggleSettings(ActionEvent actionEvent) {
        boolean isVisible = settingsPanel.isVisible();
        settingsPanel.setVisible(!isVisible);
    }
}
