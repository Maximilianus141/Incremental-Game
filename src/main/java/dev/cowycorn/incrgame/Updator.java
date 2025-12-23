package dev.cowycorn.incrgame;

import dev.cowycorn.BigNumber;
import dev.cowycorn.incrgame.backend.CurrencyManager;
import dev.cowycorn.incrgame.backend.currency.Money;

public class Updator implements Runnable {
    CurrencyManager currencyManager = CurrencyManager.getInstance();

    @Override
    public void run() {
        currencyManager.addToCurrency(Money.getInstance(), new BigNumber(0.2));
    }
}
