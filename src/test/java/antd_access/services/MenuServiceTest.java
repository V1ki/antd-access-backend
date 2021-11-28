package antd_access.services;

import antd_access.model.req.menu.MenuParams;
import antd_access.repository.db.MenuRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    @Autowired
    private MenuRepository menuRepository;

    @Test
    @Order(1)
    void isEmpty(){
        Assertions.assertThat(menuRepository.count())
                .isEqualTo(0);
    }

    @Test
    @Order(2)
    void createMenu() {
        menuService.createMenu(
                MenuParams.builder()
                        .identifier("test")
                        .name("test")
                        .icon("smile")
                        .path("/test")
                        .hide(false)
                        .idx(0)
                        .build()
        );

        Assertions.assertThat(menuRepository.count())
                .isEqualTo(1);
        Assertions.assertThat(menuRepository.findById(1L))
                .isPresent()
                .matches(menu -> menu.get().getIdentifier().equals("test")) ;
    }

    @Test
    @Order(3)
    void updateMenu() {

        menuService.createMenu(
                MenuParams.builder()
                        .mid(1L)
                        .identifier("test1")
                        .name("test")
                        .icon("smile")
                        .path("/test")
                        .hide(false)
                        .idx(0)
                        .build()
        );

        Assertions.assertThat(menuRepository.count())
                .isEqualTo(1);
        Assertions.assertThat(menuRepository.findById(1L))
                .isPresent()
                .matches(menu -> menu.get().getIdentifier().equals("test1")) ;
    }

    @Test
    @Order(4)
    void getMenuList() {
        Assertions.assertThat(menuService.getMenuList(1,10, Arrays.asList("createdAt","name"), Arrays.asList("desc","desc")))
                .hasSize(1) ;
    }

    @Test
    @Order(5)
    void getMenuDetail() {

        Assertions.assertThat(menuService.getMenuDetail(1L))
                .isPresent()
                .matches(menu -> menu.get().getIdentifier().equals("test1")) ;
    }

    @Test
    @Order(6)
    void deleteMenu() {
        menuService.deleteMenu(1L);
        Assertions.assertThat(menuRepository.count())
                .isEqualTo(0);
    }


}