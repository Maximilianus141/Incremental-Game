package dev.cowycorn.incrgame.backend;

import dev.cowycorn.BigNumber;
import dev.cowycorn.incrgame.backend.currency.CurrencyEffect;
import dev.cowycorn.incrgame.backend.currency.Money;
import dev.cowycorn.incrgame.backend.currency.Prestige;
import dev.cowycorn.incrgame.backend.currency.Rebirth;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UpgradeManager {
    private static UpgradeManager instance;

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Getter
    private List<Upgrade> upgrades = new ArrayList<>();


    private UpgradeManager() {
        UpgradeInitializer.init();
        upgrades.addAll(UpgradeInitializer.initialUpgrades);
    }

    public void buyUpgrade(Upgrade upgrade){

        if(upgrade.getCurrency().getAmount().greaterThanOrEqual(upgrade.getCost()) && !upgrade.getBought()){
            log.info("Buying upgrade {}", upgrade.getName());
            upgrade.getCurrency().setAmount(upgrade.getCurrency().getAmount().subtract(upgrade.getCost()));
            upgrade.setBought(true);
            upgrade.getUpgradeEffect().run();
        }
    }

    public static UpgradeManager getInstance() {
        if (instance == null) {
            instance = new UpgradeManager();
        }
        return instance;
    }
}
