//package com.example.caching;
//
//import com.example.caching.search.entity.Book;
//import com.example.caching.search.repository.BookRepository;
//import java.util.List;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class BookRepositoryTest {
//
//	BookRepository bookRepository;
//
//	@Test
//	@DisplayName("책 제목으로 데이터 찾아오기")
//	void find_book_by_title(){
//		//given
//		String title1 = "토비의 스프링";
//		String title2 = "자바의 정석";
//
//		//when
//		List<Book> bookList = bookRepository.findByTitleName("title1");
//
//		//then
//		for (Book book : bookList) {
//			System.out.println(book.getTitle_nm());
//		}
//
//	}
//
//}
