package pl.timsixth.minigameapi.api.database.annoations;

import pl.timsixth.minigameapi.api.database.DbModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * In every {@link DbModel} must be Id annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Id {
}
