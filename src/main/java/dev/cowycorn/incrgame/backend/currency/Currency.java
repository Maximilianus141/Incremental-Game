package dev.cowycorn.incrgame.backend.currency;

import dev.cowycorn.BigNumber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@AllArgsConstructor
public abstract class Currency {

    @Getter
    @Setter
    private BigNumber amount = new BigNumber(0);
    @Getter
    private final String name;
    @Setter
    @Getter
    private HashMap<Class, CurrencyEffect> effects;

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
