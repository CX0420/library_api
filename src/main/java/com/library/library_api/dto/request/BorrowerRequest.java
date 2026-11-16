
package com.library.library_api.dto.request;

import com.library.library_api.entity.Borrower;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BorrowerRequest(

        @NotBlank(message = "Borrower name is required")
        @Size(max = 150)
        String name,

        @NotBlank(message = "Email address is required")
        @Email(message = "Invalid email address")
        @Size(max = 255)
        String emailAddress

) {
        public Borrower toEntity() {
                Borrower borrower = new Borrower();
                borrower.setName(name);
                borrower.setEmailAddress(emailAddress);
                borrower.setStatus("ACTIVE");
                return borrower;
        }

        public void updateEntity(Borrower borrower) {
                borrower.setName(name);
                borrower.setEmailAddress(emailAddress);
        }
}
