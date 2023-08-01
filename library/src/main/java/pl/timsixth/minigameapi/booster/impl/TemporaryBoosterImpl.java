package pl.timsixth.minigameapi.booster.impl;

import lombok.Getter;
import lombok.NonNull;
import pl.timsixth.minigameapi.booster.BoosterType;
import pl.timsixth.minigameapi.booster.TemporaryBoosterFileModel;
import pl.timsixth.minigameapi.file.annotaions.SingleFile;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Getter
@SingleFile(fileName = "boosters.yml", primarySection = "boosters")
public class TemporaryBoosterImpl extends BoosterImpl implements TemporaryBoosterFileModel {

    private final long time;
    private final TimeUnit timeUnit;

    public TemporaryBoosterImpl(String name, BoosterType type, int multiplier, String displayName, long time, TimeUnit timeUnit) {
        super(name, type, multiplier, displayName);
        this.time = time;
        this.timeUnit = timeUnit;
        init();
    }

    @Override
    @NonNull
    public Map<String, Object> serialize() {
        Map<String, Object> data = new LinkedHashMap<>();

        data.put("name", getName());
        data.put("boosterType", getType().name());
        data.put("multiplier", getMultiplier());
        data.put("displayName", getDisplayName());
        data.put("time", time);
        data.put("timeUnit", timeUnit);

        return data;
    }

    public static BoosterImpl deserialize(Map<String, Object> args) {
        return new TemporaryBoosterImpl(String.valueOf(args.get("name")),
                BoosterType.valueOf(String.valueOf(args.get("boosterType"))),
                (int) args.get("multiplier"), String.valueOf(args.get("displayName")),
                (long) args.get("time"), TimeUnit.valueOf(String.valueOf(args.get("timeUnit")))
        );
    }
}
