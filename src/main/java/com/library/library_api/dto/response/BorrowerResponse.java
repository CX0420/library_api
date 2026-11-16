
package com.library.library_api.dto.response;

import com.library.library_api.entity.Borrower;
import java.time.LocalDateTime;

public record BorrowerResponse(
        Long id,
        String name,
        String emailAddress,
        String status,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate
) {
    public static BorrowerResponse from(Borrower borrower) {
        return new BorrowerResponse(
                borrower.getId(),
                borrower.getName(),
                borrower.getEmailAddress(),
                borrower.getStatus(),
                borrower.getCreatedDate(),
                borrower.getModifiedDate()
        );
    }
}
