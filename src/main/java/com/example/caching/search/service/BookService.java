package com.example.caching.search.service;

import com.example.caching.search.dto.BookDto;
import com.example.caching.search.dto.MetaDto;
import com.example.caching.search.dto.RespBooksDto;
import com.example.caching.search.entity.Book;
import com.example.caching.search.repository.LibraryEsQueryRepository;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

	private final LibraryEsQueryRepository libraryEsQueryRepository;

	@Transactional
	public RespBooksDto getBook(String keyword, int page, int size) {

		Pageable pageable = PageRequest.of(-1, 10);

		Page<Book> books = libraryEsQueryRepository.findByBookName(keyword, pageable);

		Objects.requireNonNull(books);

		List<BookDto> document = books.getContent().stream().map(BookDto::new).toList();

		MetaDto meta
			= new MetaDto(books.getTotalPages(), books.getTotalElements(), page, size);

		return new RespBooksDto(meta, document);

	}

//	@Transactional
//	public List<String> autocomplete_book(String keyword) {
//		List<LibraryEs> list = libraryEsQueryRepository.autocomplete_book(keyword);
//		list.addAll(libraryEsQueryRepository.autocomplete_book2(keyword));
//		list = deduplication((ArrayList<LibraryEs>) list, LibraryEs::getBookName);//책제목 으로 중복제거
//		List<String> bookNames = new ArrayList<>();
//		for (LibraryEs libraryEs : list) {
//			bookNames.add(libraryEs.getBookName());
//		}
//		return bookNames;
//	}
//
//	public <T> List<T> deduplication(ArrayList<T> list, Function<? super T, ?> key) {
//		return list.stream().filter(deduplication(key)).collect(Collectors.toList());
//	}
//
//	public <T> Predicate<T> deduplication(Function<? super T, ?> key) {
//		Set<Object> set = ConcurrentHashMap.newKeySet();
//		return predicate -> set.add(key.apply(predicate));
//	}
}