package edu.anand.dto;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchResult {

  private List<String> fields;
  private List<Map<String, Object>> docs;

  private String json;
  private String debugJson;
}
