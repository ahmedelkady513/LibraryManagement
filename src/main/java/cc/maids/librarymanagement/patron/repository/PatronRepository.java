package cc.maids.librarymanagement.patron.repository;

import cc.maids.librarymanagement.patron.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatronRepository extends JpaRepository<Patron, Long> {

    boolean existsByEmail(String email);
}
