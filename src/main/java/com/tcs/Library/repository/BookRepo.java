package com.tcs.Library.repository;

import com.tcs.Library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepo extends JpaRepository<Book, Long> {
    Optional<Book> findByPublicId(String publicId);

    Optional<Book> findByBookTitleIgnoreCase(String bookTitle);

    boolean existsByBookTitleIgnoreCase(String bookTitle);
}
