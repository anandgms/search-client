package edu.anand.search.oss.config;

import edu.anand.search.api.util.ObjectMapperUtil;
import org.apache.hc.core5.http.HttpHost;
import org.opensearch.client.json.jackson.JacksonJsonpMapper;
import org.opensearch.client.opensearch.OpenSearchClient;
import org.opensearch.client.transport.OpenSearchTransport;
import org.opensearch.client.transport.httpclient5.ApacheHttpClient5TransportBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {

    @Bean
    public OpenSearchClient openSearchClient() {
        // Create the transport with a Jackson mapper (or another mapper)
        OpenSearchTransport transport = ApacheHttpClient5TransportBuilder
                .builder(new HttpHost("http", "localhost", 9200))
                .setMapper(new JacksonJsonpMapper(ObjectMapperUtil.objectMapper()))
                .build();

        return new OpenSearchClient(transport);
    }
}
