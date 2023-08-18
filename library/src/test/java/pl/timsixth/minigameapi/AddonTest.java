package pl.timsixth.minigameapi;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AddonTest {

    @Test
    public void shouldGetFileName() {
        String url = "https://github.com/timsixth/T-DataBasesAPI/releases/download/v1.7.2/T-DataBasesAPI-1.7.2.jar";

        int latestIndex = url.length();
        int latestIndexSlash = url.lastIndexOf("/");

        url = url.substring(latestIndexSlash + 1, latestIndex);

        assertEquals("T-DataBasesAPI-1.7.2.jar", url);
    }
}
