package pl.timsixth.minigameapi.booster;

import java.util.concurrent.TimeUnit;

public interface TemporaryBooster extends Booster {

    long getTime();

    TimeUnit getTimeUnit();
}
