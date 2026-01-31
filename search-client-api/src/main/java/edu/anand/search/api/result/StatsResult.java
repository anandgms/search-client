package edu.anand.search.api.result;

public record StatsResult(
        String field,
        double min,
        double max,
        double avg,
        long count) {
}
