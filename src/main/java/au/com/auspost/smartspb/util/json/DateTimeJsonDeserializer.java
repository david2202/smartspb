package au.com.auspost.smartspb.util.json;


import au.com.auspost.smartspb.domain.Temperature;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.joda.time.DateTime;

import java.io.IOException;

public class DateTimeJsonDeserializer extends StdDeserializer<DateTime> {
    public DateTimeJsonDeserializer() {
        this(null);
    }

    public DateTimeJsonDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public DateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        return DateTime.parse(node.asText());
    }
}
