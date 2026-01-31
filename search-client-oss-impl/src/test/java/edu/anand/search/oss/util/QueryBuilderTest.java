package edu.anand.search.oss.util;

import edu.anand.search.api.request.SearchRequest;
import edu.anand.search.api.request.SimpleFilter;
import org.junit.jupiter.api.Test;
import org.opensearch.client.opensearch._types.query_dsl.Query;

import static org.junit.jupiter.api.Assertions.assertEquals;

class QueryBuilderTest {

    @Test
    void buildQuery() {
        SearchRequest request = new SearchRequest();
        request.addFilter(new SimpleFilter("(ssn:1234567890 OR lname:Doe)"));

        Query query = QueryBuilder.buildQuery(request);
        assertEquals("{\"bool\":{\"filter\":[{\"query_string\":{\"query\":\"(ssn:1234567890 OR lname:Doe)\"}}],\"must\":[{\"query_string\":{\"query\":\"*:*\"}}]}}", QueryBuilder.toJson(query));
    }
}