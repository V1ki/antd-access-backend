package antd_access.repository.db;

import antd_access.model.db.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

//    User findByUsername(String username);
    Optional<User> findByUsername(String username);

}
