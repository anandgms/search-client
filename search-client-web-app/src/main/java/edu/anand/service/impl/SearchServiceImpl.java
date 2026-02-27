package edu.anand.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.anand.dto.QueryForm;
import edu.anand.dto.SearchResult;
import edu.anand.search.api.dto.NamedField;
import edu.anand.search.api.request.SearchRequest;
import edu.anand.search.api.service.SearchClient;
import edu.anand.search.api.util.ObjectMapperUtil;
import edu.anand.service.SearchService;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private SearchClient searchClient;

    @Override
    public SearchResult execute(QueryForm query) {

        SearchRequest request = new SearchRequest();
        request.query(query.getQ());
        request.addFilter(query.getFq());
        request.page(query.getStart());
        request.size(query.getRows());

        //System.out.println(query);
        //System.out.println(request);        

        edu.anand.search.api.result.SearchResult result = searchClient.search(query.getSearchCore(), request);
        //List<NamedField> docs = searchClient.findAll(query.getSearchCore(), NamedField.class);
        List<NamedField> docs = result.documents();

        SearchResult searchResult = new SearchResult();
        List<Map<String, Object>> docsAsMap = docs.stream().map(doc -> ObjectMapperUtil.asMap(doc)).toList();
        List<String> fields = docsAsMap.stream().flatMap(i -> i.keySet().stream()).distinct().toList();
   
        searchResult.setDocs(docsAsMap);
        searchResult.setFields(fields);
        searchResult.setJson(ObjectMapperUtil.asPrettyJson(docsAsMap));
    
        return searchResult;
    }
}