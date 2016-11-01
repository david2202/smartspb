package au.com.auspost.smartspb.util.json;

import au.com.auspost.smartspb.domain.Temperature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TemperatureJsonSerializerTest {

    @Test
    public void testSerialize() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        assertThat(mapper.writeValueAsString(Temperature.valueOf("21.5")), is("21.5"));
    }
}
