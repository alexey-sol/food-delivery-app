package com.github.alexeysol.app.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public <T> T deserialize(String json, Class<T> valueType) {
        try {
            return mapper.readValue(json, valueType);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

    public <T> T deserialize(String json, TypeReference<T> valueTypeRef) {
        try {
            return mapper.readValue(json, valueTypeRef);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

    public Map<String, Object> deserialize(String json) {
        try {
            ObjectReader reader = mapper.readerFor(Map.class);
            return reader.readValue(json);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }

    public <T> String serialize(T value) {
        try {
            return mapper.writeValueAsString(value);
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
