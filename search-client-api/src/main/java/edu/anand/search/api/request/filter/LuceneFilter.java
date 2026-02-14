package edu.anand.search.api.request.filter;

public class LuceneFilter implements Filter {
    // e.g. Solr-style: title:java AND status:active
    private final String filter;

    public LuceneFilter(String filter){
        this.filter = filter;
    }

    @Override
    public String toString() {
        return filter;
    }
}
