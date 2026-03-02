package edu.anand.search.api.result;

import java.util.List;
import java.util.Map;

public record HighlightResult(Map<String, List<String>> fields) {}
