package dev.cowycorn.incrgame.backend.currency;

import dev.cowycorn.BigNumber;

public interface CurrencyEffect {
    BigNumber multiply(BigNumber ownAmount, BigNumber otherAmount);
}
