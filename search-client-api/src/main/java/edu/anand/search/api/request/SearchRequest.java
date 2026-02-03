package edu.anand.search.api.request;

import edu.anand.search.api.request.facet.Facet;
import edu.anand.search.api.request.filter.Filter;
import edu.anand.search.api.request.query.Query;
import edu.anand.search.api.request.query.SimpleQuery;

import java.util.ArrayList;
import java.util.List;

public class SearchRequest {
    private String index;
    private int page;
    private int size;
    private Query query;
    private List<Filter> filters;
    private List<Facet> facets;
    private List<Sort> sort;
    private List<String> includeFields;
    private List<String> excludeFields;
    private Highlight highlight;
    private boolean fuzzy;

    public String asString() {
        return query.asString();
    }

    public SearchRequest() {
        this.query = new SimpleQuery("*:*");
    }

    public boolean fuzzy() {
        return fuzzy;
    }

    public SearchRequest enableFuzzySearch() {
        this.fuzzy = true;
        return this;
    }

    public SearchRequest disableFuzzySearch() {
        this.fuzzy = true;
        return this;
    }

    public String index() {
        return index;
    }

    public SearchRequest setIndex(String index) {
        this.index = index;
        return this;
    }

    public int page() {
        return page;
    }

    public SearchRequest setPage(int page) {
        this.page = page;
        return this;
    }

    public int limit() {
        return size;
    }

    public Query query() {
        return query;
    }

    public SearchRequest setQuery(Query query) {
        this.query = query;
        return this;
    }

    public List<Filter> filters() {
        return filters;
    }

    public SearchRequest setFilters(List<Filter> filters) {
        this.filters = filters;
        return this;
    }

    public List<Facet> facets() {
        return facets;
    }

    public SearchRequest setFacets(List<Facet> facets) {
        this.facets = facets;
        return this;
    }

    public List<Sort> sortFields() {
        return sort;
    }

    public SearchRequest setSort(List<Sort> sort) {
        this.sort = sort;
        return this;
    }

    public List<String> includeFields() {
        return includeFields;
    }

    public SearchRequest setIncludeFields(List<String> includeFields) {
        this.includeFields = includeFields;
        return this;
    }

    public List<String> excludeFields() {
        return excludeFields;
    }

    public SearchRequest setExcludeFields(List<String> excludeFields) {
        this.excludeFields = excludeFields;
        return this;
    }

    public Highlight highlight() {
        return highlight;
    }

    public SearchRequest setHighlight(Highlight highlight) {
        this.highlight = highlight;
        return this;
    }

    public SearchRequest addFilter(Filter filter) {
        if (filters == null) {
            filters = new ArrayList<>();
        }
        filters.add(filter);
        return this;
    }

    public SearchRequest addFacet(Facet facet) {
        if (facets == null) {
            facets = new ArrayList<>();
        }
        facets.add(facet);
        return this;
    }

    public SearchRequest limit(int count) {
        this.size = count;
        return this;
    }


}
