package dev.cowycorn.incrgame.backend.currency;

import dev.cowycorn.BigNumber;

import java.util.HashMap;

public class Unobtainable extends Currency{
    private static Unobtainable instance;

    private Unobtainable(BigNumber amount, String name, HashMap<Class, CurrencyEffect> effects) {
        super(amount, name, effects);
    }

    public static Unobtainable getInstance(){
        if(instance == null){
            CurrencyEffect rebirthMultiplier = new CurrencyEffect() {
                @Override
                public BigNumber multiply(BigNumber ownAmount, BigNumber otherAmount) {
                    return ownAmount.add(new BigNumber(10).pow(1)).multiply(otherAmount);
                }
            };
            CurrencyEffect prestigeMultiplier = new CurrencyEffect() {
                @Override
                public BigNumber multiply(BigNumber ownAmount, BigNumber otherAmount) {
                    return ownAmount.add(new BigNumber(10).pow(1)).multiply(otherAmount);
                }
            };
            CurrencyEffect multiplierMultiplier = new CurrencyEffect() {
                @Override
                public BigNumber multiply(BigNumber ownAmount, BigNumber otherAmount) {
                    return ownAmount.add(new BigNumber(10).pow(1)).multiply(otherAmount);
                }
            };


            HashMap<Class, CurrencyEffect> effects = new HashMap<>();
            effects.put(Rebirth.class, rebirthMultiplier);
            effects.put(Prestige.class, prestigeMultiplier);
            effects.put(Multiplier.class, multiplierMultiplier);

            instance = new Unobtainable(new BigNumber(0), "Unobtainable", effects);
        }
        return instance;
    }
}
