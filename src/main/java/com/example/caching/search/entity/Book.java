package com.example.caching.search.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "library")//다들 index이름이 달라서 새로 pull받으시면 indexName 수정하셔야합니다.~!
@Mapping(mappingPath = "elastic/library-setting.json")
@Setting(settingPath = "elastic/library-mapping.json")
public class Book {

	@Field(name = "id", type = FieldType.Integer)
	private Integer seqId;

	@Field(name = "title", type = FieldType.Text)
	private String title;

	@Field(name = "content", type = FieldType.Text)
	private String content;

	@Field(name = "author", type = FieldType.Keyword)
	private String author;

	@Field(name = "isbn", type = FieldType.Keyword)
	private String isbn;

	@Field(name = "bookImg", type = FieldType.Keyword)
	private String bookImg;

}

