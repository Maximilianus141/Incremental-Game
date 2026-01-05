package dev.cowycorn.incrgame.backend;

import dev.cowycorn.BigNumber;
import dev.cowycorn.incrgame.backend.currency.CurrencyEffect;
import dev.cowycorn.incrgame.backend.currency.Money;
import dev.cowycorn.incrgame.backend.currency.Prestige;
import dev.cowycorn.incrgame.backend.currency.Rebirth;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UpgradeInitializer {
    public static List<Upgrade> initialUpgrades = new ArrayList<>();

    public static void init() {
        initialUpgrades.add(Upgrade.builder()
                .name("first upgrade")
                .description("adds one singular money")
                .upgradeEffect(() -> {
                    CurrencyManager.getInstance().addToCurrency(Money.getInstance(), new BigNumber(1));
                }).
                cost(new BigNumber(3)).
                currency(Money.getInstance()).build());

        initialUpgrades.add( Upgrade.builder()
                .name("prestige money boost")
                .description("Prestige will boost money by 5%")
                .upgradeEffect(() -> {
                    Prestige.getInstance().getEffects().put(Money.class, ((ownAmount, otherAmount) ->
                            ownAmount.multiply(new BigNumber(0.05)).add(new BigNumber(1)).multiply(otherAmount)));
                })
                .cost(new BigNumber(250))
                .currency(Rebirth.getInstance()).build());

    }
}
