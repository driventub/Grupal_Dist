package com.distribuida.book.rest;

import com.distribuida.book.clients.AuthorRestClient;
import com.distribuida.book.db.Book;
import com.distribuida.book.dtos.AutorDto;
import com.distribuida.book.dtos.BookDto;
import com.distribuida.book.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookRest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    AuthorRestClient authorRestClient;

    @GetMapping
    public List<BookDto> findAll() {
        var books = bookRepository.findAll();
        return books.stream()
                .map(book -> {
                    BookDto dto = new BookDto();
                    dto.setId(book.getId());
                    dto.setIsbn(book.getIsbn());
                    dto.setTitle(book.getTitle());
                    dto.setPrice(book.getPrice());
                    try {
                        AutorDto author = authorRestClient.findById(book.getAuthorId());
                        if (author != null) {
                            dto.setAuthorName(author.getFirstName() + " " + author.getLastName());
                        } else {
                            dto.setAuthorName("Unknown");
                        }
                    } catch (Exception e) {
                        dto.setAuthorName("Error fetching author");
                    }
                    return dto;
                }).toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> findById(@PathVariable("id") Integer id) {
        System.out.println("findById");
        return bookRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Book book) {
        bookRepository.save(book);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Integer id, @RequestBody Book book) {

        return bookRepository.findById(id).map(obj -> {
            obj.setIsbn(book.getIsbn());
            obj.setTitle(book.getTitle());
            obj.setPrice(book.getPrice());

            bookRepository.save(obj);
            return ResponseEntity.ok().<Void>build();
                }
            ).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        if (bookRepository.existsById(id)){
            bookRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }
}
