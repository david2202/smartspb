package au.com.auspost.smartspb.util.json;

import au.com.auspost.smartspb.domain.Temperature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TemperatureJsonDeserializerTest {

    @Test
    public void testDeserialize() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        assertThat(mapper.readValue("21.5", Temperature.class), is(Temperature.valueOf("21.5")));
    }
}
