package au.com.auspost.smartspb.util.json;


import au.com.auspost.smartspb.domain.Temperature;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class TemperatureJsonSerializer extends JsonSerializer<Temperature> {
    @Override
    public void serialize(Temperature temperature, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        jsonGenerator.writeNumber(temperature.getValue());
    }
}
