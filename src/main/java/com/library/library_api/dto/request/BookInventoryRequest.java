
package com.library.library_api.dto.request;

import com.library.library_api.entity.Book;
import com.library.library_api.entity.BookInventory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record BookInventoryRequest(

        @NotNull(message = "Book ID is required")
        @Positive(message = "Book ID must be positive")
        Long bookId,

        @NotBlank(message = "Copy number is required")
        @Size(max = 50)
        String copyNumber

) {
        public BookInventory toEntity(Book book) {
                BookInventory inventory = new BookInventory();
                inventory.setBook(book);
                inventory.setCopyNumber(copyNumber);
                inventory.setStatus("AVAILABLE");
                return inventory;
        }
}
