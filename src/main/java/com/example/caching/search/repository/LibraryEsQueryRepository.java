package com.example.caching.search.repository;

import com.example.caching.search.entity.Book;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.collapse.CollapseBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQuery;
import org.springframework.data.elasticsearch.client.erhlc.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

@Repository
@Primary
@RequiredArgsConstructor
public class LibraryEsQueryRepository {

	private final ElasticsearchOperations operations;

	public Page<Book> findByBookName(String keyword, Pageable pageable) {
		CollapseBuilder collapseBuilder = new CollapseBuilder("isbn13");
		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
			.must(QueryBuilders.matchQuery("bookName", keyword)) // 문장이 완전 같지 않아도 검색
			.should(QueryBuilders.termQuery("bookName.keyword", keyword)) // 완전히 일치하는 문자열
			.should(QueryBuilders.matchPhraseQuery("bookName", keyword));

		NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
			.withQuery(boolQueryBuilder)
			.withPageable(pageable)
			.withCollapseBuilder(collapseBuilder)
			.build();

		SearchHits<Book> searchHits = operations.search(nativeSearchQuery, Book.class);
		List<SearchHit<Book>> searchHitList = searchHits.getSearchHits();
		List<Book> list = new ArrayList<>();
		for (SearchHit<Book> libraryEsSearchHit : searchHitList) {
			list.add(libraryEsSearchHit.getContent());
		}

		return new PageImpl<>(list, pageable, searchHits.getTotalHits());

	}
//
//	public List<LibraryEs> findByAuthors(String keyword) {
//		Pageable pageable = PageRequest.of(0, 1000);
//		MatchPhraseQueryBuilder matchQueryBuilder = QueryBuilders.matchPhraseQuery("authors", keyword);
//		NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
//			.withQuery(matchQueryBuilder)
//			.withPageable(pageable)
//			.build();
//		SearchHits<LibraryEs> search = operations.search(nativeSearchQuery, LibraryEs.class);
//		List<SearchHit<LibraryEs>> searchHitList = search.getSearchHits();
//		List<LibraryEs> list = new ArrayList<>();
//		for (SearchHit<LibraryEs> libraryEsSearchHit : searchHitList) {
//			list.add(libraryEsSearchHit.getContent());
//		}
//		return list;
//	}
//
//	public List<LibraryEs> findByAll(LibraryRequestDto requestDto) {
//		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
//		RangeQueryBuilder yearRangeQueryBuilder = QueryBuilders.rangeQuery("publicationYear");
//		RangeQueryBuilder genreRangeQueryBuilder = QueryBuilders.rangeQuery("class_num");
//
//		Pageable pageable = PageRequest.of(0, 10000);
//
//		if (requestDto.getBookName() != null) { //제목 상세 검색 쿼리 추가
//			boolQueryBuilder.must(QueryBuilders.matchQuery("bookName", requestDto.getBookName()).operator(
//				Operator.fromString("and")));
//			boolQueryBuilder.should(QueryBuilders.matchPhraseQuery("bookName", requestDto.getBookName()));
//		}
//		if (requestDto.getAuthors() != null) { //저자 상세 검색 쿼리 추가
//			boolQueryBuilder.must(QueryBuilders.matchQuery("authors", requestDto.getAuthors()));
//		}
//		if (requestDto.getPublisher() != null) { //출판사 상세 검색 쿼리 추가
//			boolQueryBuilder.must(QueryBuilders.matchQuery("publisher", requestDto.getPublisher()));
//		}
//		if (!requestDto.getGenre().equals("전체")) { //장르 상세 검색 쿼리 추가
//			int genreNum = Integer.parseInt(requestDto.getGenre());
//			boolQueryBuilder.filter(genreRangeQueryBuilder.gte(genreNum).lt(genreNum+100));
//		}
//		if (requestDto.getLibrary() != null) { //도서관 상세 검색 쿼리 추가
//			boolQueryBuilder.filter(QueryBuilders.matchPhraseQuery("libraryName", requestDto.getLibrary()));
//		}
//
//		//날짜 상세 검색 쿼리 추가
//		boolQueryBuilder.filter(yearRangeQueryBuilder.gte(requestDto.getFirstPublication()).lt(requestDto.getEndPublication()).format("yyyy"));
//
//		NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
//			.withQuery(boolQueryBuilder)
//			.withPageable(pageable)
//			.build();
//
//		SearchHits<LibraryEs> search = operations.search(nativeSearchQuery, LibraryEs.class);
//		List<SearchHit<LibraryEs>> searchHitList = search.getSearchHits();
//		List<LibraryEs> list = new ArrayList<>();
//		for (SearchHit<LibraryEs> libraryEsSearchHit : searchHitList) {
//			list.add(libraryEsSearchHit.getContent());
//		}
//		return list;
//	}
//
//	public List<LibraryEs> autocomplete_book(String keyword) {
//
//		Pageable pageable = PageRequest.of(0, 20);
//		PrefixQueryBuilder prefixQueryBuilder = QueryBuilders.prefixQuery("bookName.keyword", keyword);
//
//		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
//			.should(prefixQueryBuilder);
//		NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
//			.withQuery(boolQueryBuilder)
//			.withPageable(pageable)
//			.withCollapseField("isbn13")
//			.build();
//		SearchHits<LibraryEs> search = operations.search(nativeSearchQuery, LibraryEs.class);
//		String json = nativeSearchQuery.getQuery().toString();
//		System.out.println(json);
//		List<SearchHit<LibraryEs>> searchHitList = search.getSearchHits();
//		List<LibraryEs> list = new ArrayList<>();
//		for (SearchHit<LibraryEs> libraryEsSearchHit : searchHitList) {
//			list.add(libraryEsSearchHit.getContent());
//		}
//		return list;
//	}
//
//	public List<LibraryEs> autocomplete_book2(String keyword) {
//		Pageable pageable = PageRequest.of(0, 10);
//		BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
//			.should(QueryBuilders.matchPhraseQuery("bookName", keyword));
//		NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
//			.withQuery(boolQueryBuilder)
//			.withCollapseField("isbn13")
//			.withPageable(pageable)
//			.build();
//		SearchHits<LibraryEs> search = operations.search(nativeSearchQuery, LibraryEs.class);
//		List<SearchHit<LibraryEs>> searchHitList = search.getSearchHits();
//		List<LibraryEs> list = new ArrayList<>();
//		for (SearchHit<LibraryEs> libraryEsSearchHit : searchHitList) {
//			list.add(libraryEsSearchHit.getContent());
//		}
//		for (LibraryEs libraryEs : list) {
//			libraryEs.getLibraryName();
//		}
//		return list;
//	}
//	public List<LibraryEs> findByIsbn13One(List<String> Isbn13List) {
//		List<LibraryEs> list = new ArrayList<>();
//		for (String isbn : Isbn13List) {
//			MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("isbn13",isbn);
//			Pageable pageable = PageRequest.of(0, 1);//하나만 저장을 해서 page = 1
//			NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
//				.withQuery(matchQueryBuilder)
//				.withPageable(pageable)
//				.build();
//			SearchHits<LibraryEs> search = operations.search(nativeSearchQuery, LibraryEs.class);
//			List<SearchHit<LibraryEs>> searchHitList = search.getSearchHits();
//
//			for (SearchHit<LibraryEs> libraryEsSearchHit : searchHitList) {
//				list.add(libraryEsSearchHit.getContent());
//			}
//		}
//		return list;
//	}
}
