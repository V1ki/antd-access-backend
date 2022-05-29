package antd_access.repository.db;

import antd_access.model.db.RolePermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RolePermissionRepository extends JpaRepository<RolePermissionEntity, Long> {
}
