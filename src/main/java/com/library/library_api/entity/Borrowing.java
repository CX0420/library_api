package com.library.library_api.entity;

import com.library.library_api.common.BaseEntity;
import com.library.library_api.enums.BorrowingStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "borrowing")
@Getter
@Setter
@NoArgsConstructor
public class Borrowing extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borrower_fk", nullable = false)
    private Borrower borrower;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_inventory_fk", nullable = false)
    private BookInventory bookInventory;

    @Column(name = "borrowed_date", nullable = false)
    private LocalDateTime borrowedDate;

    @Column(name = "due_date", nullable = false)
    private LocalDateTime dueDate;

    @Column(name = "returned_date")
    private LocalDateTime returnedDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BorrowingStatus status = BorrowingStatus.BORROWED;

    @PrePersist
    protected void onBorrowingCreate() {
        if (borrowedDate == null) {
            borrowedDate = LocalDateTime.now();
        }
    }
}
