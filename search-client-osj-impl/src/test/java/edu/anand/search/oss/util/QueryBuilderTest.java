package edu.anand.search.oss.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.anand.search.api.request.SearchRequest;
import edu.anand.search.api.request.filter.LuceneFilter;
import org.junit.jupiter.api.Test;
import org.opensearch.client.opensearch._types.query_dsl.Query;

class QueryBuilderTest {

  @Test
  void buildQuery() {
    SearchRequest request = new SearchRequest();
    request.addFilter(new LuceneFilter("(ssn:1234567890 OR lname:Doe)"));

    Query query = SearchRequestBuilder.buildQueryAndFilters(request);
    assertEquals(
        "{\"bool\":{\"filter\":[{\"query_string\":{\"query\":\"(ssn:1234567890 OR lname:Doe)\"}}],\"must\":[{\"query_string\":{\"query\":\"*:*\"}}]}}",
        SearchRequestBuilder.toJson(query));
  }
}
