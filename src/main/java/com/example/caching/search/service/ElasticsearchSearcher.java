//package com.example.caching.search.service;
//
//import edu.stanford.nlp.pipeline.CoreDocument;
//import edu.stanford.nlp.pipeline.StanfordCoreNLP;
//import java.io.IOException;
//import java.util.Map;
//import java.util.stream.Collectors;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//
//public class ElasticsearchSearcher {
//
//	private final RestHighLevelClient client;
//
//	public ElasticsearchSearcher(RestHighLevelClient client) {
//		this.client = client;
//	}
//
//	public void search(String text) throws IOException {
//		// Stanford CoreNLP 라이브러리를 사용하여 입력 문장을 lemma로 변환
//		CoreDocument doc = new CoreDocument(text);
//		StanfordCoreNLP pipeline = new StanfordCoreNLP();
//		pipeline.annotate(doc);
//		String lemmaText = doc.sentences().get(0).lemmas().stream()
//			.collect(Collectors.joining(" "));
//
//		// Elasticsearch에서 lemma를 사용하여 검색
//		SearchRequest request = new SearchRequest("books");
//		SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
//		sourceBuilder.query(QueryBuilders.matchQuery("text", lemmaText));
//		request.source(sourceBuilder);
//		SearchResponse response = client.search(request, RequestOptions.DEFAULT);
//
//		// 검색 결과 처리
//		SearchHits hits = response.getHits();
//		for (SearchHit hit : hits) {
//			String id = hit.getId();
//			Map<String, Object> sourceAsMap = hit.getSourceAsMap();
//			System.out.println("id: " + id + ", text: " + sourceAsMap.get("text"));
//		}
//	}
//}
//
