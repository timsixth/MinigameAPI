package pl.timsixth.minigameapi.api.module.sql;

import lombok.Getter;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.module.Module;
import pl.timsixth.minigameapi.api.module.sql.coins.migrations.CreateUserCoinsTableMigration;
import pl.timsixth.minigameapi.api.module.sql.core.configuration.DefaultSQLModuleConfigurator;
import pl.timsixth.minigameapi.api.module.sql.core.configuration.SQLModuleConfiguration;
import pl.timsixth.minigameapi.api.module.sql.core.integration.migrator.SQLDatabaseMigrator;
import pl.timsixth.minigameapi.api.module.sql.cosmetics.migrations.CreateUserCosmeticsTableMigration;
import pl.timsixth.minigameapi.api.module.sql.stats.migrations.CreateUserStatsTable;

/**
 * Represents module which supports SQL databases.
 * When you are using T-DataBaseAPI to integrate with SQL databases, the module supports MySQL and SQLite.
 */
@Getter
public class SQLModule implements Module {

    private final SQLModuleConfiguration sqlModuleConfiguration;

    @Getter
    private static SQLModule instance;

    @Override
    public String getName() {
        return "minigameapi-sql";
    }

    public SQLModule() {
        this(new DefaultSQLModuleConfigurator().configure());
    }

    public SQLModule(SQLModuleConfiguration sqlModuleConfiguration) {
        instance = this;

        this.sqlModuleConfiguration = sqlModuleConfiguration;
    }

    @Override
    public void onEnable() {
        initMigrations();
    }

    private void initMigrations() {
        SQLDatabaseMigrator sqlDatabaseMigrator = sqlModuleConfiguration.getSqlDatabaseMigrator();

        CreateUserCoinsTableMigration createUserCoinsTableMigration = new CreateUserCoinsTableMigration();
        CreateUserCosmeticsTableMigration createUserCosmeticsTableMigration = new CreateUserCosmeticsTableMigration();

        if (MiniGame.getInstance().getPluginConfiguration().isUseDefaultStatsSystem()) {
            CreateUserStatsTable createUserStatsTable = new CreateUserStatsTable();
            sqlDatabaseMigrator.addMigration(createUserStatsTable);

            sqlDatabaseMigrator.migrate(createUserStatsTable);
        }

        sqlDatabaseMigrator.addMigration(createUserCoinsTableMigration);
        sqlDatabaseMigrator.addMigration(createUserCosmeticsTableMigration);

        sqlDatabaseMigrator.migrate(createUserCoinsTableMigration);
        sqlDatabaseMigrator.migrate(createUserCosmeticsTableMigration);
    }
}
