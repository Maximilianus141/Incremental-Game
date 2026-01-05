package dev.cowycorn.incrgame;

import dev.cowycorn.BigNumber;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainController extends ControllerWithCurrencyInfo{
    Logger log = LoggerFactory.getLogger(MainController.class);
    ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);

    private Map<Currency, Label> toUpdate = new java.util.HashMap<>();
    private Map<Currency, Label> multipliersToUpdate = new java.util.HashMap<>();

    public BorderPane mainBP;

    public VBox buttonVBox;
    public VBox sideVbox;


    public void initialize() {
        long startTime = System.currentTimeMillis();
        loadButtons(CurrencyManager.getInstance().getCurrencies().reversed());
        loadSidePanel(CurrencyManager.getInstance().getCurrencies().reversed());
        mainBP.sceneProperty().addListener((_, _, newScene) -> {
            if (newScene != null) {
                registerKeyHandlers(newScene);
            }
        });
        MainPageUpdator updator = new MainPageUpdator(this);

        executor.scheduleAtFixedRate(updator,0, 40, TimeUnit.MILLISECONDS);
        log.info("Loaded in {}ms", System.currentTimeMillis() - startTime);
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


    private void loadButtons(List<Currency> currencies){
        for (Currency cur : currencies){


            VBox box = new VBox();
            box.setSpacing(10);
            buttonVBox.getChildren().add(box);

            Label l = new Label(cur.getName() + ": " + formatNum(cur));
            l.getStyleClass().add("currency-label");
            l.getStyleClass().add(cur.getName().toLowerCase().strip() + "-label");
            toUpdate.put(cur, l);

            box.getChildren().add(l);
            if (cur == Money.getInstance())
                continue;
            TilePane tp = new TilePane();

            tp.setHgap(15);
            tp.setVgap(15);
            tp.setPrefColumns(5);


            fillBox(ButtonManager.getInstance().getButtons().get(cur), tp.getChildren());
            box.getChildren().add(tp);

        }
    }


    public void updateLabels(){
        // Wrap UI updates in Platform.runLater
        javafx.application.Platform.runLater(() -> {
            toUpdate.forEach((cur, label) -> {
                label.setText(cur.getName() + ": " + formatNum(cur));
                // log.info("Updated {}", label.getText()); // Optional: Comment out to reduce console spam every 40ms
            });
            multipliersToUpdate.forEach((cur, label) -> {
                label.setText(FX.getFormattedCurrencyInfo(cur));
            });
        });
    }

    private static String formatNum(Currency cur) {
        return formatNum(cur.getAmount());
    }
    private static String formatNum(BigNumber num){
        return Config.get().getNumberFormatter().format(num);
    }


    private static void fillBox(List<dev.cowycorn.incrgame.backend.Button> buttons, ObservableList<Node> boxchilds) {
        buttons.forEach((b) -> {
            var button = new Button("%s %s -> %s".formatted(formatNum(b.getCost()), b.getFrom().getName(), formatNum(b.getAmount())));
            button.getStyleClass().add("currency-button");
            button.setOnAction(((event) -> {
                ButtonManager.getInstance().pressButton(b);
            }));
            boxchilds.add(button);
        });
    }

/*    private void initializeSidePanel(){
        var childs = sideVbox.getChildren();
        var currencies = CurrencyManager.getInstance().getCurrencies();
        for (Currency c : currencies) {
            var label = new Label(c.getName());
            label.getStyleClass().add("currency-label");
            label.getStyleClass().add(c.getName().toLowerCase().strip() + "-label");
            childs.add(label);
        }
    }*/


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

    public void upgradesButton(ActionEvent actionEvent) {
        FXMLLoader fl = new FXMLLoader(getClass().getResource("/upgrades.fxml"));
        executor.shutdown();
        FX.loadToNewScene(fl, mainBP.getScene(), "/styles.css");

    }
}
