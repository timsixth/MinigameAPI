package pl.timsixth.minigameapi.api.storage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.timsixth.minigameapi.api.model.Model;

@RequiredArgsConstructor
@Getter
public abstract class AbstractDao implements Dao{

    private final Model model;
}
