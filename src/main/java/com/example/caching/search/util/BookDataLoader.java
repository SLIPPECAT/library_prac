package com.example.caching.search.util;

import com.example.caching.search.entity.Book;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.Strings;
import org.elasticsearch.xcontent.XContentBuilder;
import org.elasticsearch.xcontent.XContentFactory;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.stereotype.Component;


public class BookDataLoader {

	private final String url;
	private final String username;
	private final String password;

	public BookDataLoader(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public void loadDataWithPaging(int pageSize) throws IOException, SQLException {
		// MySQL 데이터베이스 연결
		try (Connection conn = DriverManager.getConnection(url, username, password)) {
			// 총 데이터 개수 조회
			String countSql = "SELECT COUNT(*) FROM books";
			try (PreparedStatement countStmt = conn.prepareStatement(countSql)) {
				try (ResultSet countRs = countStmt.executeQuery()) {
					if (countRs.next()) {
						int totalCount = countRs.getInt(1);

						// 페이지 단위로 데이터 조회
						ExecutorService executor = Executors.newFixedThreadPool(3); // 쓰레드 풀 생성

						List<Future<Void>> futures = new ArrayList<>(); // Future 객체를 저장할 리스트 생성

						for (int i = 0; i < totalCount; i += pageSize) {
							String sql = "SELECT * FROM books LIMIT ?, ?";
							try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
								pstmt.setInt(1, i);
								pstmt.setInt(2, pageSize);
								try (ResultSet rs = pstmt.executeQuery()) {
									// Elasticsearch에 데이터 색인
									RestHighLevelClient client = new RestHighLevelClient(
										RestClient.builder(HttpHost.create("http://localhost:9200")));


									BulkRequest bulkRequest = new BulkRequest();

									while (rs.next()) {
										Book book = new Book();
										book.setSeqId(rs.getInt("seq_no"));
										book.setTitle(rs.getString("title_nm"));
										book.setContent(rs.getString("book_intrcn_cn"));
										book.setAuthor(rs.getString("authr_nm"));
										book.setIsbn(rs.getString("isbn_thirteen_no"));
										book.setBookImg(rs.getString("image_url"));
										book.setSbst(rs.getString("title_sbst_nm"));

										// IndexRequest 객체 생성 시에 고유 ID를 지정합니다.
										IndexRequest indexRequest = new IndexRequest("books")
											.id(String.valueOf(book.getSeqId())); // seq_no 컬럼을 고유 ID로 사용
										indexRequest.source(getJsonString(book), XContentType.JSON);
										bulkRequest.add(indexRequest);
									}

									Future<Void> future = executor.submit(() -> {
										BulkResponse bulkResponse = client.bulk(bulkRequest, RequestOptions.DEFAULT);

										if (bulkResponse.hasFailures()) {
											// 처리 중 실패한 문서에 대한 정보 출력
											for (BulkItemResponse bulkItemResponse : bulkResponse) {
												if (bulkItemResponse.isFailed()) {
													BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
													System.err.println("Failed to index document: " + failure.getMessage());
												}
											}
										} else {
											System.out.println("Data has been loaded successfully.");
										}

										client.close();

										return null;
									});

									futures.add(future);
								}
							}
						}
						// 모든 쓰레드가 종료될 때까지 대기
						for (Future<Void> future : futures) {
							try {
								future.get();
							} catch (InterruptedException | ExecutionException e) {
								e.printStackTrace();
							}
						}
						executor.shutdown(); // 쓰레드 풀 종료
					}
				}
			}
		}
	}

	private static String getJsonString(Book book) throws IOException {
		XContentBuilder builder = XContentFactory.jsonBuilder();
		builder.startObject();
		builder.field("seq_no", book.getSeqId());
		builder.field("title_nm", book.getTitle());
		builder.field("book_intrcn_cn", book.getContent());
		builder.field("authr_nm", book.getAuthor());
		builder.field("isbn_thirteen_no", book.getIsbn());
		builder.field("image_url", book.getBookImg());
		builder.field("title_sbst_nm", book.getSbst());
		builder.endObject();
		return Strings.toString(builder);
	}

}
