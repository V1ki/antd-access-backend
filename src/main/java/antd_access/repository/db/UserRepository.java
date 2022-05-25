package antd_access.repository.db;

import antd_access.model.db.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(exported = false)
public interface UserRepository extends JpaRepository<UserEntity, Long> {

//    User findByUsername(String username);
    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);

    void deleteByUsername(String username);

    UserEntity findByTokenAndLastLoginAt(String token, long lastLoginAt);
}
