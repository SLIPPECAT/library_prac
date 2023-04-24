//package com.example.caching.search.controller;
//
//import com.example.caching.search.dto.RespBooksDto;
//import com.example.caching.search.service.BookSearchService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@RequiredArgsConstructor
//@Controller
//public class SearchViewController {
//
//    private final BookSearchService searchService;
//
//    @GetMapping("/")
//    public String home() {
//        return "home";
//    }
//
//    @GetMapping("/books/search")
//    public String SearchBook(@RequestParam(value = "query", required = false) String query,
//        @RequestParam(value = "page", defaultValue = "1") int page,
//        @RequestParam(value = "size", defaultValue = "10") int size,
//        @RequestParam(value = "target", defaultValue = "title") String target, ModelMap model) {
//
//        if (!query.isEmpty()) {
//            RespBooksDto searchResult = searchService.searchBooks(query, page, size, target);
//            model.put("searchResult", searchResult);
//            model.put("totalPages", searchResult.getMeta().getTotalPages());
//            model.put("size", searchResult.getMeta().getTotalElements());
//
//        }
//
//        return "searchResult";
//    }
//}
