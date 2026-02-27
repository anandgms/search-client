package edu.anand.service;


import edu.anand.dto.QueryForm;
import edu.anand.dto.SearchResult;

public interface SearchService {

    SearchResult execute(QueryForm query);

}
