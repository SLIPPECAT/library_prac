package com.example.caching.search.repository;

import com.example.caching.search.entity.Book;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

//@NoRepositoryBean
public interface BookRepository extends ElasticsearchRepository<Book, Integer> {

//	Page<Book> findBooksByTitle(String query, Pageable pageable);

//	@Query("{\"bool\" : "
//			+ "{\"must\" : {\"match\" : {\"title_nm\" : \"?0\"}}, "
//			+ "\"should\" : [{\"term\" : {\"title_nm.keyword\" : \"?0\"}}, "
//			+ "{\"match_phrase\" : {\"title_nm\" : \"?0\"}}]}}")
	@Query("{\"bool\":{\"should\":[{\"match\":{\"title_nm\":{\"query\":\"?0\"}}}]}}")
	List<Book> findBooksByTitle(String query);


//	@Query("{\"bool\":{\"must\":[{\"match\":{\"title_nm\":\"?0\"}}]}}")

//	@Query("{\"bool\":{\"must\":[{\"match\":{\"title_nm\":\"?0\"}}],\"should\":[{\"term\":{\"title_nm\":\"?0\"}},{\"match_phrase\":{\"title_nm\":\"?0\"}}]}}")
//@Query("{\"bool\":{\"must\":{\"match\":{\"title_nm\":{\"query\":\"?0\",\"operator\":\"OR\"}}}}}")
//@Query("{\"bool\":{\"should\":[{\"match\":{\"title_nm\":{\"query\":\"?0\",\"operator\":\"or\"}}},{\"term\":{\"title_nm.keyword\":\"?0\"}},{\"match_phrase\":{\"title_nm\":{\"query\":\"?0\",\"slop\":10}}}]}}")
	@Query("{\"bool\":{\"should\":[{\"match\":{\"title_nm\":{\"query\":\"?0\"}}}]}}")
	List<Book> findBooksBySbst(String query);

//	Page<Book> findBooksByAuthor(String query, Pageable pageable);
}
