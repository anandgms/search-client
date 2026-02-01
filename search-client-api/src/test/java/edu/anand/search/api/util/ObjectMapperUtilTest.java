package edu.anand.search.api.util;

import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ObjectMapperUtilTest {

    private final String id = "12345-67890";
    private final LocalDate localDate = LocalDate.of(2020, Month.APRIL, 1);
    private final LocalTime localTime= LocalTime.of(13, 12, 31);
    private final LocalDateTime localDateTime= LocalDateTime.of(localDate, localTime);
    private final ZonedDateTime zonedDateTime= ZonedDateTime.of(localDate, localTime, ZoneId.systemDefault());
    private final Date date= Date.from(zonedDateTime.toInstant());
    private final List<String> colors = List.of("blue", "red", "yellow");

    record TestRecord(String id,
                      LocalDate localDate,
                      LocalTime localTime,
                      LocalDateTime localDateTime,
                      ZonedDateTime zonedDateTime,
                      Date date,
                      List<String> colors) {
    }

    @Test
    void toJson() {

        TestRecord record = new TestRecord(id, localDate, localTime, localDateTime, zonedDateTime, date, colors);
        String EXPECTED_JSON = "{\"id\":\"12345-67890\",\"localDate\":[2020,4,1],\"localTime\":[13,12,31],\"localDateTime\":[2020,4,1,13,12,31],\"zonedDateTime\":1585761151.000000000,\"date\":1585761151000,\"colors\":[\"blue\",\"red\",\"yellow\"]}";

        String result = ObjectMapperUtil.asJson(record);

        assertEquals(EXPECTED_JSON, result);
    }

    @Test
    void jsonToObject() {
        String JSON = "{\"id\":\"12345-67890\",\"localDate\":[2020,4,1],\"localTime\":[13,12,31],\"localDateTime\":[2020,4,1,13,12,31],\"zonedDateTime\":1585761151.000000000,\"date\":1585761151000,\"colors\":[\"blue\",\"red\",\"yellow\"]}";

        TestRecord result = ObjectMapperUtil.to(JSON, TestRecord.class);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(id, result.id(), "ID did not match"),
                () -> assertEquals(localDate, result.localDate(), "LocalDate did not match"),
                () -> assertEquals(localTime, result.localTime(), "LocalTime did not match"),
                () -> assertEquals(localDateTime, result.localDateTime(), "LocalDateTime did not match"),
                () -> assertTrue(zonedDateTime.isEqual(result.zonedDateTime()), "ZonedDateTime did not match"),
                () -> assertEquals(date, result.date(), "Date did not match"),
                () -> assertArrayEquals(colors.toArray(), result.colors().toArray(), "Colors did not match")
        );
    }

    @Test
    void mapToObject(){
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("localDate", localDate);
        map.put("localTime", localTime);
        map.put("localDateTime", localDateTime);
        map.put("zonedDateTime", zonedDateTime);
        map.put("date", date);
        map.put("colors", colors);

        TestRecord result = ObjectMapperUtil.to(map, TestRecord.class);

        assertAll(
                () -> assertNotNull(result),
                () -> assertEquals(id, result.id(), "ID did not match"),
                () -> assertEquals(localDate, result.localDate(), "LocalDate did not match"),
                () -> assertEquals(localTime, result.localTime(), "LocalTime did not match"),
                () -> assertEquals(localDateTime, result.localDateTime(), "LocalDateTime did not match"),
                () -> assertTrue(zonedDateTime.isEqual(result.zonedDateTime()), "ZonedDateTime did not match"),
                () -> assertEquals(date, result.date(), "Date did not match"),
                () -> assertArrayEquals(colors.toArray(), result.colors().toArray(), "Colors did not match")
        );
    }
}