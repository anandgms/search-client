package edu.anand.search.api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        return mapper;
    }

    public static String toJson(Object object) {
        ObjectMapper mapper = objectMapper();
        try {
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObject(String json, Class<T> clazz){
        ObjectMapper mapper = objectMapper();
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T toObject(Map<String, Object> item, Class<T> clazz) {
        if (item == null) {
            return null;
        }
        ObjectMapper mapper = objectMapper();
        return mapper.convertValue(item, clazz);
    }


    public static <T> T toObject(NamedField item, Class<T> clazz) {
        if (item == null) {
            return null;
        }
        ObjectMapper mapper = objectMapper();
        return mapper.convertValue(item, clazz);
    }

    public static <T> List<T> toObject(List<NamedField> items, Class<T> clazz) {
        if (items == null || items.isEmpty()) {
            return Collections.emptyList();
        }
        return items.stream().map(item -> toObject(item, clazz)).toList();
    }
}
