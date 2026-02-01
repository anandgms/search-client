package edu.anand.search.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.anand.search.api.dto.NamedField;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public final class ObjectMapperUtil {

    public static ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);

        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        return mapper;
    }

    public static String asJson(Object object) {
        ObjectMapper mapper = objectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T to(String json, Class<T> clazz){
        ObjectMapper mapper = objectMapper();
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T to(Map<String, Object> item, Class<T> clazz) {
        if (item == null) {
            return null;
        }
        ObjectMapper mapper = objectMapper();
        return mapper.convertValue(item, clazz);
    }

    public static <T> T to(NamedField item, Class<T> clazz) {
        if (item == null) {
            return null;
        }
        ObjectMapper mapper = objectMapper();
        return mapper.convertValue(item, clazz);
    }

    public static <T> List<T> to(List<NamedField> items, Class<T> clazz) {
        if (items == null || items.isEmpty()) {
            return Collections.emptyList();
        }
        return items.stream().map(item -> to(item, clazz)).toList();
    }

    public static <T> Map<String, Object> asMap(T document) {
        ObjectMapper mapper = objectMapper();
        return mapper.convertValue(
                document, new TypeReference<>() {
                }
        );
    }
}
