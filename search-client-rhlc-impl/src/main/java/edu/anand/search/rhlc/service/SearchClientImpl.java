package edu.anand.search.rhlc.service;

import edu.anand.search.api.dto.Document;
import edu.anand.search.api.request.SearchRequest;
import edu.anand.search.api.result.OperationResult;
import edu.anand.search.api.result.SearchResult;
import edu.anand.search.api.result.Status;
import edu.anand.search.api.service.SearchClient;
import edu.anand.search.api.util.ObjectMapperUtil;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.action.index.IndexResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;

public class SearchClientImpl implements SearchClient {

  private final RestHighLevelClient restHighLevelClient;

  public SearchClientImpl(RestHighLevelClient restHighLevelClient) {
    this.restHighLevelClient = restHighLevelClient;
  }

  @Override
  public <T extends Document> OperationResult saveOrUpdate(String indexName, T document) {
    IndexRequest request =
        new IndexRequest(indexName).id(document.docId()).source(ObjectMapperUtil.asMap(document));
    try {
      IndexResponse response = restHighLevelClient.index(request, RequestOptions.DEFAULT);
      return new OperationResult(Status.Created, 1, "");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public <T extends Document> OperationResult saveOrUpdate(
      String indexName, Collection<T> documents) {
    return null;
  }

  @Override
  public long count(String indexName) {
    return 0;
  }

  @Override
  public long countByQuery(String indexName, SearchRequest request) {
    return 0;
  }

  @Override
  public boolean exists(String indexName, String id) {
    return false;
  }

  @Override
  public boolean existsByQuery(String indexName, SearchRequest request) {
    return false;
  }

  @Override
  public OperationResult deleteAll(String indexName) {
    return null;
  }

  @Override
  public OperationResult deleteById(String indexName, String id) {
    return null;
  }

  @Override
  public OperationResult deleteById(String indexName, Collection<String> ids) {
    return null;
  }

  @Override
  public OperationResult deleteByQuery(String indexName, SearchRequest request) {
    return null;
  }

  @Override
  public <T> T findById(String indexName, String id, Class<T> clazz) {
    return null;
  }

  @Override
  public <T> List<T> findAll(String indexName, Class<T> clazz) {
    return List.of();
  }

  @Override
  public SearchResult search(String indexName, SearchRequest request) {
    return null;
  }
}
