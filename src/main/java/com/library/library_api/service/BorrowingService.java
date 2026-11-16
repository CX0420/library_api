
package com.library.library_api.service;

import com.library.library_api.entity.Borrowing;

import java.util.List;

public interface BorrowingService {

    List<Borrowing> getAllBorrowings();

    Borrowing getBorrowingById(Long id);

    Borrowing borrowBook(Long borrowerId, Long inventoryId);

    Borrowing returnBook(Long borrowingId);
}
