package edu.anand.search.rhlc.config;

import org.apache.hc.core5.http.HttpHost;
import org.opensearch.client.RestClient;
import org.opensearch.client.RestHighLevelClient;
import edu.anand.search.api.service.SearchClient;
import edu.anand.search.api.util.AbstractSearchClientBuilder;
import edu.anand.search.rhlc.service.SearchClientImpl;

public class SearchClientBuilder extends AbstractSearchClientBuilder {

    public RestHighLevelClient restHighLevelClient(String host) {
        return new RestHighLevelClient(
                RestClient.builder(new HttpHost("http", "localhost", 9200)));
    }

    @Override
    protected SearchClient createClient() {
        RestHighLevelClient restHighLevelClient = restHighLevelClient(host());
        return new SearchClientImpl(restHighLevelClient);
    }
}
