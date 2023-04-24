package com.example.caching.elasticsearch;

import org.elasticsearch.client.RestHighLevelClient;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.erhlc.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.client.erhlc.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@EnableElasticsearchRepositories
//@Configuration
public class ElasticSearchConfig extends AbstractElasticsearchConfiguration {

	@Bean
	@Override
	public RestHighLevelClient elasticsearchClient() {
		ClientConfiguration clientConfiguration = ClientConfiguration.builder()
			.connectedTo("localhost:9200")
			.build();
		return RestClients.create(clientConfiguration).rest();
	}
	@Bean
	@Primary
	public ElasticsearchOperations elasticsearchTemplate1() {
		ElasticsearchRestTemplate elasticsearchRestTemplate = new ElasticsearchRestTemplate(elasticsearchClient());
		elasticsearchRestTemplate.setRefreshPolicy(RefreshPolicy.WAIT_UNTIL); // RefreshPolicy 설정

		return elasticsearchRestTemplate;
	}

}