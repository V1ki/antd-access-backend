package antd_access.repository.db;


import antd_access.model.db.PermissionEntity;
import antd_access.model.db.RoleEntity;
import antd_access.model.db.RolePermissionEntity;
import antd_access.model.db.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest
@TestMethodOrder(value = org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
public class RolePermissionRepositoryTest {

    @Autowired
    RoleRepository roleRepository ;

    @Autowired
    PermissionRepository permissionRepository ;

    @Autowired
    RolePermissionRepository rolePermissionRepository ;


    @Test
    @Order(0)
    public void countMustBeOne() {
        log.info("countMustBeOne");
        Assertions.assertThat(roleRepository.count()).isEqualTo(1L);
        Assertions.assertThat(permissionRepository.count()).isEqualTo(2L);
        Assertions.assertThat(rolePermissionRepository.count()).isEqualTo(0L);
    }


    @Test
    @Order(1)
    public void addRolePermission(){

        log.info("addRolePermission");
        RoleEntity  roleEntity = roleRepository.findById(1L).get();
        PermissionEntity permissionEntity = permissionRepository.findById(1L).get();


        RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
        rolePermissionEntity.setPermission(permissionEntity);
        rolePermissionEntity.setRole(roleEntity);
        rolePermissionEntity.setId(new RolePermissionEntity.RolePermissionId());
        rolePermissionRepository.save(rolePermissionEntity);

        Assertions.assertThat(rolePermissionRepository.count()).isEqualTo(1L);
    }


    @Test
    @Order(1)
    public void updateRolePermission1(){

        log.info("addRolePermission");

        RoleEntity roleEntity = roleRepository.findById(1L).get();
        PermissionEntity permissionEntity = permissionRepository.findById(2L).get();

        RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
        rolePermissionEntity.setPermission(permissionEntity);
        rolePermissionEntity.setRole(roleEntity);
        rolePermissionEntity.setId(new RolePermissionEntity.RolePermissionId(1L,2L));

        rolePermissionEntity = rolePermissionRepository.save(rolePermissionEntity) ;
        roleEntity.getPermissions().clear();
//        roleEntity.getPermissions().add(rolePermissionEntity);

        roleRepository.save(roleEntity);
    }



    @Test
    @Order(3)
    public void addRolePermission2() {

        log.info("addRolePermission2");
        RoleEntity roleEntity = roleRepository.findById(1L).get();
        PermissionEntity permissionEntity = permissionRepository.findById(2L).get();

        roleEntity.getPermissions().stream().forEach(rolePermissionEntity -> {

            log.info("permission:{}", rolePermissionEntity.getPermission().getName());
                });

        rolePermissionRepository.deleteAll(roleEntity.getPermissions());

        Assertions.assertThat(roleEntity.getPermissions()).hasSize(2);
        org.junit.jupiter.api.Assertions.assertEquals(2, roleEntity.getPermissions().size());


        Assertions.assertThat(roleRepository.findById(1L).get().getPermissions()).hasSize(0);
        org.junit.jupiter.api.Assertions.assertEquals(0, roleRepository.findById(1L).get().getPermissions().size());

        org.junit.jupiter.api.Assertions.assertThrows(LazyInitializationException.class, () -> roleRepository.getOne(1L).getPermissions().size());


        RolePermissionEntity rolePermissionEntity = new RolePermissionEntity();
        rolePermissionEntity.setPermission(permissionEntity);
        rolePermissionEntity.setRole(roleEntity);
        rolePermissionEntity.setId(new RolePermissionEntity.RolePermissionId());


        rolePermissionRepository.save(rolePermissionEntity);

        Assertions.assertThat(roleRepository.findById(1L).get().getPermissions()).hasSize(1);

    }




    @Test
    @Order(Integer.MAX_VALUE)
    public void clear(){

        rolePermissionRepository.deleteAll();
        Assertions.assertThat(rolePermissionRepository.count()).isEqualTo(0L);
    }



    }
