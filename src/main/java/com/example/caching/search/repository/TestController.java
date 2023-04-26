package com.example.caching.search.repository;

import com.example.caching.elasticsearch.Timer;
import com.example.caching.search.entity.Book;
//import com.example.caching.search.repository.BookEsQueryRepository;
import com.example.caching.search.repository.BookEsQueryRepository;
import com.example.caching.search.repository.BookRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Controller
@RequiredArgsConstructor
public class TestController {

	private final BookRepository bookRepository;
	private final BookEsQueryRepository bookEsQueryRepository;

//	@GetMapping("/")
//	public String main() {
//		return "home";
//	}

//	@Timer
//	@GetMapping("/books/search")
//	public String searchBooks(@RequestParam(value="query") String query,
//		@RequestParam(value = "page", defaultValue = "1") int page, ModelMap model) {
//		Pageable pageable = PageRequest.of(0, 10);
//
//		Page<Book> books = bookRepository.findBooksByTitle(query, pageable);
//
//		Objects.requireNonNull(books);
//
//		List<BookDto> document = books.getContent().stream().map(BookDto::new).toList();
//
//		MetaDto meta
//			= new MetaDto(books.getTotalPages(), books.getTotalElements(), page, 10);
//
//		RespBooksDto searchResult = new RespBooksDto(meta, document);
//
//			model.put("searchResult", searchResult);
//			model.put("totalPages", searchResult.getMeta().getTotalPages());
//			model.put("size", searchResult.getMeta().getTotalElements());
//
//		return "searchResult";
//	}

	@Timer
	@GetMapping("/books/test")
	public List<Book> search(@RequestParam(value="query") String query){

//		List<Book> books = bookRepository.findBooksByTitle(query);
//		List<Book> books = bookRepository.findBooksBySbst(query);
		List<Book> books = bookEsQueryRepository.findBooksByTitle(query);
//		List<Book> books = bookEsQueryRepository.findByBookName(query);

		return books;
	}

//	@Timer
//	@GetMapping("/books/search")
//	public String searchBooks(@RequestParam(value="query") String query,
//		@RequestParam(value = "page", defaultValue = "1") int page, ModelMap model) {
//		Pageable pageable = PageRequest.of(0, 10);
//
//		Page<Book> books = bookRepository.findBooksByAuthor(query, pageable);
//
//		Objects.requireNonNull(books);
//
//		List<BookDto> document = books.getContent().stream().map(BookDto::new).toList();
//
//		MetaDto meta
//			= new MetaDto(books.getTotalPages(), books.getTotalElements(), page, 10);
//
//		RespBooksDto searchResult = new RespBooksDto(meta, document);
//
//		model.put("searchResult", searchResult);
//		model.put("totalPages", searchResult.getMeta().getTotalPages());
//		model.put("size", searchResult.getMeta().getTotalElements());
//
//		return "searchResult";
//	}



}