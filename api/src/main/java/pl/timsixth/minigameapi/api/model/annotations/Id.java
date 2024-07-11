package pl.timsixth.minigameapi.api.model.annotations;

import pl.timsixth.minigameapi.api.database.DbModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Every field which is annotated this annotation is unique key of FileModel.
 * In models which are annotated ManyFiles is a name of file.
 * In every {@link DbModel} must be Id annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Id {
}
