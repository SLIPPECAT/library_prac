//package com.example.caching;
//
//import org.apache.http.HttpHost;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.text.Text;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
//import org.elasticsearch.search.sort.FieldSortBuilder;
//import org.elasticsearch.search.sort.SortOrder;
//import org.elasticsearch.search.suggest.Suggest;
//import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
//import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
//import org.elasticsearch.search.suggest.term.TermSuggestion;
//import org.elasticsearch.search.suggest.term.TermSuggestionBuilder;
//
//import java.io.IOException;
//import java.util.Map;
//
//public class ElasticsearchLemmatizerSearchTester {
//
//	public static void main(String[] args) throws IOException {
//		// Elasticsearch에 접속
//		RestHighLevelClient client = new RestHighLevelClient(
//			RestClient.builder(new HttpHost("localhost", 9200, "http")));
//
//		// 분석 대상 문장
//		String sentence = "springboot is an open source framework for java";
//		// 문장을 Lemmatization
//		String lemmatizedSentence = Lemmatizer.lemmatize(sentence, "en");
//
//		// Elasticsearch에서 검색
//		SearchRequest searchRequest = new SearchRequest("books");
//		searchRequest.source().query(QueryBuilders.matchQuery("title", lemmatizedSentence));
//		searchRequest.source().sort(new FieldSortBuilder("published_date").order(SortOrder.DESC));
//		searchRequest.source().highlighter(new HighlightBuilder().field("title").numOfFragments(0).preTags("<b>").postTags("</b>"));
//		searchRequest.source().suggest(new CompletionSuggestionBuilder("title_suggestion")
//			.prefix(lemmatizedSentence)
//			.skipDuplicates(true)
//			.size(10)
//			.field("title_suggest"));
//		searchRequest.source().suggest(new TermSuggestionBuilder("author_suggestion")
//			.text(lemmatizedSentence)
//			.field("author_suggest")
//			.size(10));
//
//		SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
//
//		// 검색 결과 출력
//		SearchHits hits = response.getHits();
//		for (SearchHit hit : hits) {
//			Map<String, Object> sourceAsMap = hit.getSourceAsMap();
//			String title = (String) sourceAsMap.get("title");
//			String author = (String) sourceAsMap.get("author");
//			String summary = (String) sourceAsMap.get("summary");
//
//			System.out.println("Title: " + title);
//			System.out.println("Author: " + author);
//			System.out.println("Summary: " + summary);
//
//			Map<String, HighlightField> highlightFields = hit.getHighlightFields();
//			HighlightField titleHighlightField = highlightFields.get("title");
//			Text[] fragments = titleHighlightField.fragments();
//			String highlightedTitle = "";
//			for (Text fragment : fragments) {
//				highlightedTitle += fragment;
//			}
//			System.out.println("Highlighted Title: " + highlightedTitle);
//		}
//
//		// 자동 완성 결과 출력
//		Suggest suggest = response.getSuggest();
//		CompletionSuggestion titleSuggestion = suggest.getSuggestion("title_suggestion");
//		for (CompletionSuggestion.Entry entry : titleSuggestion.getEntries()) {
//			for (CompletionSuggestion.Entry.Option option : entry) {
//				System.out.println("Title suggestion:
//
