package dev.cowycorn.incrgame;

import dev.cowycorn.BigNumber;
import dev.cowycorn.NumberFormatter;
import dev.cowycorn.incrgame.backend.ButtonManager;
import dev.cowycorn.incrgame.backend.CurrencyManager;
import dev.cowycorn.incrgame.backend.currency.Money;
import dev.cowycorn.incrgame.backend.currency.Multiplier;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        CurrencyManager currencyManager = CurrencyManager.getInstance();
        currencyManager.addToCurrency(Money.getInstance(), new BigNumber(1));

        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
        Updator updator = new Updator();

        executor.scheduleAtFixedRate(updator,0, 50, TimeUnit.MILLISECONDS);

        ButtonManager.getInstance();


    }
}
