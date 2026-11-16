
package com.library.library_api.dto.request;

import com.library.library_api.entity.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BookRequest(

        @NotBlank(message = "Book title is required")
        @Size(max = 255)
        String title,

        @NotBlank(message = "ISBN number is required")
        @Size(max = 20)
        String isbnNumber,

        @NotBlank(message = "Author is required")
        @Size(max = 150)
        String author

) {
        public Book toEntity() {
                Book book = new Book();
                book.setTitle(title);
                book.setIsbnNumber(isbnNumber);
                book.setAuthor(author);
                return book;
        }

        public void updateEntity(Book book) {
                book.setTitle(title);
                book.setIsbnNumber(isbnNumber);
                book.setAuthor(author);
        }
}
