package pl.timsixth.minigameapi.api.module.sql.core.integration.migrator;

public interface SQLDatabaseMigrator {

    void migrate(Migration migration);

    void addMigration(Migration migration);
}
