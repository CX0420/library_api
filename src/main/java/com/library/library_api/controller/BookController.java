package com.library.library_api.controller;

import com.library.library_api.dto.request.BookRequest;
import com.library.library_api.dto.response.BookResponse;
import com.library.library_api.entity.Book;
import com.library.library_api.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Operation(summary = "Get all books", description = "Retrieve a list of all books")
    @GetMapping
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        List<BookResponse> books = bookService.getAllBooks()
                .stream()
                .map(BookResponse::from)
                .toList();
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Get book by ID", description = "Retrieve a book by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(BookResponse.from(book));
    }

    @Operation(summary = "Create a new book", description = "Add a new book to the library")
    @PostMapping
    public ResponseEntity<BookResponse> createBook(@Valid @RequestBody BookRequest request) {
        Book book = request.toEntity();
        Book savedBook = bookService.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(BookResponse.from(savedBook));
    }

    @Operation(summary = "Update an existing book", description = "Update the details of an existing book")
    @PutMapping("/{id}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable Long id,
            @Valid @RequestBody BookRequest request) {
        Book updatedBook = bookService.updateBook(id, request.toEntity());
        return ResponseEntity.ok(BookResponse.from(updatedBook));
    }

    @Operation(summary = "Delete a book", description = "Soft delete a book by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        Book book = bookService.getBookById(id);
        bookService.deleteBook(id);
        String message = "Successfully deleted book: " + book.getTitle();
        return ResponseEntity.ok(message);
    }
}