package edu.anand.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QueryForm {

    // Search
    private String searchCore;    
    private String q = "*:*";
    private String fq;
    private int start = 0;
    private int rows = 10;
    private String sort;
    private String source;
    
    // DIH
    private String actionType = "lucene";
    private String[] cores;
    private String[] dihOptions;
    private String autoRefresh;
    private String[] queryOptions;
}