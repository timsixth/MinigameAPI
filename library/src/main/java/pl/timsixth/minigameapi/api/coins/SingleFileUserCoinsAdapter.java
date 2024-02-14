package pl.timsixth.minigameapi.api.coins;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;
import pl.timsixth.minigameapi.api.file.annotaions.SingleFile;
import pl.timsixth.minigameapi.api.file.impl.SingleFileModelImpl;
import pl.timsixth.minigameapi.api.model.AbstractModelAdapter;
import pl.timsixth.minigameapi.api.model.InitializableModel;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Adapter for UserCoins model which is saving in single YAML file
 *
 * @see SingleFile
 * @see AbstractUserCoinsAdapter
 * @see UserCoins
 * @see AbstractModelAdapter
 */
@SingleFile(fileName = "users_coins.yml", primarySection = "users")
@SerializableAs("SingleFileUserCoinsAdapter")
public class SingleFileUserCoinsAdapter extends AbstractUserCoinsAdapter implements ConfigurationSerializable {
    public SingleFileUserCoinsAdapter(InitializableModel model, UUID uuid, double coins) {
        super(model, uuid, coins);
        model.init(this);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();

        map.put("uuid", getUuid().toString());
        map.put("coins", getCoins());

        return map;
    }

    public static SingleFileUserCoinsAdapter deserialize(Map<String, Object> args) {
        return new SingleFileUserCoinsAdapter(new SingleFileModelImpl(), UUID.fromString((String) args.get("uuid")), (double) args.get("coins"));
    }

}
