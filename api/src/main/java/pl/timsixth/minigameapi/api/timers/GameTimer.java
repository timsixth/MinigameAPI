package pl.timsixth.minigameapi.api.timers;

public interface GameTimer {

    /**
     * Calls when timer is canceled
     */
    void onCancel();

    /**
     * Calls when timer was stopped
     */
    void onEnd();

    /**
     * Calls when timer was started
     */
    void onStart();

    /**
     * Calls when timer is counting
     *
     * @param second current second
     */
    void onCounting(int second);
}
