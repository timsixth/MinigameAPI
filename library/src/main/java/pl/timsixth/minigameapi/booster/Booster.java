package pl.timsixth.minigameapi.booster;

public interface Booster {

    String getName();

    BoosterType getType();

    int getMultiplier();

    String getDisplayName();

    void setDisplayName(String displayName);
}
