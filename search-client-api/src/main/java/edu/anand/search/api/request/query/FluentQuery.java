package edu.anand.search.api.request.query;

public final class FluentQuery implements Query {

  public static Query eq(String field, Object value) {
    return new LuceneQuery(field + ":" + format(value));
  }

  public static Query ne(String field, Object value) {
    return (new LuceneQuery("NOT " + field + ":" + format(value)));
  }

  public static Query in(String field, Object... values) {
    return new LuceneQuery(field + ":(" + join(values) + ")");
  }

  public static Query between(String field, Object fromValue, Object toValue) {
    return new LuceneQuery(field + ":{" + fromValue + " TO " + toValue + "}");
  }

  public static Query gt(String field, Object value) {
    return new LuceneQuery(field + ":{" + value + " TO *}");
  }

  public static Query gte(String field, Object value) {
    return new LuceneQuery(field + ":[" + value + " TO *]");
  }

  public static Query lt(String field, Object value) {
    return new LuceneQuery(field + ":{* TO " + value + "}");
  }

  public static Query lte(String field, Object value) {
    return new LuceneQuery(field + ":[* TO " + value + "]");
  }

  public static Query and(Query... expressions) {
    return new LuceneQuery(wrap(expressions, "AND"));
  }

  public static Query or(Query... expressions) {
    return new LuceneQuery(wrap(expressions, "OR"));
  }

  public static Query not(Query expression) {
    return new LuceneQuery("NOT (" + expression.toString() + ")");
  }

  private static String wrap(Query[] expressions, String op) {
    return "("
        + java.util.Arrays.stream(expressions)
            .map(Query::toString)
            .reduce((a, b) -> a + " " + op + " " + b)
            .orElse("")
        + ")";
  }

  private static String join(Object[] values) {
    return java.util.Arrays.stream(values)
        .map(FluentQuery::format)
        .reduce((a, b) -> a + " " + b)
        .orElse("");
  }

  private static String format(Object value) {
    if (value instanceof Number || value instanceof Boolean) {
      return value.toString();
    }
    return "\"" + value + "\"";
  }

  @Override
  public String toString() {
    return "";
  }
}
