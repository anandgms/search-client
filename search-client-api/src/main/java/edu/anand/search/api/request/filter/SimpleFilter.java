package edu.anand.search.api.request.filter;

public class SimpleFilter implements Filter {
    // e.g. Solr-style: title:java AND status:active
    private final String filter;

    public SimpleFilter(String filter){
        this.filter = filter;
    }

    @Override
    public String toString() {
        return filter;
    }
}
