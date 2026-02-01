package edu.anand.search.api.request.query;

public interface Query {
    String asString(); // e.g. Solr-style: title:java AND status:active
}
