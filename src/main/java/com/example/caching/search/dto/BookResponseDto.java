package com.example.caching.search.dto;


import com.example.caching.search.entity.Books;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookResponseDto {
	private Long id;
	private String title;
	private String writer;
	private String publisher;
	private String vol;
	private String isbn;
	public static Page<BookResponseDto> toDtoList(Page<Books> postList){
		Page<BookResponseDto> ResponsePostList = postList.map(m -> BookResponseDto.builder()
			.title(m.getBookName())
			.writer(m.getAuthors())
			.publisher(m.getPublisher())
			.isbn(m.getIsbn13())
			.vol(m.getVol())
			.build()
		);
		return ResponsePostList;
	}
}
