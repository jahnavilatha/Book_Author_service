package com.Book.feignClient;

import com.Book.dto.Author;
import com.Book.model.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//no url as a.s is found at service registry
@FeignClient(name = "Author-Service",url = "http://localhost:8080")

public interface AuthorClient {
    @GetMapping("/authors/{id}")
    public Author getAuthorById(@PathVariable Long id);

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable Long id);

}
