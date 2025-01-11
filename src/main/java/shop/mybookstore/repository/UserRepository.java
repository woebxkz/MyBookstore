package shop.mybookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.mybookstore.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
