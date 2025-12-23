package dev.cowycorn.incrgame.backend.currency;

import dev.cowycorn.BigNumber;

import java.util.HashMap;
import java.util.Map;

public class Multiplier extends Currency{
    private static Multiplier instance;

    private Multiplier(BigNumber amount, String name, HashMap<Class, CurrencyEffect> effects) {
        super(amount, name, effects);
    }

    public static Multiplier getInstance(){
        if(instance == null){
            CurrencyEffect moneyMultiplier = new CurrencyEffect() {
                @Override
                public BigNumber multiply(BigNumber ownAmount, BigNumber otherAmount) {
                    return ownAmount.divide(new BigNumber(2)).add(new BigNumber(1)).multiply(otherAmount);
                }
            };
            instance = new Multiplier(new BigNumber(0), "Multiplier", new HashMap<>(Map.of(Money.class, moneyMultiplier)));
        }
        return instance;
    }
}
