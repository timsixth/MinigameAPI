package pl.timsixth.minigameapi.api.util.options;

import lombok.Getter;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.SerializableAs;

import java.util.HashMap;
import java.util.Map;

@SerializableAs("options")
@Getter
public class OptionsImpl implements ConfigurationSerializable, Options {

    private final Map<String, Object> options;

    public OptionsImpl(Map<String, Object> options) {
        this.options = options;
    }

    public OptionsImpl() {
        this.options = new HashMap<>();
    }

    @Override
    public void setValue(String name, Object value) {
        options.put(name, value);
    }

    @Override
    public Object getValue(String name) {
        return options.get(name);
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();

        data.put("options", options);

        return data;
    }

    @SuppressWarnings("unchecked")
    public static OptionsImpl deserialize(Map<String, Object> data) {
        return new OptionsImpl((Map<String, Object>) data.get("options"));
    }
}
