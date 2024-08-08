package cc.maids.librarymanagement.user.repository;

import cc.maids.librarymanagement.user.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AdminUser, Long> {

    public Optional<AdminUser> findByUsername(String username);
}
