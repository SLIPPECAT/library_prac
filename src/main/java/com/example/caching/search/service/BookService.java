//package com.example.caching.search.service;
//
//import com.example.caching.search.entity.Book;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Map;
//import java.util.stream.Collectors;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQueryBuilder;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.elasticsearch.search.SearchHit;
//import org.springframework.data.elasticsearch.core.SearchHits;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class BookService {
//
//	private final ElasticsearchOperations elasticsearchOperations;
//
//	@Autowired
//	public BookService(ElasticsearchOperations elasticsearchOperations) {
//		this.elasticsearchOperations = elasticsearchOperations;
//	}
//
//	public List<Book> searchBooksByTitle(String query) {
//		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//		searchSourceBuilder.query(QueryBuilders.matchQuery("title", query));
//
//		SearchResponse searchResponse = elasticsearchOperations.search(searchSourceBuilder.build(), Book.class);
//
//		List<Book> books = new ArrayList<>();
//
//		for (SearchHit searchHit : searchResponse.getHits().getHits()) {
//			Book book = elasticsearchOperations.get(searchHit.getId(), Book.class);
//			books.add(book);
//		}
//
//		return books;
//	}
//}
//
