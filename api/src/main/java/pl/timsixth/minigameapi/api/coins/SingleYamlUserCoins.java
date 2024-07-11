package pl.timsixth.minigameapi.api.coins;

import lombok.NonNull;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import pl.timsixth.minigameapi.api.file.FileModel;
import pl.timsixth.minigameapi.api.file.SingleYamlFileDao;
import pl.timsixth.minigameapi.api.file.annotaions.SingleFile;
import pl.timsixth.minigameapi.api.storage.Dao;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@SingleFile(fileName = "users_coins.yml", primarySection = "users")
@SerializableAs("SingleYamlUserCoins")
public class SingleYamlUserCoins extends AbstractUserCoins implements FileModel, ConfigurationSerializable {

    public SingleYamlUserCoins(UUID uuid, double coins) {
        super(uuid, coins);
    }

    @Override
    public Dao getDao() {
        return new SingleYamlFileDao(this);
    }

    @Override
    @NonNull
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("uuid", getUuid().toString());
        map.put("coins", getCoins());

        return map;
    }

    public static SingleYamlUserCoins deserialize(Map<String, Object> args) {
        return new SingleYamlUserCoins(UUID.fromString((String) args.get("uuid")), (double) args.get("coins"));
    }
}
