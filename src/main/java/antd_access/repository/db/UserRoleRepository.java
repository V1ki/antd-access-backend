package antd_access.repository.db;

import antd_access.model.db.RoleEntity;
import antd_access.model.db.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    @Query("select r from UserRole ur, Role r where ur.userId = :userId and r.id = ur.roleId")
    List<RoleEntity> findAllRoleByUserId(long userId);
}
