
package com.library.library_api.controller;

import com.library.library_api.dto.request.BookInventoryRequest;
import com.library.library_api.dto.response.BookInventoryResponse;
import com.library.library_api.entity.Book;
import com.library.library_api.entity.BookInventory;
import com.library.library_api.service.BookInventoryService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class BookInventoryController {

    private final BookInventoryService inventoryService;

    public BookInventoryController(BookInventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // Get all inventory
    @Operation(
            summary = "Get all inventory",
            description = "Retrieve all physical book copies in the library"
    )
    @GetMapping
    public ResponseEntity<List<BookInventoryResponse>> getAllInventory() {

        List<BookInventoryResponse> inventory = inventoryService.getAllInventory()
                .stream()
                .map(BookInventoryResponse::from)
                .toList();

        return ResponseEntity.ok(inventory);
    }

    // Get inventory by ID
    @Operation(
            summary = "Get inventory by ID",
            description = "Retrieve a specific book copy by its inventory ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<BookInventoryResponse> getInventoryById(
            @PathVariable Long id) {

        BookInventory inventory = inventoryService.getInventoryById(id);

        return ResponseEntity.ok(BookInventoryResponse.from(inventory));
    }

    // Get inventory by book ID
    @Operation(
            summary = "Get inventory by book ID",
            description = "Retrieve all physical copies belonging to a specific book"
    )
    @GetMapping("/book/{bookId}")
    public ResponseEntity<List<BookInventoryResponse>> getInventoryByBookId(
            @PathVariable Long bookId) {

        List<BookInventoryResponse> inventory = inventoryService
                .getInventoryByBookId(bookId)
                .stream()
                .map(BookInventoryResponse::from)
                .toList();

        return ResponseEntity.ok(inventory);
    }

    // Add a new inventory copy
    @Operation(
            summary = "Create inventory",
            description = "Add a new physical book copy to the library"
    )
    @PostMapping
    public ResponseEntity<BookInventoryResponse> createInventory(
            @Valid @RequestBody BookInventoryRequest request) {

        Book book = new Book();
        book.setId(request.bookId());

        BookInventory inventory = new BookInventory();
        inventory.setBook(book);
        inventory.setCopyNumber(request.copyNumber());

        BookInventory savedInventory = inventoryService.createInventory(inventory);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(BookInventoryResponse.from(savedInventory));
    }

    // Delete inventory
    @Operation(
            summary = "Delete inventory",
            description = "Remove a physical book copy from the library"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInventory(@PathVariable Long id) {

        BookInventory inventory = inventoryService.getInventoryById(id);

        inventoryService.deleteInventory(id);

        String message = "Successfully deleted inventory copy: "
                + inventory.getCopyNumber();

        return ResponseEntity.ok(message);
    }
}
