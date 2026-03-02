package edu.anand.config;

import edu.anand.search.api.service.SearchClient;
import edu.anand.search.oss.config.SearchClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SearchClientConfig {

  @Bean
  public SearchClient SearchClient() {
    return new SearchClientBuilder().host("localhost").build();
  }
}
