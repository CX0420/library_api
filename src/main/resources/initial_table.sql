
-- =====================================================
-- LIBRARY MANAGEMENT API
-- PostgreSQL Database Schema
-- =====================================================


-- =====================================================
-- 1. BORROWER
-- =====================================================

CREATE TABLE borrower (
    id BIGSERIAL PRIMARY KEY,

    -- Audit fields
    created_by VARCHAR(100),
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_by VARCHAR(100),
    modified_date TIMESTAMP,

    -- Borrower information
    name VARCHAR(150) NOT NULL,
    email_address VARCHAR(255) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',

    CONSTRAINT chk_borrower_status
        CHECK (status IN ('ACTIVE', 'INACTIVE'))
);


-- =====================================================
-- 2. BOOK
-- =====================================================

CREATE TABLE book (
    id BIGSERIAL PRIMARY KEY,

    -- Audit fields
    created_by VARCHAR(100),
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_by VARCHAR(100),
    modified_date TIMESTAMP,

    -- Book information
    title VARCHAR(255) NOT NULL,
    isbn_number VARCHAR(20) NOT NULL UNIQUE,
    author VARCHAR(150) NOT NULL,

    -- Soft delete
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE
);


-- =====================================================
-- 3. BOOK INVENTORY
-- Each record represents one physical book copy.
-- =====================================================

CREATE TABLE book_inventory (
    id BIGSERIAL PRIMARY KEY,

    -- Audit fields
    created_by VARCHAR(100),
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_by VARCHAR(100),
    modified_date TIMESTAMP,

    -- Inventory information
    book_fk BIGINT NOT NULL,
    copy_number VARCHAR(50) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE',

    -- Soft delete
    is_deleted BOOLEAN NOT NULL DEFAULT FALSE,

    CONSTRAINT fk_book_inventory_book
        FOREIGN KEY (book_fk)
        REFERENCES book(id),

    CONSTRAINT chk_book_inventory_status
        CHECK (status IN ('AVAILABLE', 'BORROWED', 'LOST'))
);


-- =====================================================
-- 4. BORROWING
-- Stores borrowing and returning transactions.
-- =====================================================

CREATE TABLE borrowing (
    id BIGSERIAL PRIMARY KEY,

    -- Audit fields
    created_by VARCHAR(100),
    created_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    modified_by VARCHAR(100),
    modified_date TIMESTAMP,

    -- Borrowing information
    borrower_fk BIGINT NOT NULL,
    book_inventory_fk BIGINT NOT NULL,

    borrowed_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    due_date TIMESTAMP NOT NULL,
    returned_date TIMESTAMP,

    status VARCHAR(20) NOT NULL DEFAULT 'BORROWED',

    CONSTRAINT fk_borrowing_borrower
        FOREIGN KEY (borrower_fk)
        REFERENCES borrower(id),

    CONSTRAINT fk_borrowing_book_inventory
        FOREIGN KEY (book_inventory_fk)
        REFERENCES book_inventory(id),

    CONSTRAINT chk_borrowing_status
        CHECK (
            status IN (
                'BORROWED',
                'RETURNED',
                'OVERDUE',
                'CANCELLED'
            )
        ),

    CONSTRAINT chk_borrowing_dates
        CHECK (due_date >= borrowed_date),

    CONSTRAINT chk_borrowing_returned_date
        CHECK (
            (status IN ('BORROWED', 'OVERDUE', 'CANCELLED')
                AND returned_date IS NULL)
            OR
            (status = 'RETURNED'
                AND returned_date IS NOT NULL)
        )
);


-- =====================================================
-- 5. INDEXES
-- =====================================================

CREATE INDEX idx_book_inventory_book_fk
    ON book_inventory(book_fk);

CREATE INDEX idx_borrowing_borrower_fk
    ON borrowing(borrower_fk);

CREATE INDEX idx_borrowing_inventory_fk
    ON borrowing(book_inventory_fk);

CREATE INDEX idx_borrowing_status
    ON borrowing(status);


-- =====================================================
-- 6. PREVENT DUPLICATE ACTIVE BORROWING
-- A physical copy can only have one active borrowing.
-- =====================================================

CREATE UNIQUE INDEX uq_active_borrowing_inventory
    ON borrowing(book_inventory_fk)
    WHERE status IN ('BORROWED', 'OVERDUE');


-- =====================================================
-- END OF DATABASE SCHEMA
-- =====================================================
