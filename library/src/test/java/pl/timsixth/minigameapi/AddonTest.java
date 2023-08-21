package pl.timsixth.minigameapi;

import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class AddonTest {

    @Test
    public void shouldGetFileName() {
        String url = "https://github.com/timsixth/T-DataBasesAPI/releases/download/v1.7.2/T-DataBasesAPI-1.7.2.jar";

        int latestIndex = url.length();
        int latestIndexSlash = url.lastIndexOf("/");

        url = url.substring(latestIndexSlash + 1, latestIndex);

        assertEquals("T-DataBasesAPI-1.7.2.jar", url);
    }

    @Test
    public void shouldMatchRepositoryName() {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9]{3,}/[A-Za-z0-9]{3,}");

        assertTrue(pattern.matcher("timsixth/BoostersMiniGameAddon").matches());
        assertTrue(pattern.matcher("timi/Booster123").matches());

        assertFalse(pattern.matcher("ti/Booster123").matches());
        assertFalse(pattern.matcher("tim/Bo").matches());
    }
}
