package com.example.caching.search.util;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;

import java.io.IOException;

public class ElasticsearchIndexSettingsUpdater {

	public static void main(String[] args) throws IOException {
		updateIndexSettings("books");
	}

	public static void updateIndexSettings(String indexName) throws IOException {
		// 1. Elasticsearch에 접속
		RestHighLevelClient client = new RestHighLevelClient(
			RestClient.builder(new HttpHost("localhost", 9200, "http")));

		// 2. 색인의 설정을 변경하고자 하는 클러스터 노드를 선택
		// 이 예제에서는 로컬에서 실행 중인 Elasticsearch에 연결

		// 3. API 호출을 실행하여 색인의 설정을 변경
		UpdateSettingsRequest request = new UpdateSettingsRequest(indexName);
		request.settings(
			Settings.builder()
				.put("index.analysis.tokenizer.my_ngram_tokenizer.type", "ngram")
				.put("index.analysis.tokenizer.my_ngram_tokenizer.min_gram", "2")
				.put("index.analysis.tokenizer.my_ngram_tokenizer.max_gram", "3")
				.put("index.analysis.analyzer.my_analyzer.type", "custom")
				.put("index.analysis.analyzer.my_analyzer.tokenizer", "my_ngram_tokenizer")
				.build()
		);

		client.indices().putSettings(request, RequestOptions.DEFAULT);

		// 4. Elasticsearch와의 연결 해제
		client.close();
	}
}
