package edu.anand.search.api.request.query;

public class SimpleQuery implements Query {
    // e.g. Solr-style: title:java AND status:active
    private final String query;

    public SimpleQuery(String query){
        this.query = query;
    }

    @Override
    public String asString() {
        return query;
    }
}
