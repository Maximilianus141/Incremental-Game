package dev.cowycorn.incrgame.backend;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
public class Upgrade {
    @Getter
    private String name;
    @Getter
    private String description;
    @Getter
    private UpgradeEffect  upgradeEffect;
}
