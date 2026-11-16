package com.library.library_api.service.impl;

import com.library.library_api.entity.Book;
import com.library.library_api.entity.BookInventory;
import com.library.library_api.exception.ResourceNotFoundException;
import com.library.library_api.repository.BookInventoryRepository;
import com.library.library_api.repository.BookRepository;
import com.library.library_api.service.BookInventoryService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class BookInventoryServiceImpl implements BookInventoryService {

    private final BookInventoryRepository inventoryRepository;
    private final BookRepository bookRepository;

    public BookInventoryServiceImpl(
            BookInventoryRepository inventoryRepository,
            BookRepository bookRepository) {

        this.inventoryRepository = inventoryRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookInventory> getAllInventory() {
        return inventoryRepository.findAllByIsDeletedFalse();
    }

    @Override
    @Transactional(readOnly = true)
    public BookInventory getInventoryById(Long id) {
        return inventoryRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Inventory not found or has been deleted. ID: " + id
                        ));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookInventory> getInventoryByBookId(Long bookId) {

        Book book = bookRepository.findByIdAndIsDeletedFalse(bookId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Book not found or has been deleted. ID: " + bookId
                        ));

        return inventoryRepository.findAllByBook_IdAndIsDeletedFalse(book.getId());
    }

    @Override
    public BookInventory createInventory(BookInventory inventory) {

        Long bookId = inventory.getBook().getId();

        Book book = bookRepository.findByIdAndIsDeletedFalse(bookId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Book not found or has been deleted. ID: " + bookId
                        ));

        inventory.setId(null);
        inventory.setBook(book);
        inventory.setStatus("AVAILABLE");
        inventory.setDeleted(false);

        return inventoryRepository.save(inventory);
    }

    @Override
    public void deleteInventory(Long id) {

        BookInventory inventory = getInventoryById(id);

        if ("BORROWED".equals(inventory.getStatus())) {
            throw new IllegalStateException(
                    "Cannot delete inventory because this book copy is currently borrowed."
            );
        }

        inventory.setDeleted(true);

        inventoryRepository.save(inventory);
    }
}