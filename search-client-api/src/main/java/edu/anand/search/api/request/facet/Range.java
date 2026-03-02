package edu.anand.search.api.request.facet;

public record Range<T>(String key, T from, T to) {}
