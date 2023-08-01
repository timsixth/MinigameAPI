package pl.timsixth.minigameapi.booster.user.impl;

import lombok.Data;
import pl.timsixth.minigameapi.booster.Booster;
import pl.timsixth.minigameapi.booster.user.UserBooster;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class UserBoosterImpl implements UserBooster {

    private final UUID uuid;
    private final Booster booster;
    private final LocalDateTime startedDate;
    private final LocalDateTime endDate;
    
    @Override
    public boolean isActive() {
        return LocalDateTime.now().equals(endDate);
    }
}
