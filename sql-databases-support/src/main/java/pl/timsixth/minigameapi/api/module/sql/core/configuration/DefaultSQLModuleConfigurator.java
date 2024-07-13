package pl.timsixth.minigameapi.api.module.sql.core.configuration;

import pl.timsixth.minigameapi.api.configuration.Configurator;
import pl.timsixth.minigameapi.api.module.sql.core.integration.T_DataBaseApiIntegration;
import pl.timsixth.minigameapi.api.module.sql.core.integration.migrator.T_DatabaseApiMigrator;

/**
 * Default configuration for SQLModule.
 * Default settings:
 * {@link pl.timsixth.minigameapi.api.module.sql.core.integration.SQLDatabaseAdapter} is set to {@link T_DataBaseApiIntegration}
 * {@link pl.timsixth.minigameapi.api.module.sql.core.integration.migrator.SQLDatabaseMigrator} is set to {@link T_DatabaseApiMigrator}
 *
 * @see SQLModuleConfiguration
 */
public class DefaultSQLModuleConfigurator implements Configurator<SQLModuleConfiguration> {
    @Override
    public SQLModuleConfiguration configure() {
        return SQLModuleConfiguration.builder()
                .sqlDatabaseAdapter(new T_DataBaseApiIntegration())
                .sqlDatabaseMigrator(new T_DatabaseApiMigrator())
                .build();
    }
}
