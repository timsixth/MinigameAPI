package pl.timsixth.minigameapi.api.module.sql;

import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.migration.Migrations;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.coins.migrations.CreateUserCoinsTableMigration;
import pl.timsixth.minigameapi.api.cosmetics.user.migrations.CreateUserCosmeticsTableMigration;
import pl.timsixth.minigameapi.api.module.Module;
import pl.timsixth.minigameapi.api.stats.migrations.CreateUserStatsTable;

public class SQLModule implements Module {
    @Override
    public String getName() {
        return "minigameapi-sql";
    }

    @Override
    public void onEnable() {
        initMigrations();
    }

    private void initMigrations() {
//        if (!MiniGame.getInstance().getPluginConfiguration().isUseDataBase()) return;
//
//        Migrations migrations = DatabasesApiPlugin.getApi().getMigrations();
//        CreateUserCoinsTableMigration createUserCoinsTableMigration = new CreateUserCoinsTableMigration();
//        CreateUserCosmeticsTableMigration createUserCosmeticsTableMigration = new CreateUserCosmeticsTableMigration();
//
//        if (MiniGame.getInstance().getPluginConfiguration().isUseDefaultStatsSystem()) {
//            CreateUserStatsTable createUserStatsTable = new CreateUserStatsTable();
//            migrations.addMigration(createUserStatsTable);
//
//            migrations.migrate(createUserStatsTable);
//        }
//
//        migrations.addMigration(createUserCoinsTableMigration);
//        migrations.addMigration(createUserCosmeticsTableMigration);
//
//        migrations.migrate(createUserCoinsTableMigration);
//        migrations.migrate(createUserCosmeticsTableMigration);
    }
}
