package antd_access;

import antd_access.model.db.User;
import antd_access.model.req.menu.MenuParams;
import antd_access.repository.db.UserRepository;
import antd_access.services.MenuService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AntdAccessApplication {

	public static void main(String[] args) {
		SpringApplication.run(AntdAccessApplication.class, args);
	}


	@Bean
	CommandLineRunner initUserData(UserRepository userRepository, PasswordEncoder passwordEncoder){
		return args -> {
			User user = new User();
			user.setUsername("admin");

			user.setPassword(passwordEncoder.encode("Abcd1234"));

			user.setCreatedAt(System.currentTimeMillis());
			user.setUpdatedAt(System.currentTimeMillis());
			userRepository.save(user);
		};
	}


	@Bean
	CommandLineRunner initMenuData(MenuService menuService){
		return args -> {
			menuService.createMenu(
					MenuParams.builder()
							.identifier("welcome")
							.name("welcome")
							.icon("ad")
							.path("/welcome")
							.hide(false)
							.idx(0)
							.build()
			);
			menuService.createMenu(
					MenuParams.builder()
							.identifier("admin")
							.name("admin")
							.icon("crown")
							.path("/admin")
							.hide(false)
							.idx(1)
							.build()
			);

			menuService.createMenu(
					MenuParams.builder()
							.identifier("menus")
							.name("admin")
							.icon("smile")
							.path("/menus")
							.hide(false)
							.idx(3)
							.build()
			);
			menuService.createMenu(
					MenuParams.builder()
							.identifier("roles")
							.name("roles")
							.icon("user-secret")
							.path("/roles")
							.hide(false)
							.idx(4)
							.build()
			);
		};
	}

}
