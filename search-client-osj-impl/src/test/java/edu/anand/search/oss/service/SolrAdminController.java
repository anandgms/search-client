@Controller
@RequestMapping("/admin")
public class SolrAdminController {

    @GetMapping("/query")
    public String queryPage(Model model) {

        model.addAttribute("query", new QueryForm());

        return "solr-query";
    }


    @PostMapping("/query")
    public String executeQuery(
            QueryForm query,
            Model model) {

        SearchResult result = searchService.execute(query);

        model.addAttribute("query", query);
        model.addAttribute("response", result);
        model.addAttribute("responseJson", result.getJson());
        model.addAttribute("debugJson", result.getDebugJson());

        return "solr-query";
    }

}