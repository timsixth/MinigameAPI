package pl.timsixth.minigameapi.api.cosmetics.user;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.cosmetics.Cosmetic;
import pl.timsixth.minigameapi.api.cosmetics.CosmeticsManager;
import pl.timsixth.minigameapi.api.file.FileModel;
import pl.timsixth.minigameapi.api.file.SingleYamlFileDao;
import pl.timsixth.minigameapi.api.file.annotaions.SingleFile;
import pl.timsixth.minigameapi.api.storage.Dao;

import java.util.*;

/**
 * Represents UserCosmetics model which is saving in single YAML file
 *
 * @see SingleFile
 * @see AbstractUserCosmetics
 * @see UserCosmetics
 * @see SingleYamlFileDao
 */
@SingleFile(fileName = "users_cosmetics.yml", primarySection = "users_cosmetics")
@SerializableAs("SingleYamlUserCosmetics")
public class SingleYamlUserCosmetics extends AbstractUserCosmetics implements FileModel, ConfigurationSerializable {

    public SingleYamlUserCosmetics(UUID uuid) {
        super(uuid);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new LinkedHashMap<>();

        data.put("uuid", getUuid().toString());
        data.put("cosmetics", convertCosmetics());

        return data;
    }

    @SuppressWarnings("unchecked")
    public static SingleYamlUserCosmetics deserialize(Map<String, Object> args) {
        SingleYamlUserCosmetics userCosmeticsAdapter = new SingleYamlUserCosmetics(UUID.fromString((String) args.get("uuid")));

        userCosmeticsAdapter.setCosmetics(convertCosmetics((Map<String, Boolean>) args.get("cosmetics")));

        return userCosmeticsAdapter;
    }

    private Map<String, Boolean> convertCosmetics() {
        Map<String, Boolean> cosmetics = new HashMap<>();

        getCosmetics().forEach((cosmetic, status) -> cosmetics.put(cosmetic.getName(), status));

        return cosmetics;
    }

    private static Map<Cosmetic, Boolean> convertCosmetics(Map<String, Boolean> cosmetics) {
        CosmeticsManager cosmeticsManager = MiniGame.getInstance().getCosmeticsManager();
        Map<Cosmetic, Boolean> cosmeticsKeyAsObject = new HashMap<>();

        for (Map.Entry<String, Boolean> cosmeticNameAndStatus : cosmetics.entrySet()) {
            Optional<Cosmetic> cosmeticOptional = cosmeticsManager.getCosmeticByName(cosmeticNameAndStatus.getKey());

            if (!cosmeticOptional.isPresent()) continue;

            cosmeticsKeyAsObject.put(cosmeticOptional.get(), cosmeticNameAndStatus.getValue());
        }

        return cosmeticsKeyAsObject;
    }

    @Override
    public Dao getDao() {
        return new SingleYamlFileDao(this);
    }
}
