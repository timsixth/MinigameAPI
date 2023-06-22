package pl.timsixth.minigameapi.file.annotaions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see pl.timsixth.minigameapi.file.FileModel
 * @see ManyFiles
 * @see SingleFile
 * Every field which is annotated this annotaion is uniqe key of FileModel.
 * In models which are annotated ManyFiles is a name of file.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IdSection {
}
