
package com.library.library_api.dto.response;

import com.library.library_api.entity.Borrowing;
import java.time.LocalDateTime;

public record BorrowingResponse(
        Long id,
        Long borrowerId,
        String borrowerName,
        Long inventoryId,
        Long bookId,
        String bookTitle,
        String copyNumber,
        LocalDateTime borrowedDate,
        LocalDateTime dueDate,
        LocalDateTime returnedDate,
        String status
) {
    public static BorrowingResponse from(Borrowing borrowing) {
        return new BorrowingResponse(
                borrowing.getId(),
                borrowing.getBorrower().getId(),
                borrowing.getBorrower().getName(),
                borrowing.getBookInventory().getId(),
                borrowing.getBookInventory().getBook().getId(),
                borrowing.getBookInventory().getBook().getTitle(),
                borrowing.getBookInventory().getCopyNumber(),
                borrowing.getBorrowedDate(),
                borrowing.getDueDate(),
                borrowing.getReturnedDate(),
                borrowing.getStatus().name()
        );
    }
}
