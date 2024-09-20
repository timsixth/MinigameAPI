package pl.timsixth.minigameapi.api.configuration;

import lombok.Getter;
import pl.timsixth.minigameapi.api.configuration.configurators.DefaultGameConfigurator;
import pl.timsixth.minigameapi.api.configuration.configurators.DefaultPluginConfigurator;
import pl.timsixth.minigameapi.api.configuration.type.GameConfiguration;
import pl.timsixth.minigameapi.api.configuration.type.PluginConfiguration;

@Getter
public final class ConfiguratorsInitializer {

    private final Configurator<GameConfiguration> gameConfigurator;
    private final Configurator<PluginConfiguration> pluginConfigurator;

    private ConfiguratorsInitializer(Builder builder) {
        this.pluginConfigurator = builder.pluginConfigurator;
        this.gameConfigurator = builder.gameConfigurator;
    }

    /**
     * @return game configuration {@link GameConfiguration}
     */
    public GameConfiguration getGameConfiguration() {
        return gameConfigurator.configure();
    }

    /**
     * @return plugin configuration {@link PluginConfiguration}
     */
    public PluginConfiguration getPluginConfiguration() {
        return pluginConfigurator.configure();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Configurator<GameConfiguration> gameConfigurator;
        private Configurator<PluginConfiguration> pluginConfigurator;

        public Builder() {
            this.gameConfigurator = new DefaultGameConfigurator();
            this.pluginConfigurator = new DefaultPluginConfigurator();
        }

        public Builder setGameConfigurator(Configurator<GameConfiguration> gameConfigurator) {
            this.gameConfigurator = gameConfigurator;
            return this;
        }

        public Builder setPluginConfigurator(Configurator<PluginConfiguration> pluginConfigurator) {
            this.pluginConfigurator = pluginConfigurator;
            return this;
        }

        public ConfiguratorsInitializer build() {
            return new ConfiguratorsInitializer(this);
        }
    }
}
