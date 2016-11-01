package au.com.auspost.smartspb.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;

public class StreetPostingBoxTest {

    @Test
    public void testNewApiKey() {
        String apiKey = UUID.randomUUID().toString();
        StreetPostingBox spb = new StreetPostingBox();
        spb.setApiKey(apiKey);
        spb.newApiKey();
        assertEquals(apiKey, spb.getPrevApiKey());
        assertNotEquals(apiKey, spb.getApiKey());
        assertThat(spb.getApiKey().length(), equalTo(36));
    }

    @Test
    public void testCheckApiKey() {
        String apiKey = UUID.randomUUID().toString();
        StreetPostingBox spb = new StreetPostingBox();
        spb.setApiKey(apiKey);

        assertThat(spb.checkApiKey(apiKey), is(true));
    }

    @Test
    public void testCheckPreviousApiKey() {
        String apiKey = UUID.randomUUID().toString();
        String prevApiKey = UUID.randomUUID().toString();

        StreetPostingBox spb = new StreetPostingBox();
        spb.setApiKey(apiKey);
        spb.setPrevApiKey(prevApiKey);

        assertThat(spb.checkApiKey(prevApiKey), is(true));
    }

    @Test
    public void testCheckInvalidApiKey() {
        String apiKey = UUID.randomUUID().toString();
        String prevApiKey = UUID.randomUUID().toString();

        StreetPostingBox spb = new StreetPostingBox();
        spb.setApiKey(apiKey);
        spb.setPrevApiKey(prevApiKey);

        assertThat(spb.checkApiKey(UUID.randomUUID().toString()), is(false));
    }

    @Test
    public void testCheckInvalidApiKeyWithNullPreviousApiKey() {
        String apiKey = UUID.randomUUID().toString();

        StreetPostingBox spb = new StreetPostingBox();
        spb.setApiKey(apiKey);

        assertThat(spb.checkApiKey(UUID.randomUUID().toString()), is(false));
    }
}
