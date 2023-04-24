package com.example.caching.search.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LibraryRequestDto {

	private String bookName;
	private String authors;
	private String publisher;
	private String firstPublication;
	private String endPublication;
	private String genre;
	private String library;

}
