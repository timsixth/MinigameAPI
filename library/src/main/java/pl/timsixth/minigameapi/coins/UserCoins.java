package pl.timsixth.minigameapi.coins;

import pl.timsixth.minigameapi.user.User;

public interface UserCoins extends User {

    double getCoins();

    void setCoins(double coins);

    void addCoins(double coins);

    void removeCoins(double coins);

    boolean hasCoins(double coins);
}
