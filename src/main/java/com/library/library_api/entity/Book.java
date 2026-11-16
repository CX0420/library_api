package com.library.library_api.entity;

import com.library.library_api.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book")
@Getter
@Setter
@NoArgsConstructor
public class Book extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String title;

    @Column(name = "isbn_number", nullable = false,
            unique = true, length = 20)
    private String isbnNumber;

    @Column(nullable = false, length = 150)
    private String author;

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
