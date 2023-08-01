package pl.timsixth.minigameapi.booster.user;

import pl.timsixth.minigameapi.user.User;

import java.util.List;

public interface UserBoosters extends User {

    List<UserBooster> getActivatedBoosters();

    List<UserBooster> getBoosters();

    int getFullMultiplier();

    void setFullMultiplier(int newMultiplier);

}
