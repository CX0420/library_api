
package com.library.library_api.service.impl;

import com.library.library_api.entity.Book;
import com.library.library_api.exception.ResourceNotFoundException;
import com.library.library_api.repository.BookRepository;
import com.library.library_api.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Get all active books
    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAllByIsDeletedFalse();
    }

    // Get book by ID (excluding deleted books)
    @Override
    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        return bookRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Book not found with ID: " + id));
    }

    // Create a new book
    @Override
    @Transactional
    public Book createBook(Book book) {

        if (bookRepository.existsByIsbnNumber(book.getIsbnNumber())) {
            throw new IllegalStateException(
                    "A book with ISBN " + book.getIsbnNumber() + " already exists."
            );
        }

        book.setId(null);
        book.setDeleted(false);

        return bookRepository.save(book);
    }

    // Update an existing book
    @Override
    public Book updateBook(Long id, Book book) {

        Book existingBook = getBookById(id);

        existingBook.setTitle(book.getTitle());
        existingBook.setIsbnNumber(book.getIsbnNumber());
        existingBook.setAuthor(book.getAuthor());

        return bookRepository.save(existingBook);
    }

    // Soft delete a book
    @Transactional
    @Override
    public void deleteBook(Long id) {

        Book book = bookRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "This book has already been deleted or does not exist."
                        )
                );

        book.setDeleted(true);

        bookRepository.save(book);
    }
}
