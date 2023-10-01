package pl.timsixth.minigameapi.api.file;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import pl.timsixth.minigameapi.api.MiniGame;
import pl.timsixth.minigameapi.api.file.annotaions.IdSection;
import pl.timsixth.minigameapi.api.file.annotaions.ManyFiles;
import pl.timsixth.minigameapi.api.file.annotaions.SingleFile;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

@Getter
public final class ConfigurationFile {

    private String name;
    private String primarySection;
    private Object idSection;
    @Setter
    private File file;
    @Setter
    private YamlConfiguration yamlConfiguration;

    /**
     * @param type object which is preparing to serialization
     * @param <T>  every FileModel
     * @throws NoSuchFieldException   when the field is null
     * @throws IllegalAccessException when method has illegal access to field
     */
    <T extends ConfigurationSerializable> void prepareModel(T type) throws NoSuchFieldException, IllegalAccessException {
        Class<? extends ConfigurationSerializable> typeClass = type.getClass();

        if (typeClass.isAnnotationPresent(SingleFile.class)) {
            SingleFile singleFile = typeClass.getAnnotation(SingleFile.class);

            this.name = singleFile.fileName();
            this.primarySection = singleFile.primarySection();
        }

        if (typeClass.isAnnotationPresent(ManyFiles.class)) {
            ManyFiles manyFiles = typeClass.getAnnotation(ManyFiles.class);

            this.primarySection = manyFiles.primarySection();
        }

        setIdSection(type, typeClass);

        if (idSection == null) setIdSectionFromSuperclass(type);

        if (typeClass.isAnnotationPresent(ManyFiles.class)) this.name = idSection.toString() + ".yml";
    }

    /**
     * Sets idSection value
     *
     * @param type  type class which implements {@link ConfigurationSerializable}
     * @param clazz param to gets fields by reflection
     * @throws IllegalAccessException when can not access to fields
     */
    private <T extends ConfigurationSerializable> void setIdSection(T type, Class<?> clazz) throws IllegalAccessException {
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.isAnnotationPresent(IdSection.class)) {
                if (idSection != null)
                    throw new IllegalStateException("Only one field can be annotated as IdSection");
                field.setAccessible(true);
                idSection = field.get(type);
            }
        }
    }

    /**
     * The method saves file
     */
    public void saveFile() {
        try {
            yamlConfiguration.save(file);
        } catch (IOException e) {
            Bukkit.getLogger().severe(e.getMessage());
        }
    }

    /**
     * If subclass doesn't have IdSection annotation, this method sets idSection from superclass
     *
     * @param type class which implements {@link ConfigurationSerializable}
     * @throws IllegalAccessException when can not access to fields
     */
    private <T extends ConfigurationSerializable> void setIdSectionFromSuperclass(T type) throws IllegalAccessException {
        Class<?> superclass = type.getClass().getSuperclass();

        setIdSection(type, superclass);

        if (idSection == null) throw new IllegalStateException("Every file model must have IdSection annotation");
    }
}
