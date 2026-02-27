package edu.anand.util;

import edu.anand.search.api.dto.Document;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

public record Book (UUID id,
    String title,
    String author,
    LocalDate publishDate,
    float price,
    Set<String> sellers) implements Document {

    @Override
    public String docId() {
        return id.toString();
    }
}
