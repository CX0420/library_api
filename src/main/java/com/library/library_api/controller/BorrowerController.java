
package com.library.library_api.controller;

import com.library.library_api.dto.request.BorrowerRequest;
import com.library.library_api.dto.response.BorrowerResponse;
import com.library.library_api.entity.Borrower;
import com.library.library_api.service.BorrowerService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowers")
public class BorrowerController {

    private final BorrowerService borrowerService;

    public BorrowerController(BorrowerService borrowerService) {
        this.borrowerService = borrowerService;
    }

    // Get all active borrowers
    @Operation(
            summary = "Get all borrowers",
            description = "Retrieve a list of all active borrowers"
    )
    @GetMapping
    public ResponseEntity<List<BorrowerResponse>> getAllBorrowers() {

        List<BorrowerResponse> borrowers = borrowerService.getAllBorrowers()
                .stream()
                .map(BorrowerResponse::from)
                .toList();

        return ResponseEntity.ok(borrowers);
    }

    // Get borrower by ID
    @Operation(
            summary = "Get borrower by ID",
            description = "Retrieve an active borrower by their ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<BorrowerResponse> getBorrowerById(
            @PathVariable Long id) {

        Borrower borrower = borrowerService.getBorrowerById(id);

        return ResponseEntity.ok(BorrowerResponse.from(borrower));
    }

    // Create a new borrower
    @Operation(
            summary = "Create borrower",
            description = "Register a new borrower in the library"
    )
    @PostMapping
    public ResponseEntity<BorrowerResponse> createBorrower(
            @Valid @RequestBody BorrowerRequest request) {

        Borrower borrower = request.toEntity();

        Borrower savedBorrower = borrowerService.createBorrower(borrower);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BorrowerResponse.from(savedBorrower));
    }

    // Update an existing borrower
    @Operation(
            summary = "Update borrower",
            description = "Update the details of an existing active borrower"
    )
    @PutMapping("/{id}")
    public ResponseEntity<BorrowerResponse> updateBorrower(
            @PathVariable Long id,
            @Valid @RequestBody BorrowerRequest request) {

        Borrower updatedBorrower = borrowerService.updateBorrower(
                id,
                request.toEntity()
        );

        return ResponseEntity.ok(BorrowerResponse.from(updatedBorrower));
    }

    // Deactivate borrower (soft delete)
    @Operation(
            summary = "Deactivate borrower",
            description = "Deactivate a borrower by their ID without physically deleting their record"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBorrower(
            @PathVariable Long id) {

        Borrower borrower = borrowerService.getBorrowerById(id);

        borrowerService.deleteBorrower(id);

        String message = "Successfully deactivated borrower: "
                + borrower.getName();

        return ResponseEntity.ok(message);
    }
}
