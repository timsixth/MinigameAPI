package pl.timsixth.minigameapi.booster.impl;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import pl.timsixth.minigameapi.booster.BoosterFileModel;
import pl.timsixth.minigameapi.booster.BoosterType;
import pl.timsixth.minigameapi.file.SingleFileModel;
import pl.timsixth.minigameapi.file.annotaions.IdSection;
import pl.timsixth.minigameapi.file.annotaions.SingleFile;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
@SingleFile(fileName = "boosters.yml", primarySection = "boosters")
public class BoosterImpl extends SingleFileModel implements BoosterFileModel {

    @IdSection
    private final String name;
    private final BoosterType type;
    private final int multiplier;
    private String displayName;

    public BoosterImpl(String name, BoosterType type, int multiplier, String displayName) {
        this.name = name;
        this.type = type;
        this.multiplier = multiplier;
        this.displayName = displayName;
        init();
    }

    @Override
    @NonNull
    public Map<String, Object> serialize() {
        Map<String, Object> data = new LinkedHashMap<>();

        data.put("name", name);
        data.put("boosterType", type.name());
        data.put("multiplier", multiplier);
        data.put("displayName", displayName);

        return data;
    }

    public static BoosterImpl deserialize(Map<String, Object> args) {
        return new BoosterImpl(String.valueOf(args.get("name")),
                BoosterType.valueOf(String.valueOf(args.get("boosterType"))),
                (int) args.get("multiplier"), String.valueOf(args.get("displayName"))
        );
    }
}
