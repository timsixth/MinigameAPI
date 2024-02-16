package pl.timsixth.minigameapi.api.stats.model;

import lombok.Getter;
import pl.timsixth.minigameapi.api.database.annoations.Id;
import pl.timsixth.minigameapi.api.file.annotaions.IdSection;
import pl.timsixth.minigameapi.api.model.AbstractModelAdapter;
import pl.timsixth.minigameapi.api.model.InitializableModel;

import java.util.UUID;

@Getter
public abstract class AbstractUserStatsAdapter extends AbstractModelAdapter implements UserStats {

    @Id
    @IdSection
    private final UUID uuid;
    private final String name;
    private final String arenaName;
    private int wins;
    private int defeats;

    public AbstractUserStatsAdapter(InitializableModel model, UUID uuid, String name, String arenaName, int wins, int defeats) {
        super(model);
        this.uuid = uuid;
        this.name = name;
        this.arenaName = arenaName;
        this.wins = wins;
        this.defeats = defeats;
    }

    @Override
    public void addWin() {
        wins++;
        update();
    }

    @Override
    public void addDefeat() {
        defeats++;
        update();
    }

    @Override
    public int getGamesPlayed() {
        return wins + defeats;
    }
}
