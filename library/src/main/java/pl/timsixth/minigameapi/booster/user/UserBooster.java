package pl.timsixth.minigameapi.booster.user;

import pl.timsixth.minigameapi.booster.Booster;

import java.time.LocalDateTime;
import java.util.UUID;

public interface UserBooster  {

    LocalDateTime getStartedDate();

    LocalDateTime getEndDate();

    Booster getBooster();

    boolean isActive();

    UUID getUuid();
}
