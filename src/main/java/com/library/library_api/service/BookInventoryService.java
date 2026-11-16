package com.library.library_api.service;

import com.library.library_api.entity.BookInventory;

import java.util.List;

public interface BookInventoryService {

    List<BookInventory> getAllInventory();

    BookInventory getInventoryById(Long id);

    List<BookInventory> getInventoryByBookId(Long bookId);

    BookInventory createInventory(BookInventory inventory);

    void deleteInventory(Long id);
}