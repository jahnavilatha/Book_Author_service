package com.Book.controller;

import com.Book.dto.Author;
import com.Book.dto.BookDTO;
import com.Book.feignClient.AuthorClient;
import com.Book.model.Book;
import com.Book.repository.BookRepository;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BookController {

    @Autowired
    public BookRepository bookRepository;

    @Autowired
    private AuthorClient authorClient;

    @GetMapping("/books")
    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(this::covertToBookDTO)
                .collect(Collectors.toList());
    }

    private BookDTO covertToBookDTO(Book book) {
        Author author = authorClient.getAuthorById(book.getAuthorId());
        return new BookDTO(book.getId(), book.getTitle(), author);

    }


    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        try{
            Book book = bookRepository.findById(id);
            if (book != null) {
            BookDTO bookDTO = covertToBookDTO(book);
            return ResponseEntity.ok(bookDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        }catch (FeignException e){
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(null);
        }

    }

}
