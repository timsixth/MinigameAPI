package pl.timsixth.minigameapi.api.file.annotaions;

import pl.timsixth.minigameapi.api.file.FileModel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see FileModel
 * @see ManyFiles
 * Every class which is annotated this annotation save every one instance of FileModel to single file
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SingleFile {
    /**
     * @return file name
     */
    String fileName();

    /**
     * @return primary section in file
     */
    String primarySection();

}
