package com.example.caching.search.repository;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import com.example.caching.search.entity.Book;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.collapse.CollapseBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;


import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ReactiveElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.elasticsearch.index.query.QueryBuilders;

import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Repository;

@Primary
@RequiredArgsConstructor
@Repository
public class BookEsQueryRepository {

	private final ElasticsearchOperations operations;


	public List<Book> findBooksByTitle(String query) {
		System.out.println(
			"키워드 11111"
		);
		CollapseBuilder collapseBuilder = new CollapseBuilder("title_nm");
//		Pageable pageable = PageRequest.of(0, 1000);
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
			.must(QueryBuilders.matchQuery("title_nm", query))//문장이 완전 같지 않아도 검색
			.should(QueryBuilders.matchQuery("title_nm", query))//문장이 완전 같지 않아도 검색
			.should(QueryBuilders.termQuery("title_nm.keyword", query))//완전히 일치하는 문자열
			.should(QueryBuilders.matchPhraseQuery("title_nm", query));

		NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
			.withQuery(boolQueryBuilder)
			.withQuery(matchQuery("title_nm", query))
//			.withPageable(pageable)
//			.withCollapseField(String.valueOf(collapseBuilder))
			.build();

		SearchHits<Book> search = operations.search(nativeSearchQuery, Book.class);
		System.out.println(search);
		List<SearchHit<Book>> searchHitList = search.getSearchHits();
		List<Book> list = new ArrayList<>();
		for (SearchHit<Book> libraryEsSearchHit : searchHitList) {
			list.add(libraryEsSearchHit.getContent());
		}
		return list;
	}
}