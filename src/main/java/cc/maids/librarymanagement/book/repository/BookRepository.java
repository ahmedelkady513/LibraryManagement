package cc.maids.librarymanagement.book.repository;

import cc.maids.librarymanagement.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
    boolean existsByIsbn(String isbn);
}
