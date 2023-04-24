package com.example.caching.search.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDto3 {
	private String bookName;
	private String authors;
	private String publisher;
	private String publicationYear;
	private String bookImageURL;
	private String class_nm;
	private String class_no;
	private String isbn13;
}