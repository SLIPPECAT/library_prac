package com.example.caching.search.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;


@Mapping(mappingPath = "elastic/library-setting.json")
@Setting(settingPath = "elastic/library-mapping.json")
@Getter
@Setter
@NoArgsConstructor
@Document(indexName = "books")
public class Book {

	@Id
	@Field(name = "seq_no")
	private Integer seqId;

	@Field(name = "title_nm")
	private String title;

	@Field(name = "title_sbst_nm")
	private String sbst;

	@Field(name = "book_intrcn_cn")
	private String content;

	@Field(name = "authr_nm")
	private String author;

	@Field(name = "isbn_thirteen_no")
	private String isbn;

	@Field(name = "image_url")
	private String bookImg;

	public Book(Integer seqId, String title, String content, String author, String isbn,
		String image_url, String sbst) {
		this.seqId = seqId;
		this.title = title;
		this.content = content;
		this.author = author;
		this.isbn = isbn;
		this.bookImg = image_url;
		this.sbst = sbst;
	}
}
