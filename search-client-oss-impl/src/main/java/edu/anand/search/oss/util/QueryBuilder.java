package edu.anand.search.oss.util;

import edu.anand.search.api.request.FuzzyRequest;
import edu.anand.search.api.request.SearchRequest;
import jakarta.json.stream.JsonGenerator;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch._types.query_dsl.BoolQuery;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch._types.query_dsl.QueryBuilders;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public final class QueryBuilder {

    public static Query buildQuery(FuzzyRequest request) {
        return Query.of(q -> q.match(m -> m.field(request.field())
                .query(FieldValue.of(request.searchTerm()))
                .fuzziness("auto")
                .prefixLength(1)
                .maxExpansions(50)
        ));
    }

    public static Query buildQuery(SearchRequest request) {
        BoolQuery.Builder builder = QueryBuilders.bool();

        addQuery(request, builder);
        addFilters(request, builder);

        return builder.build().toQuery();
    }

    private static void addQuery(SearchRequest request, BoolQuery.Builder builder) {
        Query query = Query.of(q -> q.queryString(qs -> qs.query(request.query().asString())));
        builder.must(query);
    }

    private static void addFilters(SearchRequest request, BoolQuery.Builder builder) {
        List<Query> filters = new ArrayList<>();
        if(request.filters() != null && !request.filters().isEmpty()) {
            request.filters().forEach(filter ->
                    filters.add(Query.of(q -> q.queryString(qs -> qs.query(filter.asString()))))
            );
        }
        builder.filter(filters);
    }

    public static String toJson(Query query) {
        StringWriter writer = new StringWriter();
        JsonGenerator generator = new JacksonJsonpMapper().jsonProvider()
                .createGenerator(writer);
        query.serialize(generator, new JacksonJsonpMapper());
        generator.flush();
        return writer.toString();
    }
}
