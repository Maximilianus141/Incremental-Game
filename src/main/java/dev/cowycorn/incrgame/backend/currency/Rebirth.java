package dev.cowycorn.incrgame.backend.currency;

import dev.cowycorn.BigNumber;

import java.util.HashMap;
import java.util.Map;

public class Rebirth extends Currency{
    private static Rebirth instance;

    private Rebirth(BigNumber amount, String name, HashMap<Class, CurrencyEffect> effects) {
        super(amount, name, effects);
    }

    public static Rebirth getInstance(){
        if(instance == null){
            CurrencyEffect multiplierMultiplier = new CurrencyEffect() {
                @Override
                public BigNumber multiply(BigNumber ownAmount, BigNumber otherAmount) {
                    return ownAmount.add(new BigNumber(1)).multiply(otherAmount);
                }
            };
            instance = new Rebirth(new BigNumber(0), "Rebirth", new HashMap<>(Map.of(Multiplier.class, multiplierMultiplier)));
        }
        return instance;
    }
}
