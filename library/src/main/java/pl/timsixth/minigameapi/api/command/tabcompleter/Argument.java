package pl.timsixth.minigameapi.api.command.tabcompleter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents single argument when onTabComplete method is executing
 */
@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public final class Argument {

    private final int argsLength;
    private List<String> values = new ArrayList<>();
    private List<String> conditions = new ArrayList<>();
    private int index = -1;

    /**
     * @param argsLength length of arguments
     * @param values     values to send on tab complete
     * @param index      index of condition
     * @param condition  one rule, after this rule values will be sent
     */
    public Argument(int argsLength, List<String> values, int index, String condition) {
        this.argsLength = argsLength;
        this.values = values;
        this.conditions.add(condition);
        this.index = index;
    }

    /**
     * @param argsLength length of arguments
     * @param value      values to send on tab complete
     * @param index      index of condition
     * @param condition  one rule, after this rule values will be sent
     */
    public Argument(int argsLength, String value, int index, String condition) {
        this.argsLength = argsLength;
        this.values.add(value);
        this.conditions.add(condition);
        this.index = index;
    }

    /**
     * @param argsLength length of arguments
     * @param values     values to send on tab complete
     * @param index      index of condition
     * @param conditions list of rules, after these rules values will be sent
     */
    public Argument(int argsLength, List<String> values, int index, List<String> conditions) {
        this.argsLength = argsLength;
        this.index = index;
        this.values.addAll(values);
        this.conditions.addAll(conditions);
    }
}
