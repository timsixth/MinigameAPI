package pl.timsixth.minigameapi.api.util.options;

import java.util.Map;

public interface Options {

    /**
     * Sets value
     *
     * @param name  key
     * @param value value
     */
    void setValue(String name, Object value);

    /**
     * Gets object by key
     *
     * @param name key
     * @return value or null when can not get value
     */
    Object getValue(String name);

    /**
     * Gets all options
     *
     * @return map of options
     */
    Map<String, Object> getOptions();

    /**
     * Gets value by key, or gets default value
     *
     * @param name  key
     * @param value default value
     * @return value or default value when null
     */
    default Object getValueOrDefault(String name, Object value) {
        return getValue(name) == null ? value : getValue(name);
    }
}
