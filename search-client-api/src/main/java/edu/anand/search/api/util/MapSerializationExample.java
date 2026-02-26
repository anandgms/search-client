package edu.anand.search.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper.DefaultTyping;
import java.util.HashMap;
import java.util.Map;

public class MapSerializationExample {

    MapSerializationExample(){}

    public static void main(String[] args) throws Exception {
        // Create a Map with various types
        Map<String, Object> data = new HashMap<>();
        data.put("stringKey", "Hello, World!");
        data.put("integerKey", 123);
        data.put("booleanKey", true);
        data.put("nestedMap", new HashMap<String, String>() {{
            put("innerKey", "innerValue");
        }});

        ObjectMapper mapper = new ObjectMapper();
        // Enable default typing to include a "@class" property with type info
        mapper.enableDefaultTyping(DefaultTyping.NON_FINAL); 

        // Serialize the Map to a JSON string
        String json = mapper.writeValueAsString(data);
        System.out.println("Serialized JSON with type info: " + json);

        // Deserialize the JSON string back to a Map<String, Object>
        // Jackson will use the "@class" info to recreate original types
        Map<String, Object> deserializedData = mapper.readValue(json, Map.class);
        System.out.println("Deserialized map: " + deserializedData);

        // Verify original types are maintained
        System.out.println("Type of 'integerKey' value: " + deserializedData.get("integerKey").getClass().getName());
    }
}
