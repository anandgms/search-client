package edu.anand.search.rhlc.util;

import edu.anand.search.api.request.SearchRequest;
import edu.anand.search.api.request.filter.SimpleFilter;
import org.junit.jupiter.api.Test;

class QueryBuilderTest {

    @Test
    void buildQuery() {
        SearchRequest request = new SearchRequest();
        request.addFilter(new SimpleFilter("(ssn:1234567890 OR lname:Doe)"));

//        Query query = OpenSearchRequestBuilder.buildQueryAndFilters(request);
//        assertEquals("{\"bool\":{\"filter\":[{\"query_string\":{\"query\":\"(ssn:1234567890 OR lname:Doe)\"}}],\"must\":[{\"query_string\":{\"query\":\"*:*\"}}]}}", OpenSearchRequestBuilder.toJson(query));
    }
}