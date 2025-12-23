package dev.cowycorn.incrgame.backend;

import dev.cowycorn.BigNumber;
import dev.cowycorn.incrgame.backend.currency.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class CurrencyManager {
    private static CurrencyManager instance;

    @Getter
    private List<Currency> currencies = new ArrayList<>();

    public void addToCurrency(Currency currency, BigNumber amount){
        for(Currency cur : currencies){
            CurrencyEffect x = cur.getEffects().get(currency.getClass());
            if(x == null)
                continue;
            if(cur.getAmount().isZero())
                continue;

            amount = x.multiply(cur.getAmount(), amount);
        }
        currency.setAmount(currency.getAmount().add(amount));
    }


    private CurrencyManager() {
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
