package pl.timsixth.exampleminigame.gamestate;

import lombok.RequiredArgsConstructor;
import pl.timsixth.exampleminigame.ExampleMiniGamePlugin;
import pl.timsixth.exampleminigame.config.Messages;
import pl.timsixth.exampleminigame.config.Settings;
import pl.timsixth.exampleminigame.task.SimpleStartGameTimer;
import pl.timsixth.minigameapi.api.game.Game;
import pl.timsixth.minigameapi.api.game.state.GameState;

@RequiredArgsConstructor
public class StartingGameState implements GameState {

    private final Settings settings;
    private final Messages messages;
    private final ExampleMiniGamePlugin plugin;
    private final Game game;

    @Override
    public void run() {
        //the old version
//        new StartGameCountDown(settings.getStartTimer(), game, messages, settings)
//                .runTaskTimer(plugin, 20L, 20L);

        //the countdown on the level bar
//        new XpStartGameTimer(game, settings, messages)
//                .runTaskTimer(plugin, 20L, 20L);

        //the countdown on the chat
        new SimpleStartGameTimer(game, settings, messages)
                .runTaskTimer(plugin, 20L, 20L);
    }
}
