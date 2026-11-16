
package com.library.library_api.service.impl;

import com.library.library_api.entity.Book;
import com.library.library_api.entity.BookInventory;
import com.library.library_api.entity.Borrower;
import com.library.library_api.entity.Borrowing;
import com.library.library_api.enums.BorrowingStatus;
import com.library.library_api.exception.ResourceNotFoundException;
import com.library.library_api.repository.BookInventoryRepository;
import com.library.library_api.repository.BorrowerRepository;
import com.library.library_api.repository.BorrowingRepository;
import com.library.library_api.service.BorrowingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class BorrowingServiceImpl implements BorrowingService {

    private final BorrowingRepository borrowingRepository;
    private final BorrowerRepository borrowerRepository;
    private final BookInventoryRepository inventoryRepository;

    public BorrowingServiceImpl(
            BorrowingRepository borrowingRepository,
            BorrowerRepository borrowerRepository,
            BookInventoryRepository inventoryRepository) {
        this.borrowingRepository = borrowingRepository;
        this.borrowerRepository = borrowerRepository;
        this.inventoryRepository = inventoryRepository;
    }

    // Get all borrowing records
    @Override
    @Transactional(readOnly = true)
    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.findAll();
    }

    // Get borrowing record by ID
    @Override
    @Transactional(readOnly = true)
    public Borrowing getBorrowingById(Long id) {
        return borrowingRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Borrowing not found with ID: " + id));
    }

    // Borrow a book
    @Override
    public Borrowing borrowBook(Long borrowerId, Long inventoryId) {

        // Find borrower
        Borrower borrower = borrowerRepository.findById(borrowerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Borrower not found with ID: " + borrowerId));

        // Check borrower status
        if (!"ACTIVE".equals(borrower.getStatus())) {
            throw new IllegalStateException("Borrower is not active");
        }

        // Find inventory (exclude deleted copies)
        BookInventory inventory = inventoryRepository.findByIdAndIsDeletedFalse(inventoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Inventory not found or has been deleted. ID: " + inventoryId));

        // Check whether the book has been soft-deleted
        Book book = inventory.getBook();

        if (book.isDeleted()) {
            throw new IllegalStateException(
                    "Cannot borrow a book that has been removed from the library");
        }

        // Check inventory availability
        if (!"AVAILABLE".equals(inventory.getStatus())) {
            throw new IllegalStateException("Book copy is not available");
        }

        // Create borrowing record
        LocalDateTime now = LocalDateTime.now();

        Borrowing borrowing = new Borrowing();
        borrowing.setBorrower(borrower);
        borrowing.setBookInventory(inventory);
        borrowing.setBorrowedDate(now);
        borrowing.setDueDate(now.plusDays(14));
        borrowing.setStatus(BorrowingStatus.BORROWED);

        // Update inventory status
        inventory.setStatus("BORROWED");

        inventoryRepository.save(inventory);

        return borrowingRepository.save(borrowing);
    }

    // Return a book
    @Override
    public Borrowing returnBook(Long borrowingId) {

        Borrowing borrowing = getBorrowingById(borrowingId);

        // Check borrowing status
        if (borrowing.getStatus() != BorrowingStatus.BORROWED
                && borrowing.getStatus() != BorrowingStatus.OVERDUE) {
            throw new IllegalStateException(
                    "This borrowing cannot be returned");
        }

        BookInventory inventory = borrowing.getBookInventory();

        // Update borrowing record
        borrowing.setStatus(BorrowingStatus.RETURNED);
        borrowing.setReturnedDate(LocalDateTime.now());

        // Make inventory available again
        inventory.setStatus("AVAILABLE");

        inventoryRepository.save(inventory);

        return borrowingRepository.save(borrowing);
    }
}
