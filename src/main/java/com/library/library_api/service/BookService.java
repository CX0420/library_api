
package com.library.library_api.service;

import com.library.library_api.entity.Book;

import java.util.List;

public interface BookService {

    // Get all books
    List<Book> getAllBooks();

    // Get book by ID
    Book getBookById(Long id);

    // Create a new book
    Book createBook(Book book);

    // Update an existing book
    Book updateBook(Long id, Book book);

    // Delete a book
    void deleteBook(Long id);
}
