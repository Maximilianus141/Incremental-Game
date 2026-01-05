package dev.cowycorn.incrgame;

import dev.cowycorn.NumberFormatter;
import dev.cowycorn.incrgame.backend.CurrencyManager;
import dev.cowycorn.incrgame.backend.Upgrade;
import dev.cowycorn.incrgame.backend.UpgradeManager;
import dev.cowycorn.incrgame.backend.currency.Currency;
import dev.cowycorn.incrgame.util.FX;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class UpgradesController extends ControllerWithCurrencyInfo{
    public VBox upgradeContainer;
    Logger log = LoggerFactory.getLogger(UpgradesController.class);
    ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);




    public Map<Upgrade, Button> toUpdate = new HashMap<>();

    public BorderPane mainBP;

    private Map<Currency, Label> multipliersToUpdate = new java.util.HashMap<>();

    public VBox sideVbox;

    public void initialize() {
        long startTime = System.currentTimeMillis();
        loadSidePanel(CurrencyManager.getInstance().getCurrencies().reversed());
        loadUpgrades(UpgradeManager.getInstance().getUpgrades());

        mainBP.sceneProperty().addListener((_, _, newScene) -> {
            if (newScene != null) {
                registerKeyHandlers(newScene);
            }
        });

        MainPageUpdator updator = new MainPageUpdator(this);

        executor.scheduleAtFixedRate(updator,0, 40, TimeUnit.MILLISECONDS);
        log.info("Loaded in {}ms", System.currentTimeMillis() - startTime);
    }


    public void updateLabels(){
        // Wrap UI updates in Platform.runLater
        javafx.application.Platform.runLater(() -> {
            multipliersToUpdate.forEach((cur, label) -> {
                label.setText(FX.getFormattedCurrencyInfo(cur));
            });
        });
    }

    private void loadUpgrades(List<Upgrade> upgrades) {
        for (Upgrade upgrade : upgrades) {
            HBox box = new HBox();
            box.setSpacing(10);
            box.getStyleClass().add("upgrade-box");
            box.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

            upgradeContainer.getChildren().add(box);

            TextField tf = new TextField(upgrade.getDescription());
            tf.setEditable(false);
            tf.getStyleClass().add("upgrade-description");
            HBox.setHgrow(tf, javafx.scene.layout.Priority.ALWAYS);

            box.getChildren().add(tf);

            Button b;



            if (!upgrade.getBought()) {
                b = new Button(Config.get().getNumberFormatter().format(upgrade.getCost()) + " " + upgrade.getCurrency().getName());
                b.getStyleClass().add("buy-button");
            } else {
                buttonSetBought(b = new Button("Bought"));
            }

            b.setPrefWidth(100);
            b.setOnAction(event -> {
                UpgradeManager.getInstance().buyUpgrade(upgrade);
                updateButtons();
            });
            toUpdate.put(upgrade, b);
            box.getStyleClass().add("upgrade-box");
            box.paddingProperty().set(new javafx.geometry.Insets(10, 10, 10, 10));
            box.getChildren().add(b);
        }
    }

    private void buttonSetBought(Button b){
        b.setText("Bought");
        b.getStyleClass().add("bought-button");
        b.getStyleClass().remove("buy-button");
    }

    public void updateButtons(){
        toUpdate.forEach((u, b) -> {
            if (u.getBought()) {
                buttonSetBought(b);
            }
        });
    }

    private void loadSidePanel(List<Currency> currencies){
        for (Currency cur : currencies){
            Label l = new Label(FX.getFormattedCurrencyInfo(cur));
            l.getStyleClass().add("currency-label");
            l.getStyleClass().add(cur.getName().toLowerCase().strip() + "-label");
            l.setStyle("-fx-font-size: 18;");
            sideVbox.getChildren().add(l);
            multipliersToUpdate.put(cur, l);
        }
    }


    public void back(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/main.fxml"));
        FX.loadToNewScene(loader, mainBP.getScene(), "/styles.css");
    }

    private void registerKeyHandlers(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case ESCAPE ->{
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/menu.fxml"));
                    executor.shutdown();
                    FX.loadToNewScene(fxmlLoader, mainBP.getScene(), "/styles.css");
                }
            }
        });
    }
}
