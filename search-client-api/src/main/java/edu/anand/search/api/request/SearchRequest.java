package edu.anand.search.api.request;

import edu.anand.search.api.request.facet.Facet;
import edu.anand.search.api.request.filter.Filter;
import edu.anand.search.api.request.filter.SimpleFilter;
import edu.anand.search.api.request.query.Query;
import edu.anand.search.api.request.query.SimpleQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SearchRequest {
    private Query query;
    private boolean fuzzySearch = false;
    private List<Filter> filters;
    private List<Facet> facets;
    private Highlight highlight;
    private int page;
    private int size;
    private int limit;  
    private List<Sort> sortBy;
    private List<String> includeFields;
    private List<String> excludeFields;

    public SearchRequest() {
        this.query = new SimpleQuery("*:*");
    }

    public SearchRequest page(int page) {
        this.page = page;
        return this;
    }

    public int page() {
        return this.page;
    }

    public SearchRequest size(int size) {
        this.size = size;
        return this;
    }

    public int size() {
        return this.size;
    }

    public SearchRequest limit(int maxResults) {
        this.limit = maxResults;
        return this;
    }

    public int limit() {
        return this.limit;
    }

    public SearchRequest query(String luceneQueryString) {
        fuzzySearch = false;
        this.query = new SimpleQuery(luceneQueryString);
        return this;
    }

    public SearchRequest query(String field, String value) {
        fuzzySearch = false;
        this.query = new SimpleQuery(field + ":" + value);
        return this;
    }

    public SearchRequest query(Query query) {
        fuzzySearch = false;
        this.query = query;
        return this;
    }

    public SearchRequest fuzzyQuery(String luceneQueryString) {
        fuzzySearch = true;
        this.query = new SimpleQuery(luceneQueryString);
        return this;
    }

    public SearchRequest fuzzyQuery(String field, String value) {
        fuzzySearch = true;
        this.query = new SimpleQuery(field + ":" + value);
        return this;
    }

    public boolean isFuzzySearch() {
        return this.fuzzySearch;
    }

    public String query() {
        return this.query.toString();
    }

    public SearchRequest addFilter(String luceneQueryString) {
        addFilter(new SimpleFilter(luceneQueryString));
        return this;
    }

    public SearchRequest addFilter(Filter filter) {
        if (filters == null) {
            filters = new ArrayList<>();
        }
        filters.add(filter);
        return this;
    }

    public SearchRequest addFilters(Collection<Filter> filters) {
        if (filters == null) {
            filters = new ArrayList<>();
        }
        filters.addAll(filters);
        return this;
    }

    public List<Filter> filters() {
        return this.filters;
    }

    public SearchRequest clearFilters() {
        if (filters != null) {
            filters.clear();
        }
        return this;
    }

    public SearchRequest addFacet(Facet facet) {
        if (facets == null) {
            facets = new ArrayList<>();
        }
        facets.add(facet);
        return this;
    }

    public SearchRequest addFacets(Collection<Facet> facets) {
        if (facets == null) {
            facets = new ArrayList<>();
        }
        facets.addAll(facets);
        return this;
    }

    public SearchRequest addFacets(Facet... facets) {
        if (this.facets == null) {
            this.facets = new ArrayList<>();
        }
        for (Facet facet : facets) {
            this.facets.add(facet);
        }
        return this;
    }

    public List<Facet> facets() {
        return this.facets;
    }

    public SearchRequest clearFacets() {
        if (facets != null) {
            facets.clear();
        }
        return this;
    }

    public SearchRequest highlight(Highlight highlight) {
        this.highlight = highlight;
        return this;
    }

    public Highlight highlight() {
        return this.highlight;
    }

    public SearchRequest sortBy(Sort soryBy) {
        if (this.sortBy == null) {
            this.sortBy = new ArrayList<>();
        }
        this.sortBy.add(soryBy);
        return this;
    }

    public SearchRequest sortBy(List<Sort> sortBy) {
        this.sortBy = sortBy;
        return this;
    }

    public SearchRequest sortBy(Sort... sortBy) {
        if (this.sortBy == null) {
            this.sortBy = new ArrayList<>();
        }
        for (Sort sort : sortBy) {
            this.sortBy.add(sort);
        }
        return this;
    }

    public List<Sort> sortBy() {
        return this.sortBy;
    }

    public SearchRequest includeFields(List<String> includeFields) {
        this.includeFields = includeFields;
        return this;
    }

    public List<String> includeFields() {
        return this.includeFields;
    }

    public SearchRequest includeFields(String... includeFields) {
        if (this.includeFields == null) {
            this.includeFields = new ArrayList<>();
        }
        for (String field : includeFields) {
            this.includeFields.add(field);
        }
        return this;
    }

    public SearchRequest excludeFields(List<String> excludeFields) {
        this.excludeFields = excludeFields;
        return this;
    }

    public SearchRequest excludeFields(String... excludeFields) {
        if (this.excludeFields == null) {
            this.excludeFields = new ArrayList<>();
        }
        for (String field : excludeFields) {
            this.excludeFields.add(field);
        }
        return this;
    }

    public List<String> excludeFields() {
        return this.excludeFields;
    }
    
    @Override
    public String toString() {
        return "SearchRequest{" +
                "query=" + query +
                ", filters=" + filters +
                ", facets=" + facets +
                ", highlight=" + highlight +
                ", page=" + page +
                ", size=" + size +
                ", limit=" + limit +
                ", sortBy=" + sortBy +
                ", includeFields=" + includeFields +
                ", excludeFields=" + excludeFields +
                '}';
    }
}
