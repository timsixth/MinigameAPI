package pl.timsixth.minigameapi.file.annotaions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @see pl.timsixth.minigameapi.file.FileModel
 * @see SingleFile
 * @see IdSection
 * Every class which is annotated this annotation save every one instance of FileModel to many files.
 * The field which annotated IdSection is a name of file.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ManyFiles {
    String primarySection() default "";
}
