package edu.anand.search.api.request;

import java.util.List;

public record Highlight(List<String> fields, String searchTerm, int limit) {
}
