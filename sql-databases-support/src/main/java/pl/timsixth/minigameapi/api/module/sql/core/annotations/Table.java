package pl.timsixth.minigameapi.api.module.sql.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Table {
    /**
     * Sets table name of class which implemented {@link pl.timsixth.minigameapi.api.module.sql.core.DbModel}
     *
     * @return name of table
     */
    String name();
}
