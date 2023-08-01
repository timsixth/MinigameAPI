package pl.timsixth.minigameapi.booster.user.impl;

import lombok.Getter;
import lombok.Setter;
import pl.timsixth.minigameapi.booster.user.UserBooster;
import pl.timsixth.minigameapi.booster.user.UserBoosters;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class UserBoostersImpl implements UserBoosters {

    private final UUID uuid;
    private final List<UserBooster> boosters;
    private int fullMultiplier;

    public UserBoostersImpl(UUID uuid, List<UserBooster> boosters, int fullMultiplier) {
        this.uuid = uuid;
        this.boosters = boosters;
        this.fullMultiplier = fullMultiplier;
    }

    public UserBoostersImpl(UUID uuid, int fullMultiplier) {
        this.uuid = uuid;
        this.boosters = new ArrayList<>();
        this.fullMultiplier = fullMultiplier;
    }

    @Override
    public List<UserBooster> getActivatedBoosters() {
        return boosters.stream()
                .filter(UserBooster::isActive)
                .collect(Collectors.toList());
    }
}
