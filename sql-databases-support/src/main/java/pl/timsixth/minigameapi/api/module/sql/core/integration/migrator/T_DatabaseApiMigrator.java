package pl.timsixth.minigameapi.api.module.sql.core.integration.migrator;

import pl.timsixth.databasesapi.DatabasesApiPlugin;
import pl.timsixth.databasesapi.database.migration.Migrations;

public class T_DatabaseApiMigrator implements SQLDatabaseMigrator {

    private final Migrations migrations;

    public T_DatabaseApiMigrator() {
        this.migrations = DatabasesApiPlugin.getApi().getMigrations();
    }

    @Override
    public void migrate(Migration migration) {
        if (!(migration instanceof T_DatabaseApiMigration)) return;

        T_DatabaseApiMigration databaseApiMigration = (T_DatabaseApiMigration) migration;

        migrations.migrate(databaseApiMigration);
    }

    @Override
    public void addMigration(Migration migration) {
        if (!(migration instanceof T_DatabaseApiMigration)) return;

        T_DatabaseApiMigration databaseApiMigration = (T_DatabaseApiMigration) migration;

        migrations.addMigration(databaseApiMigration);
    }
}
