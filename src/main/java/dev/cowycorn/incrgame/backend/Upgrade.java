package dev.cowycorn.incrgame.backend;


import dev.cowycorn.BigNumber;
import dev.cowycorn.incrgame.backend.currency.Currency;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Builder
public class Upgrade {
    @Getter
    private String name;

    @Getter
    private String description;

    @Getter
    @NonNull
    private UpgradeEffect  upgradeEffect;

    @Getter
    @NonNull
    private BigNumber cost;

    @Getter
    @NonNull
    private Currency currency;

    @Setter
    @Getter
    @Builder.Default
    private Boolean bought = false;
}
