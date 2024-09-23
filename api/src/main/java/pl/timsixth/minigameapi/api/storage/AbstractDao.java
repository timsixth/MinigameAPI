package pl.timsixth.minigameapi.api.storage;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.timsixth.minigameapi.api.model.Model;

/**
 * Template method of {@link Dao}
 */
@RequiredArgsConstructor
@Getter(AccessLevel.PROTECTED)
public abstract class AbstractDao implements Dao {

    private final Model model;
}
