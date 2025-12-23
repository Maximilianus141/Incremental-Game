package dev.cowycorn.incrgame.backend;

import dev.cowycorn.BigNumber;
import dev.cowycorn.incrgame.backend.currency.Money;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class UpgradeManager {
    private static UpgradeManager instance;

    @Getter
    private List<Upgrade> upgrades = new ArrayList<>();

    private UpgradeManager() {
        Upgrade u = Upgrade.builder()
                .name("first upgrade")
                .description("adds one singular money")
                .upgradeEffect(() -> {
                    CurrencyManager.getInstance().addToCurrency(Money.getInstance(), new BigNumber(1));
                }).build();
    }

    public static UpgradeManager getInstance() {
        if (instance == null) {
            instance = new UpgradeManager();
        }
        return instance;
    }
}
