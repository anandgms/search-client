package edu.anand.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import edu.anand.search.api.service.SearchClient;
import edu.anand.search.oss.config.SearchClientBuilder;

@Configuration
public class SearchClientConfig {

    @Bean
    public SearchClient SearchClient(){
        return new SearchClientBuilder()
            .host("localhost")
            .build();
    }
}
