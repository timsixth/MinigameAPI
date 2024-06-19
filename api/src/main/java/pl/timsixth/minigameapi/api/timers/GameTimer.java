package pl.timsixth.minigameapi.api.timers;

public interface GameTimer {

    void onCancel();

    void onEnd();

    void onStart();

    void onCounting(int second);
}
