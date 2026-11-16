package com.library.library_api.entity;

import com.library.library_api.common.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "book_inventory")
@Getter
@Setter
@NoArgsConstructor
public class BookInventory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_fk", nullable = false)
    private Book book;

    @Column(name = "copy_number", nullable = false,
            unique = true, length = 50)
    private String copyNumber;

    @Column(nullable = false, length = 20)
    private String status = "AVAILABLE";

    @Column(name = "is_deleted", nullable = false)
    private boolean isDeleted = false;
}
