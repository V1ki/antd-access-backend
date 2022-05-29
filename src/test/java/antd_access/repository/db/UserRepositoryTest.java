package antd_access.repository.db;

import antd_access.model.db.RoleEntity;
import antd_access.model.db.UserEntity;
import antd_access.model.db.UserRoleEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@SpringBootTest
@TestMethodOrder(org.junit.jupiter.api.MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Test
    @Order(0)
    public void testCount() {

        assertEquals(1, userRepository.count());
        assertEquals(1, roleRepository.count());
    }




    @Test
    @Order(1)
    public void testAddUser(){
        // 正常流程
        RoleEntity roleEntity = roleRepository.getOne(1L) ;


        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("test");
        userEntity.setPassword("test");
        userEntity.setAvatar("test");


        userEntity = userRepository.save(userEntity);
        log.info("userEntity: {}", userEntity.getUid());


        UserRoleEntity userRoleEntity = new UserRoleEntity();
        userRoleEntity.setRole(roleEntity);
        userRoleEntity.setUser(userEntity);
        userRoleEntity.setId(new UserRoleEntity.UserRoleKey());
        userRoleRepository.save(userRoleEntity);
        log.info("userRoleEntity: {}", userRepository.findById(userEntity.getUid()).get().getRoles());

        assertEquals(1, userRepository.findById(userEntity.getUid()).get().getRoles().size());
        assertEquals(2, userRepository.count());
        assertEquals(1, userRoleRepository.count());
        assertEquals(1, roleRepository.count());

        UserEntity otherEntity = userRepository.findById(userEntity.getUid()).get();
        userRoleRepository.deleteAll(otherEntity.getRoles());
        assertEquals(0, userRoleRepository.count());
        assertEquals(2, userRepository.count());
        // 不能这样删除 ...
        // 需要先删除关联信息..
        userRepository.deleteById(userEntity.getUid());
        assertEquals(1, userRepository.count());
        assertEquals(0, userRoleRepository.count());


    }
}
