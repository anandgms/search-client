package edu.anand.search.api.service;

import edu.anand.search.api.dto.Document;
import edu.anand.search.api.request.FuzzyRequest;
import edu.anand.search.api.request.SearchRequest;
import edu.anand.search.api.result.OperationResult;
import edu.anand.search.api.result.SearchResult;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface SearchClient {

    /**
     * Saves a document to the specified index. If document already exists, it is overwritten.
     */
    <T extends Document> OperationResult saveOrUpdate(String indexName, T document);

    /**
     * Bulk inserts a collection of documents to the specified index. If a document in the collections
     * already exists, it is overwritten.
     */
    <T extends Document> OperationResult saveOrUpdate(String indexName, Collection<T> documents);

    /**
     * Counts the total number of documents in the specified index.
     */
    long count(String indexName);

    /**
     * Counts the total number of documents in the specified index that matches the search criteria.
     */
    long countByQuery(String indexName, SearchRequest request);

    /**
     * Returns true if document identified by its ID field exists in the specified index.
     */
    boolean exists(String indexName, String id);

    /**
     * Returns true if document identified by its ID field exists in the specified index.
     */
    default boolean exists(String indexName, UUID id){
        return exists(indexName, id.toString());
    }

    /**
     * Returns true if document identified by its ID field is not found in the specified index.
     */
    default boolean notExistsByQuery(String indexName, String id) {
        return !exists(indexName, id);
    }

    /**
     * Returns true if document identified by its ID field is not found in the specified index.
     */
    default boolean notExistsByQuery(String indexName, UUID id) {
        return !exists(indexName, id.toString());
    }

    /**
     * Returns true if there is one or more documents that match the search criteria in the specified index.
     */
    boolean existsByQuery(String indexName, SearchRequest request);

    /**
     * Returns true if the search criteria does not match any document in the specified index.
     */
    default boolean notExistsByQuery(String indexName, SearchRequest request) {
        return !existsByQuery(indexName, request);
    }

    /**
     * Bulk deletes all the documents from the specified index.
     */
    OperationResult deleteAll(String indexName);

    /**
     * Deletes the document identified by its ID field from the specified index.
     */
    OperationResult deleteById(String indexName, String id);

    /**
     * Deletes the document identified by its ID field from the specified index.
     */
    default OperationResult deleteById(String indexName, UUID uuid){
        return deleteById(indexName, uuid.toString());
    }

    /**
     * Deletes all the documents identified by its ID field from the specified index.
     */
    OperationResult deleteById(String indexName, Collection<String> ids);

    /**
     * Deletes the given document from the specified index.
     */
    default <T extends Document> OperationResult delete(String indexName, T document){
        return deleteById(indexName, document.getId());
    }

    /**
     * Deletes a collection of documents from the specified index.
     */
    default <T extends Document> OperationResult delete(String indexName, Collection<T> documents){
        return deleteById(indexName, documents.stream().map(Document::getId).toList());
    }

    /**
     * Deletes all documents that match the search criteria from the specified index.
     */
    OperationResult deleteByQuery(String indexName, SearchRequest request);

    /**
     * Finds a document identified by its ID field from the specified index.
     */
    <T> T findById(String indexName, String id, Class<T> clazz);

    /**
     * Finds a document identified by its ID field from the specified index.
     */
    default <T> T findById(String indexName, UUID id, Class<T> clazz){
        return findById(indexName, id.toString(), clazz);
    }

    /**
     * Finds all documents from the specified index. Suitable for small indices, as this
     * method does not support pagination.
     */
    <T> List<T> findAll(String indexName, Class<T> clazz);

    /**
     * Finds all documents that match the search criteria from the specified index.
     * Pagination is controlled using the page and row properties in the SearchRequest.
     */
    SearchResult search(String indexName, SearchRequest request);

    /**
     * Finds documents where a given field matches the search term. Case-insensitive and allows
     * minor spelling errors in the search term. Intended for suggest and auto-completion.
     */
    SearchResult fuzzySearch(String indexName, FuzzyRequest request);
}
