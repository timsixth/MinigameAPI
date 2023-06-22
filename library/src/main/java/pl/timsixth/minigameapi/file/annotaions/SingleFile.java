package pl.timsixth.minigameapi.file.annotaions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see pl.timsixth.minigameapi.file.FileModel
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
