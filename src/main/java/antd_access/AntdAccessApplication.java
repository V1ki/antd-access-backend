package antd_access;

import antd_access.model.db.UserEntity;
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


	/**
	 * 为了生成admin 进行测试.
	 * @param userRepository userRepository
	 * @param passwordEncoder passwordEncoder
	 * @return CommandLineRunner
	 */
	@Bean
	CommandLineRunner initRunner(UserRepository userRepository, PasswordEncoder passwordEncoder)	{
		return args -> {
			if(userRepository.existsByUsername("admin")) {
				return;
			}
			UserEntity userEntity = new UserEntity();
			userEntity.setUsername("admin");

			// 这样对吗? 直接保存明文密码,这样好吗?
//        user.setPassword(  userReq.getPassword());
			userEntity.setPassword(passwordEncoder.encode("Abcd1234"));

			userEntity.setCreatedAt(System.currentTimeMillis());
			userEntity.setUpdatedAt(System.currentTimeMillis());

			userRepository.save(userEntity);
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
