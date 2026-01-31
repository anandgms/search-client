package edu.anand.search.api.request;

public record FuzzyRequest (String field,
    String searchTerm,
    int maxResults){
}
