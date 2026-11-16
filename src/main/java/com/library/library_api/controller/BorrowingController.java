
package com.library.library_api.controller;

import com.library.library_api.dto.request.BorrowingRequest;
import com.library.library_api.dto.response.BorrowingResponse;
import com.library.library_api.entity.Borrowing;
import com.library.library_api.service.BorrowingService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowings")
public class BorrowingController {

    private final BorrowingService borrowingService;

    public BorrowingController(BorrowingService borrowingService) {
        this.borrowingService = borrowingService;
    }

    // Get all borrowing records
    @Operation(
            summary = "Get all borrowings",
            description = "Retrieve a list of all borrowing records in the library"
    )
    @GetMapping
    public ResponseEntity<List<BorrowingResponse>> getAllBorrowings() {

        List<BorrowingResponse> borrowings = borrowingService.getAllBorrowings()
                .stream()
                .map(BorrowingResponse::from)
                .toList();

        return ResponseEntity.ok(borrowings);
    }

    // Get borrowing record by ID
    @Operation(
            summary = "Get borrowing by ID",
            description = "Retrieve a specific borrowing record by its ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<BorrowingResponse> getBorrowingById(
            @PathVariable Long id) {

        Borrowing borrowing = borrowingService.getBorrowingById(id);

        return ResponseEntity.ok(BorrowingResponse.from(borrowing));
    }

    // Borrow a book
    @Operation(
            summary = "Borrow a book",
            description = "Create a borrowing record for an active borrower and an available book copy"
    )
    @PostMapping
    public ResponseEntity<BorrowingResponse> borrowBook(
            @Valid @RequestBody BorrowingRequest request) {

        Borrowing borrowing = borrowingService.borrowBook(
                request.borrowerId(),
                request.inventoryId()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BorrowingResponse.from(borrowing));
    }

    // Return a borrowed book
    @Operation(
            summary = "Return a book",
            description = "Mark a borrowed book as returned and make its inventory copy available again"
    )
    @PutMapping("/{id}/return")
    public ResponseEntity<BorrowingResponse> returnBook(
            @PathVariable Long id) {

        Borrowing borrowing = borrowingService.returnBook(id);

        return ResponseEntity.ok(BorrowingResponse.from(borrowing));
    }
}
