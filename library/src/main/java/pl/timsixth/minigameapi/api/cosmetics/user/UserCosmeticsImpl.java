package pl.timsixth.minigameapi.api.cosmetics.user;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import pl.timsixth.databasesapi.database.query.QueryBuilder;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.database.AbstractDbModel;
import pl.timsixth.minigameapi.api.database.annoations.Id;
import pl.timsixth.minigameapi.api.cosmetics.Cosmetic;

import java.util.*;
/**
 * Implementation of {@link UserCosmetics}
 *
 * @see AbstractDbModel
 * @see UserCosmeticsDbModel
 */
@Getter
@Setter
@ToString
public class UserCosmeticsImpl extends AbstractDbModel implements UserCosmeticsDbModel {

    @Id
    private final UUID uuid;
    private Map<Cosmetic, Boolean> cosmetics;

    public UserCosmeticsImpl(UUID uuid) {
        this.uuid = uuid;
        this.cosmetics = new HashMap<>();
    }

    @Override
    public boolean hasCosmetic(Cosmetic cosmetic) {
        return cosmetics.containsKey(cosmetic);
    }

    @Override
    public void removeCosmetic(Cosmetic cosmetic) {
        cosmetics.remove(cosmetic);
        delete();
    }

    @Override
    public void addCosmetic(Cosmetic cosmetic) {
        cosmetics.put(cosmetic, false);
        delete();
        save();
    }

    @Override
    public boolean isCosmeticEnable(Cosmetic cosmetic) {
        return cosmetics.getOrDefault(cosmetic, false);
    }

    @Override
    public void enableCosmetic(Cosmetic cosmetic) {
        if (isCosmeticEnable(cosmetic)) return;

        cosmetics.replace(cosmetic, true);
        update();
    }

    @Override
    public void disableCosmetic(Cosmetic cosmetic) {
        if (!isCosmeticEnable(cosmetic)) return;

        cosmetics.replace(cosmetic, false);
        update();
    }

    @Override
    public String getTableName() {
        return MiniGame.getInstance().getDefaultPluginConfiguration().getTablesPrefix() + "users_cosmetics";
    }


    @Override
    public Object update() {

        cosmetics.forEach((cosmetic, status) -> {
            QueryBuilder queryBuilder = new QueryBuilder();

            Map<String, Object> data = new HashMap<>();
            data.put("enabled", status);


            String query = queryBuilder.update(getTableName(), data)
                    .where("uuid = '" + uuid.toString() + "' AND cosmetic = '" + cosmetic.getName() + "'")
                    .build();

            executeUpdate(query);
        });

        return this;
    }

    @Override
    public Object save() {

        cosmetics.forEach((cosmetic, status) -> {
            QueryBuilder queryBuilder = new QueryBuilder();

            String query = queryBuilder.insert(getTableName(), null, uuid, cosmetic.getName(), status).build();

            executeUpdate(query);
        });
        return this;
    }

    @Override
    public boolean delete() {
        cosmetics.forEach((cosmetic, status) -> {
            QueryBuilder queryBuilder = new QueryBuilder();

            String query = queryBuilder.deleteAll(getTableName())
                    .where("uuid = '" + uuid.toString() + "' AND cosmetic = '" + cosmetic.getName() + "'")
                    .build();

            executeUpdate(query);

        });

        return true;
    }

    @Override
    public void resetAllCosmetics() {
        cosmetics.forEach((cosmetic, status) -> cosmetics.replace(cosmetic, false));

        update();
    }

    @Override
    public void resetAllCosmeticsCategory(String category) {
        List<Cosmetic> allCosmeticsCategory = getAllCosmeticsCategory(category);

        allCosmeticsCategory.forEach(cosmetic -> cosmetics.replace(cosmetic, false));

        update();
    }

    @Override
    public List<Cosmetic> getAllCosmeticsCategory(String category) {
        List<Cosmetic> cosmetics = new ArrayList<>();

        this.cosmetics.forEach((cosmetic, status) -> {
            if (cosmetic.getName().toUpperCase().contains(category.toUpperCase())) {
                cosmetics.add(cosmetic);
            }
        });

        return cosmetics;
    }

    @Override
    public List<Cosmetic> getActiveCosmeticsByCategory(String category) {
        List<Cosmetic> cosmetics = new ArrayList<>();

        this.cosmetics.forEach((cosmetic, status) -> {
            if (cosmetic.getName().toUpperCase().contains(category.toUpperCase())) {
                if (this.cosmetics.get(cosmetic)) cosmetics.add(cosmetic);
            }
        });

        return cosmetics;
    }

}
