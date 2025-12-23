package dev.cowycorn.incrgame.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FX {
    public static void loadToNewScene(FXMLLoader fxmlLoader, Scene currentScene, String styleSheetPath) {
        try {
            Parent root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage stage = (Stage) currentScene.getWindow();
        Scene scene = new Scene(fxmlLoader.getRoot(), currentScene.getWidth(), currentScene.getHeight());
        scene.getStylesheets().add(FX.class.getResource("/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}
