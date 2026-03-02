package edu.anand.search.api.request.query;

public class LuceneQuery implements Query {
  // e.g. Solr-style: title:java AND status:active
  private final String query;

  public LuceneQuery(String query) {
    this.query = query;
  }

  @Override
  public String toString() {
    return query;
  }
}
