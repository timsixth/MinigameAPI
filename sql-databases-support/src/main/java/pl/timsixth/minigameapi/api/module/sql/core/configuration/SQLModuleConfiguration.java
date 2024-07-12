package pl.timsixth.minigameapi.api.module.sql.core.configuration;

import lombok.Builder;
import lombok.Getter;
import pl.timsixth.minigameapi.api.configuration.Configuration;
import pl.timsixth.minigameapi.api.module.sql.core.integration.SQLDatabaseAdapter;
import pl.timsixth.minigameapi.api.module.sql.core.integration.migrator.SQLDatabaseMigrator;

@Getter
@Builder
public class SQLModuleConfiguration implements Configuration {

    private SQLDatabaseAdapter sqlDatabaseAdapter;
    private SQLDatabaseMigrator sqlDatabaseMigrator;
}
