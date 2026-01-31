package edu.anand.search.api.dto;

import java.util.HashMap;

public class NamedField extends HashMap<String, Object> implements Document {

    @Override
    public String getId() {
        return get("id").toString();
    }}
