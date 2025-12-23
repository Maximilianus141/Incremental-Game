package dev.cowycorn.incrgame.backend.currency;

import dev.cowycorn.BigNumber;

import java.util.HashMap;

public class Money extends Currency{
    private static Money instance;

    private Money(BigNumber amount, String name, HashMap<Class, CurrencyEffect> effects) {
        super(amount, name, effects);
    }

    public static Money getInstance(){

        if(instance == null){
            instance = new Money(new BigNumber(0), "Money", new HashMap<>());
        }
        return instance;
    }
}
