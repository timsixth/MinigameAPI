package pl.timsixth.minigameapi.api.loader.annotations;

import pl.timsixth.minigameapi.api.loader.Loader;
import pl.timsixth.minigameapi.api.model.Model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModelLoader {

    Class<? extends Loader<? extends Model>> clazz();
}
