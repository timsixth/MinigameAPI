package pl.timsixth.minigameapi.api.file;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import pl.timsixth.minigameapi.api.file.annotaions.IdSection;
import pl.timsixth.minigameapi.api.file.annotaions.ManyFiles;
import pl.timsixth.minigameapi.api.file.annotaions.SingleFile;
import pl.timsixth.minigameapi.api.util.ModelUtil;

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
    private String parentDirectory;

    /**
     * @param type object which is preparing to serialization
     * @throws NoSuchFieldException   when the field is null
     * @throws IllegalAccessException when method has illegal access to field
     */
    void prepareModel(Object type) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = type.getClass();

        if (clazz.isAnnotationPresent(SingleFile.class)) {
            SingleFile singleFile = clazz.getAnnotation(SingleFile.class);

            this.name = singleFile.fileName();
            this.primarySection = singleFile.primarySection();
        }

        if (clazz.isAnnotationPresent(ManyFiles.class)) {
            ManyFiles manyFiles = clazz.getAnnotation(ManyFiles.class);

            this.primarySection = manyFiles.primarySection();
            this.parentDirectory = manyFiles.parentDirectory();
        }

        setIdSection(type);

        if (idSection == null) throw new IllegalStateException("Every file model must have IdSection annotation");

        if (clazz.isAnnotationPresent(ManyFiles.class)) this.name = idSection.toString() + ".yml";
    }

    /**
     * Sets idSection value
     *
     * @param type type class which implements {@link ConfigurationSerializable}
     * @throws IllegalAccessException when can not access to fields
     */
    private void setIdSection(Object type) throws IllegalAccessException {
        Field[] fields = ModelUtil.findFields(type);

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

}
