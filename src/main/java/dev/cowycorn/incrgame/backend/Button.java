package dev.cowycorn.incrgame.backend;

import dev.cowycorn.BigNumber;
import dev.cowycorn.incrgame.backend.currency.Currency;
import lombok.Data;

@Data
public class Button {
    boolean resetting;

    Currency from;
    BigNumber cost;

    Currency to;
    BigNumber amount;


}
