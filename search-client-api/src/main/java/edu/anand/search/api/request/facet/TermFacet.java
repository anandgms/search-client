package edu.anand.search.api.request.facet;

public record TermFacet(String name, String field) implements Facet {

    @Override
    public String toString() {
        return "term{" +
                "field='" + field +
                '}';
    }  
}
