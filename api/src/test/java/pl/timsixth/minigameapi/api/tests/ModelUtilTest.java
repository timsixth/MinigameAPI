package pl.timsixth.minigameapi.api.tests;

import org.junit.Test;
import pl.timsixth.minigameapi.api.model.annoations.IgnoreFields;
import pl.timsixth.minigameapi.api.util.ModelUtil;

import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;

public class ModelUtilTest {

    @Test
    public void shouldNotGetFieldsBecauseClassHasIgnoredFields() {
        Field[] fields = ModelUtil.findFields(new TestClass1());

        assertEquals(0, fields.length);
    }

    @Test
    public void shouldGetFieldsFromSuperClassButSubClassHasIgnoredFields() {
        Field[] fields = ModelUtil.findFields(new TestClass4());

        assertEquals(1, fields.length);
    }

    @Test
    public void shouldNotGetFieldsBecauseClassHasStaticFields() {
        Field[] fields = ModelUtil.findFields(new TestClass2());

        assertEquals(0, fields.length);
    }

    @Test
    public void shouldGetFieldsFromSuperClassButSubClassStaticFields() {
        Field[] fields = ModelUtil.findFields(new TestClass5());

        assertEquals(1, fields.length);
    }

    @Test
    public void shouldGetFields() {
        Field[] fields = ModelUtil.findFields(new TestClass3());

        assertEquals(1, fields.length);
    }

    @IgnoreFields
    private static class TestClass1 {
        String name = "";
    }

    private static class TestClass2 {
        static String name = "";
    }

    private static class TestClass3 {
        String name = "";
    }

    @IgnoreFields
    private static class TestClass4 extends TestClass3 {
        String name = "";
    }

    private static class TestClass5 extends TestClass3 {
        static String name = "";
    }

}
