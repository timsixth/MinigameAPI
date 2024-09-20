package pl.timsixth.minigameapi.api.model.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Every field which is annotated this annotation is unique key of FileModel.
 * In models which are annotated ManyFiles is a name of file.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Id {
}
