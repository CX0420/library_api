package com.library.library_api.repository;

import com.library.library_api.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByIsDeletedFalse();

    Optional<Book> findByIdAndIsDeletedFalse(Long id);

    boolean existsByIsbnNumber(String isbnNumber);
}