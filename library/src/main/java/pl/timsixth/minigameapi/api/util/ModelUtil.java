package pl.timsixth.minigameapi.api.util;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;

@UtilityClass
public class ModelUtil {

    public static Field[] findFields(Object object) {
        Field[] fields = {};

        Class<?> clazz = object.getClass();

        while (fields.length == 0 || hasStaticFields(fields)) {
            fields = clazz.getDeclaredFields();

            clazz = clazz.getSuperclass();
        }

        return fields;
    }

    private static boolean hasStaticFields(Field[] fields) {
        return Arrays.stream(fields)
                .anyMatch(field -> Modifier.isStatic(field.getModifiers()));
    }
}
