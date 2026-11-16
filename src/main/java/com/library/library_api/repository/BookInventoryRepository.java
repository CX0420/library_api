package com.library.library_api.repository;

import com.library.library_api.entity.BookInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookInventoryRepository extends JpaRepository<BookInventory, Long> {

    List<BookInventory> findAllByIsDeletedFalse();

    Optional<BookInventory> findByIdAndIsDeletedFalse(Long id);

    List<BookInventory> findAllByBook_IdAndIsDeletedFalse(Long bookId);
}