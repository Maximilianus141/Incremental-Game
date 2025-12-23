package dev.cowycorn.incrgame;

import dev.cowycorn.NumberFormatter;
import dev.cowycorn.incrgame.backend.ButtonManager;
import dev.cowycorn.incrgame.backend.CurrencyManager;
import dev.cowycorn.incrgame.backend.currency.Currency;
import dev.cowycorn.incrgame.backend.currency.Money;
import dev.cowycorn.incrgame.util.FX;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainController {
    Logger log = LoggerFactory.getLogger(MainController.class);

    private Map<Currency, Label> toUpdate = new java.util.HashMap<>();

    public VBox sideVbox;


    public VBox mainVbox;

    public void initialize() {
        long startTime = System.currentTimeMillis();
        initializeButtons();
        initializeSidePanel();
        mainVbox.sceneProperty().addListener((_, _, newScene) -> {
            if (newScene != null) {
                registerKeyHandlers(newScene);
            }
        });
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        MainPageUpdator updator = new MainPageUpdator(this);

        executor.scheduleAtFixedRate(updator,0, 40, TimeUnit.MILLISECONDS);
        log.info("Loaded in {}ms", System.currentTimeMillis() - startTime);
    }

    private void initializeButtons() {
        var childs = mainVbox.getChildren();
        var buts = CurrencyManager.getInstance().getCurrencies().reversed();

        for (Currency cur : buts) {
            var label = new Label(cur.getName() + ": " + NumberFormatter.quickFormat(cur.getAmount()));
            toUpdate.put(cur, label);
            label.getStyleClass().add("currency-label");
            label.getStyleClass().add(cur.getName().toLowerCase().strip() + "-label");
            childs.add(label);
            if(cur.getClass() == Money.class)
                continue;
            initButtons(cur, childs);
        }
    }
    public void updateLabels(){
        // Wrap UI updates in Platform.runLater
        javafx.application.Platform.runLater(() -> {
            toUpdate.forEach((cur, label) -> {
                label.setText(cur.getName() + ": " + NumberFormatter.quickFormat(cur.getAmount()));
                // log.info("Updated {}", label.getText()); // Optional: Comment out to reduce console spam every 40ms
            });
        });
    }

    private void initButtons(Currency cur, ObservableList<Node> childs) {
        var box = new HBox();
        box.setMinWidth(1400);
        var box2 = new HBox();
        box2.setMinWidth(1400);
        box.setSpacing(5);
        box2.setSpacing(5);
        List<dev.cowycorn.incrgame.backend.Button> buttons = ButtonManager.getInstance().getButtons().get(cur);
        var firstHalfButtons = buttons.subList(0, buttons.size() / 2);
        var secondHalfButtons = buttons.subList(buttons.size() / 2, buttons.size());
        var boxchilds = box.getChildren();
        var boxchilds2 = box2.getChildren();

        log.info("Loaded {} buttons for {}", buttons.size(), cur.getName());

        fillBox(firstHalfButtons, boxchilds);
        fillBox(secondHalfButtons, boxchilds2);

        childs.addAll(box, box2);
    }

    private static void fillBox(List<dev.cowycorn.incrgame.backend.Button> buttons, ObservableList<Node> boxchilds) {
        buttons.forEach((b) -> {
            var button = new Button("%s %s -> %s".formatted(NumberFormatter.quickFormat(b.getCost()), b.getFrom().getName(), NumberFormatter.quickFormat(b.getAmount())));
            button.setOnAction(((event) -> {
                ButtonManager.getInstance().pressButton(b);
            }));
            boxchilds.add(button);
        });
    }

    private void initializeSidePanel(){
        var childs = sideVbox.getChildren();
        var currencies = CurrencyManager.getInstance().getCurrencies();
        for (Currency c : currencies) {
            var label = new Label(c.getName());
            label.getStyleClass().add("currency-label");
            label.getStyleClass().add(c.getName().toLowerCase().strip() + "-label");
            childs.add(label);
        }
    }


    private void registerKeyHandlers(Scene scene) {
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            switch (event.getCode()) {
                case ESCAPE ->{
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/menu.fxml"));
                    FX.loadToNewScene(fxmlLoader, mainVbox.getScene(), "/styles.css");
                }
            }
        });
    }

    public void upgradesButton(ActionEvent actionEvent) {
        FXMLLoader fl = new FXMLLoader(getClass().getResource("/upgrades.fxml"));
        FX.loadToNewScene(fl, mainVbox.getScene(), "/styles.css");
    }
}
