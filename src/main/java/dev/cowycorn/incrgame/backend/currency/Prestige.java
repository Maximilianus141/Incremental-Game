package dev.cowycorn.incrgame.backend.currency;

import dev.cowycorn.BigNumber;

import java.util.HashMap;

public class Prestige extends Currency{
    private static Prestige instance;

    private Prestige(BigNumber amount, String name, HashMap<Class, CurrencyEffect> effects) {
        super(amount, name, effects);
    }

    public static Prestige getInstance(){
        if(instance == null){
            CurrencyEffect rebirthMultiplier = new CurrencyEffect() {
                @Override
                public BigNumber multiply(BigNumber ownAmount, BigNumber otherAmount) {
                    return ownAmount.add(new BigNumber(1)).multiply(otherAmount);
                }
            };
            CurrencyEffect moneyMultiplier = new CurrencyEffect() {
                @Override
                public BigNumber multiply(BigNumber ownAmount, BigNumber otherAmount) {
                    return ownAmount.multiply(new BigNumber(0.05)).add(new BigNumber(1)).multiply(otherAmount);
                }
            };

            HashMap<Class, CurrencyEffect> effects = new HashMap<>();
            effects.put(Rebirth.class, rebirthMultiplier);
            effects.put(Money.class, moneyMultiplier);

            instance = new Prestige(new BigNumber(0), "Prestige", effects);
        }
        return instance;
    }
}
