package edu.anand.search.oss.util;

import edu.anand.search.api.request.SearchRequest;
import edu.anand.search.api.request.facet.*;
import jakarta.json.stream.JsonGenerator;
import org.jspecify.annotations.NonNull;
import org.opensearch.client.json.JsonData;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch._types.Time;
import org.opensearch.client.opensearch._types.aggregations.*;
import org.opensearch.client.opensearch._types.query_dsl.BoolQuery;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch._types.query_dsl.QueryBuilders;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class OpenSearchRequestBuilder {

    public static String toJson(Query query) {
        StringWriter writer = new StringWriter();
        JsonGenerator generator = new JacksonJsonpMapper().jsonProvider()
                .createGenerator(writer);
        query.serialize(generator, new JacksonJsonpMapper());
        generator.flush();
        return writer.toString();
    }

    public static org.opensearch.client.opensearch.core.SearchRequest buildRequest(String indexName, Query query) {
        return new org.opensearch.client.opensearch.core.SearchRequest.Builder()
                .index(indexName).query(query).build();
    }

    public static org.opensearch.client.opensearch.core.SearchRequest buildFuzzyRequest(String indexName, SearchRequest request) {
        String queryString = request.query().asString();
        String[] tokens = queryString.split(":");

        Query query = Query.of(q -> q.match(m -> m.field(tokens[0])
                .query(FieldValue.of(tokens[1]))
                .fuzziness("auto")
                .prefixLength(2)
                .maxExpansions(50)
        ));

        return new org.opensearch.client.opensearch.core.SearchRequest.Builder()
                .index(indexName).query(query)
                .build();
    }

    public static org.opensearch.client.opensearch.core.SearchRequest buildRequest(String indexName, SearchRequest request) {
        if (request.fuzzy()) {
            return buildFuzzyRequest(indexName, request);
        }

        Query query = buildQueryAndFilters(request);
        Map<String, Aggregation> aggregationMap = buildAggregations(request);

        return new org.opensearch.client.opensearch.core.SearchRequest.Builder()
                .index(indexName).query(query)
                .aggregations(aggregationMap).build();
    }

    public static Query buildQueryAndFilters(SearchRequest request) {
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
        if (request.filters() != null && !request.filters().isEmpty()) {
            request.filters().forEach(filter ->
                    filters.add(Query.of(q -> q.queryString(qs -> qs.query(filter.asString()))))
            );
        }
        builder.filter(filters);
    }

    private static Map<String, Aggregation> buildAggregations(SearchRequest request) {
        Map<String, Aggregation> aggregationMap = new HashMap<>();
        List<Facet> facets = request.facets();
        for (Facet facet : facets) {
            Aggregation aggregation = null;
            if (facet instanceof TermFacet termFacet) {
                aggregation = buildTermAggregation(termFacet);
            } else if (facet instanceof NumericRangeFacet rangeFacet) {
                aggregation = buildNumericRangeAggregation(rangeFacet);
            } else if (facet instanceof DateRangeFacet rangeFacet) {
                aggregation = buildDateRangeAggregation(rangeFacet);
            } else if (facet instanceof HistogramFacet intervalFacet) {
                aggregation = buildHistogramAggregation(intervalFacet);
            } else if (facet instanceof DateHistogramFacet intervalFacet) {
                aggregation = buildDateHistogramAggregation(intervalFacet);
            }

            if (aggregation != null) {
                aggregationMap.put(facet.name(), aggregation);
            }
        }
        return aggregationMap;
    }

    private static @NonNull Aggregation buildTermAggregation(TermFacet termFacet) {
        return new Aggregation.Builder().terms(t -> t.field(termFacet.field())
                .size(termFacet.limit())).build();
    }

    private static @NonNull Aggregation buildNumericRangeAggregation(NumericRangeFacet rangeFacet) {
        List<Range<Float>> ranges = rangeFacet.ranges();
        List<AggregationRange> aggregationRanges = new ArrayList<>();
        for (Range<Float> range : ranges) {
            AggregationRange aggregationRange = new AggregationRange.Builder()
                    .key(range.key())
                    .from(JsonData.of(range.from()))
                    .to(JsonData.of(range.to())).build();
            aggregationRanges.add(aggregationRange);
        }
        return new Aggregation.Builder().range(r -> r.field(rangeFacet.field())
                        .ranges(aggregationRanges))
                .build();
    }

    private static Aggregation buildHistogramAggregation(HistogramFacet intervalFacet) {
        return new Aggregation.Builder().histogram(r -> r.field(intervalFacet.field())
                        .interval(intervalFacet.interval()))
                .build();
    }

    private static Aggregation buildDateHistogramAggregation(DateHistogramFacet intervalFacet) {
        return new Aggregation.Builder().dateHistogram(r -> r.field(intervalFacet.field())
                        .calendarInterval(CalendarInterval.Year)
                        .format("yyyy-MM-dd")
                 )
                .build();
    }

    private static @NonNull Aggregation buildDateRangeAggregation(DateRangeFacet rangeFacet) {
        List<Range<String>> ranges = rangeFacet.ranges();
        List<DateRangeExpression> aggregationRanges = new ArrayList<>();
        for (Range<String> range : ranges) {
            DateRangeExpression aggregationRange = new DateRangeExpression.Builder().key(range.key())
                    .from(FieldDateMath.builder().expr(range.from()).build())
                    .to(FieldDateMath.builder().expr(range.to()).build())
                    .build();
            aggregationRanges.add(aggregationRange);
        }
        return new Aggregation.Builder().dateRange(r -> r.field(rangeFacet.field())
                        .ranges(aggregationRanges)
                        //.format("yyyy-MM-dd")
                )
                .build();
    }
}