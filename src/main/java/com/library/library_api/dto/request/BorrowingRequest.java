
package com.library.library_api.dto.request;

import com.library.library_api.entity.BookInventory;
import com.library.library_api.entity.Borrower;
import com.library.library_api.entity.Borrowing;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record BorrowingRequest(

        @NotNull(message = "Borrower ID is required")
        @Positive(message = "Borrower ID must be positive")
        Long borrowerId,

        @NotNull(message = "Inventory ID is required")
        @Positive(message = "Inventory ID must be positive")
        Long inventoryId

) {
        public Borrowing toEntity(
                Borrower borrower,
                BookInventory inventory
        ) {
                Borrowing borrowing = new Borrowing();
                borrowing.setBorrower(borrower);
                borrowing.setBookInventory(inventory);
                return borrowing;
        }
}
