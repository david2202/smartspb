package au.com.auspost.smartspb.util.json;


import au.com.auspost.smartspb.domain.Temperature;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class TemperatureJsonDeserializer extends StdDeserializer<Temperature> {
    public TemperatureJsonDeserializer() {
        this(null);
    }

    public TemperatureJsonDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Temperature deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        return Temperature.valueOf(node.asText());
    }
}
