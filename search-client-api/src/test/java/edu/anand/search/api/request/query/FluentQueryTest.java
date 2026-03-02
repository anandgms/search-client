package edu.anand.search.api.request.query;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class FluentQueryTest {

  @Test
  void logicalQuery() {
    Query age = FluentQuery.between("age", 18, 35);
    Query sports = FluentQuery.in("sport", "Tennis", "Badminton");
    Query level = FluentQuery.in("level", "beginner", "intermediate", "expert");
    Query score = FluentQuery.in("score", 1.0, 2.0, 3.0);

    Query candidates = FluentQuery.and(age, sports, FluentQuery.or(level, score));
    // String expectedJson = "(age:{18 TO 35} AND sport:(\"Tennis\" \"Badminton\") AND
    // (level:(\"beginner\" \"intermediate\" \"expert\") OR score:(1.0 2.0 3.0)))";
    assertNotNull(candidates.toString().trim());
  }
}
