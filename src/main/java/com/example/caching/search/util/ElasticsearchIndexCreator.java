package com.example.caching.search.util;

import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.settings.put.UpdateSettingsRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentFactory;

public class ElasticsearchIndexCreator {

	public static void main(String[] args) throws IOException {

		RestHighLevelClient client = new RestHighLevelClient(
			RestClient.builder(new HttpHost("localhost", 9200, "http")));

		// 인덱스 생성
		CreateIndexRequest request = new CreateIndexRequest("books");
		// 샤드, 레플리카 수 조정
		request.settings(Settings.builder()
			.put("index.number_of_shards", 6)
			.put("index.number_of_replicas", 1)
		);

		XContentBuilder settingsBuilder = XContentFactory.jsonBuilder()
			.startObject()
				.startObject("analysis")
					.startObject("analyzer")
						.startObject("ngram_analyzer")
						.field("tokenizer", "ngram_tokenizer")
						.endObject()
					.endObject()
					.startObject("tokenizer")
						.startObject("ngram_tokenizer")
							.field("type", "ngram")
							.field("min_gram", 1)
							.field("max_gram", 2)
							.startArray("token_chars")
								.value("letter")
								.value("digit")
							.endArray()
						.endObject()
					.endObject()
				.endObject()
			.endObject();

		request.settings(settingsBuilder);

		XContentBuilder mappingBuilder = XContentFactory.jsonBuilder()
			.startObject()
				.startObject("properties")
					.startObject("seq_no")
						.field("type", "integer")
					.endObject()
					.startObject("title_nm")
						.field("type", "text")
						.field("analyzer", "ngram_analyzer")
						.startObject("fields")
							.startObject("keyword")
								.field("type", "keyword")
								.field("ignore_above", 256)
							.endObject()
							.startObject("ngram_analyzer")
								.field("type", "text")
								.field("analyzer", "ngram_analyzer")
							.endObject()
						.endObject()
					.endObject()
					.startObject("book_intrcn_cn")
						.field("type", "text")
					.endObject().
					startObject("authr_nm")
						.field("type", "text")
					.endObject()
					.startObject("isbn_thirteen_no")
						.field("type", "keyword")
					.endObject()
					.startObject("image_url")
						.field("type", "keyword")
					.endObject()
					.startObject("title_sbst_nm")
						.field("type", "text")
					.endObject()
				.endObject()
			.endObject();

		request.mapping(mappingBuilder.toString());

		client.indices().create(request, RequestOptions.DEFAULT);

		client.close();
	}
}
//			.startObject()
//				.startObject("analysis")
//				.startObject("analyzer")
//				.startObject("ngram_analyzer")
//				.field("tokenizer", "ngram_analyzer")
//				.endObject()
//				.startObject("english_analyzer")
//				.field("tokenizer", "whitespace")
//				.field("filter", new String[]{"lowercase", "porter_stem", "snowball"})
//				.endObject()
//				.endObject()
//				.startObject("tokenizer")
////						.startObject("nori_tokenizer")
////							.field("type", "nori_tokenizer")
////						.endObject()
//				.startObject("ngram_tokenizer")
//				.field("type", "ngram")
//				.field("min_gram", 2)
//				.field("max_gram", 3)
//				.startArray("token_chars")
//				.value("letter")
//				.value("digit")
//				.endArray()
//				.endObject()
//				.endObject()
//				.endObject()
//				.endObject();


//		XContentBuilder mappingBuilder = XContentFactory.jsonBuilder();
//		mappingBuilder.startObject();
//		{
//			mappingBuilder.startObject("properties");
//			{
//				mappingBuilder.startObject("seq_nm");
//				{
//					mappingBuilder.field("type", "integer");
//				}
//				mappingBuilder.endObject();
//				mappingBuilder.startObject("title_nm");
//				{
//					mappingBuilder.field("type", "text");
//				}
//				mappingBuilder.endObject();
//				mappingBuilder.startObject("book_intrcn_cn");
//				{
//					mappingBuilder.field("type", "text");
//				}
//				mappingBuilder.endObject();
//				mappingBuilder.startObject("authr_nm");
//				{
//					mappingBuilder.field("type", "keyword");
//				}
//				mappingBuilder.endObject();
//				mappingBuilder.startObject("isbn_thirteen_no");
//				{
//					mappingBuilder.field("type", "keyword");
//				}
//				mappingBuilder.endObject();
//				mappingBuilder.startObject("image_url");
//				{
//					mappingBuilder.field("type", "keyword");
//				}
//				mappingBuilder.endObject();
//			}
//			mappingBuilder.endObject();
//		}
//		mappingBuilder.endObject();