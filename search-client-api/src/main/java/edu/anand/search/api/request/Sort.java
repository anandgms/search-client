package edu.anand.search.api.request;

public class Sort {

    public enum Order {
        ASC, DESC;

        Order reverse() {
            if (this == ASC) return DESC;
            return ASC;
        }
    }

    private String field;
    private Order order;
}
