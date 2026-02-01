package edu.anand.search.oss.service;

import edu.anand.search.api.dto.Document;
import edu.anand.search.api.dto.NamedField;
import edu.anand.search.api.request.SearchRequest;
import edu.anand.search.api.result.OperationResult;
import edu.anand.search.api.result.SearchResult;
import edu.anand.search.api.result.Status;
import edu.anand.search.api.service.SearchClient;
import edu.anand.search.oss.util.OpenSearchRequestBuilder;
import edu.anand.search.oss.util.OpenSearchResponseBuilder;
import jakarta.annotation.Nonnull;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.opensearch._types.Refresh;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch.core.*;
import org.opensearch.client.opensearch.core.bulk.BulkOperation;
import org.opensearch.client.opensearch.core.bulk.DeleteOperation;
import org.opensearch.client.opensearch.core.bulk.IndexOperation;
import org.opensearch.client.opensearch.core.search.Hit;
import org.opensearch.client.transport.endpoints.BooleanResponse;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SearchClientImpl implements SearchClient {

    private final OpenSearchClient client;

    public SearchClientImpl(OpenSearchClient client) {
        this.client = client;
    }

    @Override
    public <T extends Document> OperationResult saveOrUpdate(String indexName, @Nonnull T document) {
        IndexRequest<T> request = new IndexRequest.Builder<T>().index(indexName)
                .id(document.docId())
                .document(document).build();
        try {
            IndexResponse response = client.index(request);
            return new OperationResult(Status.Created, 1, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T extends Document> OperationResult saveOrUpdate(String indexName, @Nonnull Collection<T> documents) {
        List<BulkOperation> indexOperations = new ArrayList<>(documents.size());
        documents.forEach(d -> indexOperations.add(
                new BulkOperation.Builder()
                        .index(IndexOperation.of(i -> i.index(indexName)
                                .id(d.docId())
                                .document(d))
                        ).build()));

        BulkRequest request = new BulkRequest.Builder().index(indexName)
                .operations(indexOperations)
                .refresh(Refresh.True)
                .build();

        try {
            BulkResponse response = client.bulk(request);
            return new OperationResult(Status.Created, documents.size(), "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OperationResult deleteById(String indexName, String id) {
        DeleteRequest request = new DeleteRequest.Builder().index(indexName).id(id).build();
        try {
            DeleteResponse response = client.delete(request);
            return new OperationResult(Status.Deleted, 1, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OperationResult deleteById(String indexName, Collection<String> ids) {
        List<BulkOperation> indexOperations = new ArrayList<>(ids.size());
        ids.forEach(id -> indexOperations.add(
                new BulkOperation.Builder()
                        .delete(DeleteOperation.of(i -> i.index(indexName).id(id)))
                        .build()));

        BulkRequest request = new BulkRequest.Builder().index(indexName)
                .operations(indexOperations)
                .refresh(Refresh.True)
                .build();

        try {
            BulkResponse response = client.bulk(request);
            return new OperationResult(Status.Deleted, ids.size(), "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OperationResult deleteByQuery(String indexName, SearchRequest request) {
        Query boolQuery = OpenSearchRequestBuilder.buildQueryAndFilters(request);
        return deleteByQuery(indexName, boolQuery);
    }

    @Override
    public OperationResult deleteAll(String indexName) {
        Query matchAllQuery = Query.of(q -> q.matchAll(m -> m));
        return deleteByQuery(indexName, matchAllQuery);
    }

    // Delete all documents that match the query
    private OperationResult deleteByQuery(String indexName, Query query) {
        DeleteByQueryRequest deleteRequest = DeleteByQueryRequest.of(d -> d
                .index(indexName)
                .query(query)
                .waitForCompletion(false)
        );

        try {
            DeleteByQueryResponse response = client.deleteByQuery(deleteRequest);
            long deletedCount = (response.deleted() == null) ? 0 : response.deleted();
            return new OperationResult(Status.Deleted, deletedCount, "");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean exists(String indexName, String id) {
        ExistsRequest request = new ExistsRequest.Builder().index(indexName).id(id).build();
        try {
            BooleanResponse response = client.exists(request);
            return response.value();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean existsByQuery(String indexName, SearchRequest request) {
        long count = countByQuery(indexName, request);
        return count > 0;
    }

    @Override
    public long count(String indexName) {
        CountRequest request = new CountRequest.Builder().index(indexName).build();
        try {
            CountResponse response = client.count(request);
            return response.count();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long countByQuery(String indexName, SearchRequest request) {
        Query boolQuery = OpenSearchRequestBuilder.buildQueryAndFilters(request);
        CountRequest countRequest = new CountRequest.Builder().index(indexName).query(boolQuery).build();
        CountResponse response = null;
        try {
            response = client.count(countRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.count();
    }

    @Override
    public <T> T findById(String indexName, String id, Class<T> clazz) {
        GetRequest request = new GetRequest.Builder().index(indexName).id(id).build();
        GetResponse<T> response;
        try {
            response = client.get(request, clazz);
            return response.source();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T> List<T> findAll(String indexName, Class<T> clazz) {
        Query matchAllQuery = Query.of(q -> q.matchAll(m -> m));
        org.opensearch.client.opensearch.core.SearchRequest searchRequest = OpenSearchRequestBuilder.buildRequest(indexName, matchAllQuery);
        SearchResponse<T> response;
        try {
            response = client.search(searchRequest, clazz);
            return response.hits().hits().stream().map(Hit::source).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SearchResult search(String indexName, SearchRequest request) {
        org.opensearch.client.opensearch.core.SearchRequest searchRequest = OpenSearchRequestBuilder.buildRequest(indexName, request);
        SearchResponse<NamedField> response;
        try {
            response = client.search(searchRequest, NamedField.class);
            return OpenSearchResponseBuilder.buildSearchResult(response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}