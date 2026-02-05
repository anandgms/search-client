package edu.anand.search.api.request;

public record Sort(String field, Order order) {

    public enum Order {
        ASC, DESC;

        Order reverse() {
            if (this == ASC) return DESC;
            return ASC;
        }
    }

    @Override
    public String toString() {
        return "Sort{" +
                "field='" + field +
                ", order=" + order +
                '}';
    }
}