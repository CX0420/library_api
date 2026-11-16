
package com.library.library_api.dto.response;

import com.library.library_api.entity.BookInventory;
import java.time.LocalDateTime;

public record BookInventoryResponse(
        Long id,
        Long bookId,
        String bookTitle,
        String copyNumber,
        String status,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate
) {
    public static BookInventoryResponse from(BookInventory inventory) {
        return new BookInventoryResponse(
                inventory.getId(),
                inventory.getBook().getId(),
                inventory.getBook().getTitle(),
                inventory.getCopyNumber(),
                inventory.getStatus(),
                inventory.getCreatedDate(),
                inventory.getModifiedDate()
        );
    }
}
