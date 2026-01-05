package dev.cowycorn.incrgame.backend;

import dev.cowycorn.BigNumber;
import dev.cowycorn.incrgame.MainController;
import dev.cowycorn.incrgame.backend.currency.*;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class CurrencyManager {
    private static CurrencyManager instance;
    private Properties properties = new Properties();

    private List<Currency> secretCurrencies = new ArrayList<>();

    @Getter
    private List<Currency> currencies = new ArrayList<>();

    public void addToCurrency(Currency currency, BigNumber amount){

        amount = getMultiplier(currency).multiply(amount);
        currency.setAmount(currency.getAmount().add(amount));
    }

    public BigNumber getMultiplier(Currency currency){
        BigNumber amount = new BigNumber(1);
        List<Currency> currencies = new ArrayList<>(this.currencies);
        currencies.add(Unobtainable.getInstance());
        for(Currency cur : currencies){
            CurrencyEffect x = cur.getEffects().get(currency.getClass());
            if(x == null)
                continue;
            if(cur.getAmount().isZero())
                continue;

            amount = x.multiply(cur.getAmount(), amount);
        }
        return amount;
    }


    private CurrencyManager() {
        try (InputStream in = ButtonManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            properties.load(in);
        } catch (IOException e) {
            Logger log = LoggerFactory.getLogger(MainController.class);
            log.error("Failed to load button.properties");
        }


        currencies = new ArrayList<>();


        currencies.add(Prestige.getInstance());
        currencies.add(Rebirth.getInstance());
        currencies.add(Multiplier.getInstance());
        currencies.add(Money.getInstance());

    }
    public static CurrencyManager getInstance() {
        if (instance == null) {
            instance = new CurrencyManager();
        }
        return instance;
    }
}
