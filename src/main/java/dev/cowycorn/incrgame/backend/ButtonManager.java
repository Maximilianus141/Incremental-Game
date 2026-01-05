package dev.cowycorn.incrgame.backend;

import dev.cowycorn.BigNumber;
import dev.cowycorn.GameMath;
import dev.cowycorn.incrgame.MainController;
import dev.cowycorn.incrgame.backend.currency.Currency;
import dev.cowycorn.incrgame.backend.currency.Money;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

public class ButtonManager {
    private static ButtonManager instance;
    private CurrencyManager currencyManager = CurrencyManager.getInstance();
    private Properties properties = new Properties();

    private final int HOW_MANY_BUTTONS = 10;

    @Getter
    HashMap<Currency, ArrayList<Button>> buttons = new HashMap<>();

    public static ButtonManager getInstance() {
        if (instance == null)
            instance = new ButtonManager();
        return instance;
    }

    private ButtonManager() {
        try (InputStream in = ButtonManager.class.getClassLoader().getResourceAsStream("button.properties")) {
            properties.load(in);
        } catch (IOException e) {
            Logger log = LoggerFactory.getLogger(MainController.class);
            log.error("Failed to load button.properties");
        }
        Currency before = Money.getInstance();
        for (Currency cur : currencyManager.getCurrencies().reversed()){
            if (cur == Money.getInstance())
                continue;
            String propertyName = cur.getName().toLowerCase();

            double costIncrease = Double.parseDouble((String) properties.get(propertyName + ".upgrade.cost.increase"));

            double amountIncrease = Double.parseDouble((String) properties.get(propertyName + ".upgrade.amount.increase"));
            double baseCost = Double.parseDouble((String) properties.get(propertyName + ".upgrade.cost.base"));
            double baseAmount = Double.parseDouble((String) properties.get(propertyName + ".upgrade.amount.base"));
            boolean resetting = Boolean.parseBoolean((String) properties.get(propertyName + ".upgrade.resetting"));
            double costCurve = 0;
            try {
                costCurve = Double.parseDouble((String) properties.get(propertyName + ".upgrade.cost.curve"));
            } catch (Exception _) {}
            double amountCurve = 0;
            try {
                amountCurve = Double.parseDouble((String) properties.get(propertyName + ".upgrade.amount.curve"));
            } catch (Exception _) {}
            ArrayList<Button> buttons = new ArrayList<>();

            for (int i = 0; i < HOW_MANY_BUTTONS; i++) {
                Button b = new Button();
                b.setResetting(resetting);
                b.setFrom(before);
                b.setCost(GameMath.nextGeometricCost(new BigNumber(baseCost), new BigNumber(costIncrease * i * (costCurve + 1)), i));
                b.setTo(cur);
                b.setAmount(GameMath.nextGeometricCost(new BigNumber(baseAmount), new BigNumber(amountIncrease * i * (amountCurve + 1)), i));
                buttons.add(b);
            }
            before = cur;
            this.buttons.put(cur, buttons);
        }
    }

    private void resetBelow(Currency currency){
        boolean after = false;
        for (Currency cur : currencyManager.getCurrencies()){
            if (after){
                cur.setAmount(new BigNumber(0));
            }
            if (cur == currency){
                after = true;
            }

        }
    }

    public boolean pressButton(Button button) {
        if (button.getFrom().getAmount().greaterThan(button.getCost())) {
            currencyManager.addToCurrency(button.getTo(), button.getAmount());
            button.getFrom().setAmount(button.getFrom().getAmount().subtract(button.getCost()));
            if (button.resetting) {
                resetBelow(button.getTo());
            }
            return true;
        }
        return false;
    }



}
