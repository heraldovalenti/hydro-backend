package com.aes.dashboard.backend.service.pwsWeatherData;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;

public class PWSWeatherResponseDeserializer extends JsonDeserializer<PWSWeatherResponse> {


    @Override
    public PWSWeatherResponse deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
            throws IOException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);
        if (node.isArray()) {
            return null;
        }
        return codec.treeToValue(node, PWSWeatherResponse.class);
    }
}
