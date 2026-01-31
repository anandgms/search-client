package edu.anand.search.api.request;

public class SimpleFilter implements Filter {
    // e.g. Solr-style: title:java AND status:active
    private final String filter;

    public SimpleFilter(String filter){
        this.filter = filter;
    }

    @Override
    public String asString() {
        return filter;
    }
}
