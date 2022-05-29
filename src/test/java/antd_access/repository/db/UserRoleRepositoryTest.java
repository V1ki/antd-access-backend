package antd_access.repository.db;

import antd_access.model.db.RoleEntity;
import antd_access.model.db.UserEntity;
import antd_access.model.db.UserRoleEntity;
import org.assertj.core.api.Assertions;
import org.hibernate.Hibernate;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
class UserRoleRepositoryTest {

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Order(0)
    @Test
    void ensureHasOneRole() {
        assertEquals(1, roleRepository.count());
    }

    @Order(1)
    @Test
    void deleteAll() {
        userRoleRepository.deleteAll();
//
//        UserEntity userEntity = userRepository.findById(1L).get();
//        userEntity.getRoles().clear();
//        userRepository.save(userEntity);

        assertEquals(0, userRoleRepository.count());
        assertEquals(1, userRepository.count());
        assertEquals(1, roleRepository.count());
    }

    @Order(2)
    @Test
    void insertUserRole() {
        UserEntity userEntity = userRepository.findById(1L).get();
        RoleEntity newRoleEntity = new RoleEntity(){{
            setName("user");
            setIdentifier("user");
            setDescription("description");
            setPriority(1);
            setCreatedAt(System.currentTimeMillis());
            setUpdatedAt(System.currentTimeMillis());
        }};
//        newRoleEntity = roleRepository.findById(1L).get() ;


        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setUser(userEntity);
//        userRole.setRole(roleRepository.findById(1L).get());
        userRole.setRole(newRoleEntity);
        userRole.setId(new UserRoleEntity.UserRoleKey());
        userRole.setCreatedAt(System.currentTimeMillis());
        userRoleRepository.save(userRole);
//        userRepository.save(userEntity);

        assertEquals(1, userRoleRepository.count());
        assertEquals(1, userRepository.count());
        assertEquals(1, roleRepository.count());
    }


    @Order(3)
    @Test
    void deleteAgain() {
        assertEquals(1, userRoleRepository.count());
        userRoleRepository.deleteAll();
        assertEquals(0, userRoleRepository.count());
        assertEquals(1, userRepository.count());
        assertEquals(1, roleRepository.count());
    }



    @Order(4)
    @Test
    void insertUserRoleAgain() {
        UserEntity userEntity = userRepository.findById(1L).get();
        RoleEntity newRoleEntity = new RoleEntity(){{
            setName("user");
            setIdentifier("user");
            setDescription("description");
            setPriority(1);
            setCreatedAt(System.currentTimeMillis());
            setUpdatedAt(System.currentTimeMillis());
        }};
        newRoleEntity = roleRepository.findById(1L).get() ;


        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setUser(userEntity);
//        userRole.setRole(roleRepository.findById(1L).get());
        userRole.setRole(newRoleEntity);
        userRole.setId(new UserRoleEntity.UserRoleKey());
        userRole.setCreatedAt(System.currentTimeMillis());
        userRoleRepository.save(userRole);
//        userRepository.save(userEntity);

        assertEquals(1, userRoleRepository.count());
    }

    @Order(5)
    @Test
    void findAllRoleByUserId() {

        UserEntity userEntity = userRepository.findById(1L).get();
        assertNotNull(userEntity);
        assertTrue(Hibernate.isInitialized(userEntity.getRoles()));
        assertEquals(1, userEntity.getRoles().size());

        Assertions.assertThat(userEntity.getRoles())
                .anyMatch(ur -> "admin".equals(ur.getRole().getName()));
    }

    @Order(6)
    @Test
    void deleteAgain2() {
        assertEquals(1, userRoleRepository.count());
        userRoleRepository.deleteAll();
        userRoleRepository.flush();

        assertEquals(0, userRoleRepository.count());
        assertEquals(1, userRepository.count());
        assertEquals(1, roleRepository.count());
    }



}