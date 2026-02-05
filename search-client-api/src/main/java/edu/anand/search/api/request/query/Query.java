package edu.anand.search.api.request.query;

public interface Query {
    String toString(); // e.g. Solr-style: title:java AND status:active
}
