
package com.library.library_api.dto.response;

import com.library.library_api.entity.Book;
import java.time.LocalDateTime;

public record BookResponse(
        Long id,
        String title,
        String isbnNumber,
        String author,
        LocalDateTime createdDate,
        LocalDateTime modifiedDate
) {
    public static BookResponse from(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getIsbnNumber(),
                book.getAuthor(),
                book.getCreatedDate(),
                book.getModifiedDate()
        );
    }
}
