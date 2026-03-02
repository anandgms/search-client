package edu.anand.search.oss.util;

import edu.anand.search.api.dto.NamedField;
import edu.anand.search.api.result.*;
import edu.anand.search.api.result.facet.FacetResult;
import edu.anand.search.api.result.facet.HistogramFacetResult;
import edu.anand.search.api.result.facet.RangeFacetResult;
import edu.anand.search.api.result.facet.TermFacetResult;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jspecify.annotations.NonNull;
import org.opensearch.client.opensearch._types.aggregations.*;
import org.opensearch.client.opensearch.core.SearchResponse;
import org.opensearch.client.opensearch.core.search.Hit;

public class SearchResponseBuilder {

  public static SearchResult buildSearchResult(SearchResponse<NamedField> response) {
    SearchResult searchResult = new SearchResult();

    // Hits
    List<NamedField> documents = response.hits().hits().stream().map(Hit::source).toList();
    searchResult.setDocuments(documents);

    if (response.hits().total() != null) {
      searchResult.setTotalHits(response.hits().total().value());
    }

    // Highlights
    List<Hit<NamedField>> hits = response.hits().hits();
    Map<String, HighlightResult> highlights = new HashMap<>();
    for (Hit<NamedField> hit : hits) {
      Map<String, List<String>> snippets = hit.highlight();
      highlights.put(hit.id(), new HighlightResult(snippets));
    }
    searchResult.setHighlights(highlights);

    // Facets
    Map<String, Aggregate> aggregateMap = response.aggregations();
    for (Map.Entry<String, Aggregate> entry : aggregateMap.entrySet()) {
      FacetResult facetResult = null;
      if (entry.getValue().isSterms()) {
        facetResult = getTermFacetResult(entry);
      } else if (entry.getValue().isHistogram()) {
        facetResult = getHistogramFacetResult(entry);
      } else if (entry.getValue().isDateHistogram()) {
        facetResult = getDateHistogramFacetResult(entry);
      } else if (entry.getValue().isRange()) {
        facetResult = getNumericRangeFacetResult(entry);
      } else if (entry.getValue().isDateRange()) {
        facetResult = getDateRangeFacetResult(entry);
      }

      if (facetResult != null) {
        searchResult.addFacet(entry.getKey(), facetResult);
      }
    }

    return searchResult;
  }

  private static @NonNull RangeFacetResult getDateRangeFacetResult(
      Map.Entry<String, Aggregate> entry) {
    List<RangeBucket> buckets = entry.getValue().dateRange().buckets().array();
    RangeFacetResult result = new RangeFacetResult(entry.getKey(), new HashMap<>());
    for (RangeBucket bucket : buckets) {
      result.addResult(bucket.key(), bucket.docCount());
    }
    return result;
  }

  private static @NonNull RangeFacetResult getNumericRangeFacetResult(
      Map.Entry<String, Aggregate> entry) {
    List<RangeBucket> buckets = entry.getValue().range().buckets().array();
    RangeFacetResult result = new RangeFacetResult(entry.getKey(), new HashMap<>());
    for (RangeBucket bucket : buckets) {
      result.addResult(bucket.key(), bucket.docCount());
    }
    return result;
  }

  private static @NonNull HistogramFacetResult getHistogramFacetResult(
      Map.Entry<String, Aggregate> entry) {
    List<HistogramBucket> buckets = entry.getValue().histogram().buckets().array();
    HistogramFacetResult result = new HistogramFacetResult(entry.getKey(), new HashMap<>());
    for (HistogramBucket bucket : buckets) {
      result.addResult(bucket.key(), bucket.docCount());
    }
    return result;
  }

  private static @NonNull HistogramFacetResult getDateHistogramFacetResult(
      Map.Entry<String, Aggregate> entry) {
    List<DateHistogramBucket> buckets = entry.getValue().dateHistogram().buckets().array();
    HistogramFacetResult result = new HistogramFacetResult(entry.getKey(), new HashMap<>());
    for (DateHistogramBucket bucket : buckets) {
      result.addResult(bucket.key(), bucket.docCount());
    }
    return result;
  }

  private static @NonNull TermFacetResult getTermFacetResult(Map.Entry<String, Aggregate> entry) {
    List<StringTermsBucket> buckets = entry.getValue().sterms().buckets().array();
    TermFacetResult result = new TermFacetResult(entry.getKey(), new HashMap<>());
    for (StringTermsBucket bucket : buckets) {
      result.addResult(bucket.key(), bucket.docCount());
    }
    return result;
  }
}
