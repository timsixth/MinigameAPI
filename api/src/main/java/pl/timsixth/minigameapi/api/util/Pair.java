package pl.timsixth.minigameapi.api.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public final class Pair<T, S> {

    private final T first;
    private final S second;
}
