package antd_access.repository.db;

import antd_access.model.db.RoleEntity;
import antd_access.model.db.UserRoleEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
class UserRoleRepositoryTest {

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    RoleRepository roleRepository;

    @Order(0)
    @Test
    void ensureHasOneRole(){
        assertEquals(1, roleRepository.count());
    }

    @Order(1)
    @Test
    void deleteAll(){
        userRoleRepository.deleteAll();
        assertEquals(0, userRoleRepository.count());
    }


    @Order(2)
    @Test
    void insertUserRole(){
        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setRoleId(1);
        userRole.setUserId(1);
        userRole.setCreatedAt(System.currentTimeMillis());
        userRoleRepository.save(userRole);

        assertEquals(1, userRoleRepository.count());
    }

    @Order(3)
    @Test
    void findAllRoleByUserId() {
        List<RoleEntity> roleEntityList  = userRoleRepository.findAllRoleByUserId(1);
        assertEquals(1, roleEntityList.size());

        Assertions.assertThat(roleEntityList.get(0))
                .isNotNull()
                .hasFieldOrPropertyWithValue("name", "admin1")
        ;
    }
}