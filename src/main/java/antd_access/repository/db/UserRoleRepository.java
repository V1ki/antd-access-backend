package antd_access.repository.db;

import antd_access.model.db.RoleEntity;
import antd_access.model.db.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

}
