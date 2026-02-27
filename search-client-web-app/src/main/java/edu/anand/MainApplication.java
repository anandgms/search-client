package edu.anand;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import edu.anand.search.api.service.SearchClient;
import edu.anand.search.oss.config.SearchClientBuilder;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Bean
    public SearchClient SearchClient(){
        return new SearchClientBuilder()
            .host("localhost")
            .build();
    }
}