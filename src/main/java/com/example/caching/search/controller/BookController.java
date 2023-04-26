//package com.example.caching.search.controller;
//
//import com.example.caching.search.dto.BookResponseDto;
//import com.example.caching.search.dto.BookResponseDto2;
//import com.example.caching.search.dto.BookResponseDto3;
//import com.example.caching.search.dto.LibraryRequestDto;
//import com.example.caching.search.dto.RespBooksDto;
//import com.example.caching.search.entity.BooksReview;
//import com.example.caching.search.service.BookService;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.util.List;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@RequiredArgsConstructor
//@Controller
//public class BookController {
//	private final BookService bookService;
//
//	@GetMapping("/")
//	public String main() {
//		return "home";
//	}
//
//	@GetMapping("/search")
//	public String getBook(@RequestParam(value = "query") String query,
//		@RequestParam(value = "page", defaultValue = "1") int page,
//		@RequestParam(value = "size", defaultValue = "10") int size, ModelMap model) {
//
//		if (!query.isEmpty()) {
//			RespBooksDto searchResult = bookService.getBook(query, page, size);
//			model.put("searchResult", searchResult);
//			model.put("totalPages", searchResult.getMeta().getTotalPages());
//			model.put("size", searchResult.getMeta().getTotalElements());
//		}
//
//		return "searchResult";
//	}
//}
//
//
//
////	int startIndex;
////	int endIndex;
////	long startCount = (page - 1) * 30 + 1;
////	long endCount = startCount + 30 - 1;
////		model.addAttribute("startCount", startCount);
////			if (page / 10 < 1) {
////	startIndex = 1;
////	endIndex = 10;
////	if (endIndex >= bookList.getTotalPages())
////	endIndex = bookList.getTotalPages();
////	} else {
////	if (page % 10 == 0) {
////	page -= 1;
////	}
////	startIndex = page / 10 * 10 + 1;
////	endIndex = startIndex + 9;
////	if (endIndex >= bookList.getTotalPages())
////	endIndex = bookList.getTotalPages();
////	}
////
////	model.addAttribute("startIndex", startIndex);
////	model.addAttribute("endIndex", endIndex);
////
////
////	if (endCount > bookList.getTotalElements())
////	model.addAttribute("endCount", bookList.getTotalElements());
////	else
////	model.addAttribute("endCount", endCount);
////
////
////	model.addAttribute("bookList", bookList);
////	model.addAttribute("keyword", keyword);
////
////	model.addAttribute("page_url","search");