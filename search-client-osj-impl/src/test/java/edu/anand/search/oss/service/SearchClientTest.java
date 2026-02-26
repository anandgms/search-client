package edu.anand.search.oss.service;

import edu.anand.search.api.request.*;
import edu.anand.search.api.request.facet.DateHistogramFacet;
import edu.anand.search.api.request.facet.DateRangeFacet;
import edu.anand.search.api.request.facet.HistogramFacet;
import edu.anand.search.api.request.facet.NumericRangeFacet;
import edu.anand.search.api.request.facet.Range;
import edu.anand.search.api.request.facet.TermFacet;
import edu.anand.search.api.result.OperationResult;
import edu.anand.search.api.result.SearchResult;
import edu.anand.search.api.result.Status;
import edu.anand.search.api.service.SearchClient;
import edu.anand.search.api.util.ObjectMapperUtil;
import edu.anand.search.oss.util.Book;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SearchClientTest {

    private static final UUID bookId = UUID.fromString("693a64ee-0865-4947-9dd5-67f21933b142");
    private static final Book book = new Book(bookId, "Jurassic Park",
            "Michael Crichton", LocalDate.of(1997, Month.JUNE, 21),
            9.99F, Set.of("Barnes & Nobles", "Used Book Store", "Amazon", "Walmart", "Library"));

    @Autowired
    SearchClient searchClient;

    @Test
    void saveOrUpdate() {
        OperationResult result = searchClient.saveOrUpdate("books", book);

        assertEquals(Status.Created, result.result());
    }

    @Test
    void saveOrUpdate_Collection() throws IOException {
        Path path = Paths.get("src/test/resources/books.txt");
        List<Book> books = Files.readAllLines(path).stream().map(json -> ObjectMapperUtil.to(json, Book.class)).toList();

        OperationResult result = searchClient.saveOrUpdate("books", books);

        assertEquals(Status.Created, result.result());
    }

    @Test
    void deleteById() {
        searchClient.saveOrUpdate("books", book);

        OperationResult result = searchClient.deleteById("books", book.docId());

        assertEquals(Status.Deleted, result.result());
    }

    @Test
    void deleteById_Document() {
        searchClient.saveOrUpdate("books", book);

        OperationResult result = searchClient.delete("books", book);

        assertEquals(Status.Deleted, result.result());
    }

    @Test
    void deleteByQuery() {
        searchClient.saveOrUpdate("books", book);

        SearchRequest request = new SearchRequest();
        request.query("id:" + bookId);
        OperationResult result = searchClient.deleteByQuery("books", request);

        assertEquals(Status.Deleted, result.result());
    }

    @Test
    void deleteAll() {
        OperationResult result = searchClient.deleteAll("books");

        assertEquals(Status.Deleted, result.result());
    }

    @Test
    void exists() {
        searchClient.saveOrUpdate("books", book);

        boolean exists = searchClient.exists("books", bookId);

        assertTrue(exists);
    }

    @Test
    void existsByQuery() {
        searchClient.saveOrUpdate("books", book);

        SearchRequest request = new SearchRequest();
        request.query("id", bookId.toString());
        boolean exists = searchClient.existsByQuery("books", request);

        assertTrue(exists);
    }

    @Test
    void count() {
        searchClient.saveOrUpdate("books", book);

        long count = searchClient.count("books");

        assertTrue(count > 0);
    }

    @Test
    void countByQuery() {
        searchClient.saveOrUpdate("books", book);

        SearchRequest request = new SearchRequest();
        request.query("id:" + bookId);
        long count = searchClient.countByQuery("books", request);

        assertTrue(count > 0);
    }

    @Test
    void findById() {
        searchClient.saveOrUpdate("books", book);

        Book book = searchClient.findById("books", bookId, Book.class);

        assertNotNull(book);
        assertEquals("Michael Crichton", book.author());
    }

    @Test
    void findAll() {
        searchClient.saveOrUpdate("books", book);

        List<Book> books = searchClient.findAll("books", Book.class);

        assertNotNull(books);
        assertFalse(books.isEmpty());
    }

    @Test
    void fuzzySearch() {
        SearchRequest request = new SearchRequest();
        request.fuzzyQuery("sellers:Amazon").limit(10);

        SearchResult result = searchClient.search("books", request);

        List<Book> books = ObjectMapperUtil.to(result.documents(), Book.class);
        assertNotNull(books);
        assertFalse(books.isEmpty());
    }

    @Test
    void search() {
        searchClient.saveOrUpdate("books", book);
        SearchRequest request = new SearchRequest();
        request.query("*:*");

        SearchResult result = searchClient.search("books", request);

        assertNotNull(result.documents());
        assertFalse(result.documents().isEmpty());
    }

    @Test
    void search_With_Filters() {
        searchClient.saveOrUpdate("books", book);
        SearchRequest request = new SearchRequest();
        request.query("*:*")
                .limit(10)
                .addFacet(new TermFacet("authors", "author"));

        SearchResult result = searchClient.search("books", request);

        assertNotNull(result.documents());
        assertFalse(result.documents().isEmpty());
    }

    @Test
    void search_With_TermFacet() {
        searchClient.saveOrUpdate("books", book);
        SearchRequest request = new SearchRequest();
        request.query("*:*")
                .limit(10)
                .addFacet(new TermFacet("authors", "author"));

        SearchResult result = searchClient.search("books", request);

        assertNotNull(result.documents());
        assertFalse(result.documents().isEmpty());
    }

    @Test
    void search_With_NumericRangeFacet() {
        searchClient.saveOrUpdate("books", book);
        SearchRequest request = new SearchRequest();
        request.query("*:*")
                .limit(10)
                .addFacet(new NumericRangeFacet("priceRanges", "price",
                        List.of(new Range<>("0-5", 0f, 5f),
                                new Range<>("5-10", 5f, 10f),
                                new Range<>("10-15", 10f, 15f),
                                new Range<>("15-20", 15f, 20f))));

        SearchResult result = searchClient.search("books", request);

        assertNotNull(result.documents());
        assertFalse(result.documents().isEmpty());
    }

    @Test
    void search_With_HistogramFacet() {
        searchClient.saveOrUpdate("books", book);
        SearchRequest request = new SearchRequest();
        request.query("*:*")
                .limit(10)
                .addFacet(new HistogramFacet("priceIntervals", "price", 1.0));

        SearchResult result = searchClient.search("books", request);

        assertNotNull(result.documents());
        assertFalse(result.documents().isEmpty());
    }

    @Test
    void search_With_Highlight() {
        searchClient.saveOrUpdate("books", book);
        SearchRequest request = new SearchRequest();
        request.query("*:*")
                .highlight(new Highlight(List.of("title", "sellers"), "Park"))
                .limit(10);

        SearchResult result = searchClient.search("books", request);

        assertNotNull(result.highlights());
        assertFalse(result.highlights().isEmpty());
        System.out.println(result.highlights());
    }

    @Test
    void search_With_DateHistogramFacet() {
        searchClient.saveOrUpdate("books", book);
        SearchRequest request = new SearchRequest();
        request.query("*:*")
                .limit(10)
                .addFacet(new DateHistogramFacet("publishDates", "publishDate", ChronoField.MONTH_OF_YEAR));

        System.out.println(request);

        SearchResult result = searchClient.search("books", request);

        assertNotNull(result.documents());
        assertFalse(result.documents().isEmpty());
    }

    @Test
    @Disabled
    void search_With_DateRangeFacet() {
        searchClient.saveOrUpdate("books", book);
        SearchRequest request = new SearchRequest();
        request.query("*:*")
                .limit(10)
                .addFacet(new DateRangeFacet("publishDates", "publishDate",
                        List.of(
                                new Range<>("100y to now", "now-100y/d", "now+1d/d"),
                                new Range<>("50y to now", "now-50y/d", "now+1d/d"),
                                new Range<>("7d to now", "now-7d", "now+1d")
                        )
                ));

        SearchResult result = searchClient.search("books", request);

        assertNotNull(result.documents());
        assertFalse(result.documents().isEmpty());
   }
}