//package com.example.caching.search.controller;
//
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.Client;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//
//public class ElasticSearchExample {
//	private final Client client;
//
//	public ElasticSearchExample(Client client) {
//		this.client = client;
//	}
//
//	public void search() {
//		String searchText = "자바의정석";
//		QueryBuilder queryBuilder = QueryBuilders.matchQuery("text", searchText)
//			.analyzer("custom_analyzer")
//			.minimumShouldMatch("50%");
//
//		QueryBuilder customAnalyzer = QueryBuilders
//			.analyzer("custom_analyzer")
//			.tokenizer("standard")
//			.addTokenFilter("lowercase")
//			.addTokenFilter(
//				TokenFilterBuilders.nGramFilter("ngram_filter")
//					.minGram(3)
//					.maxGram(3)
//			);
//
//		SearchResponse response = client.prepareSearch("index_name")
//			.setQuery(queryBuilder)
//			.setAnalyzer(customAnalyzer)
//			.execute()
//			.actionGet();
//
//		SearchHits hits = response.getHits();
//		for (SearchHit hit : hits.getHits()) {
//			// search result processing
//			System.out.println(hit.getSourceAsString());
//		}
//	}
//}
