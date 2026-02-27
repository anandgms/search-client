package edu.anand.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import edu.anand.dto.QueryForm;
import edu.anand.dto.SearchResult;
import edu.anand.service.SearchService;

@Controller
@RequestMapping("/solr")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("/query")
    public String queryPage(Model model) {
        model.addAttribute("query", new QueryForm());
        return "solr-query";
    }


    @PostMapping("/run-query")
    public String executeQuery(QueryForm query, Model model) {

        SearchResult result = searchService.execute(query);

        model.addAttribute("query", query);
        model.addAttribute("response", result);
        model.addAttribute("responseJson", result.getJson());
        model.addAttribute("debugJson", result.getDebugJson());

        return "solr-query";
    }
}