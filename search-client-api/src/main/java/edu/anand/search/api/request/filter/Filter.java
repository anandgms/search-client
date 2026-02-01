package edu.anand.search.api.request.filter;

public interface Filter {
    String asString(); // e.g. Solr-style: title:java AND status:active
}
