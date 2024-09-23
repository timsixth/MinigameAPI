package pl.timsixth.minigameapi.api.module.sql.core.integration.migrator;

/**
 * Adapter for migration system
 */
public interface SQLDatabaseMigrator {

    /**
     * Migrates one migration
     *
     * @param migration migration to migrate
     */
    void migrate(Migration migration);

    /**
     * Adds migration to migrations to migrate
     *
     * @param migration migration to add
     */
    void addMigration(Migration migration);
}
