package antd_access;

import antd_access.model.db.PermissionEntity;
import antd_access.model.db.RoleEntity;
import antd_access.model.db.UserEntity;
import antd_access.model.req.menu.MenuParams;
import antd_access.repository.db.MenuRepository;
import antd_access.repository.db.PermissionRepository;
import antd_access.repository.db.RoleRepository;
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
	CommandLineRunner initMenuData(MenuService menuService, MenuRepository menuRepository){
		return args -> {
			if(menuRepository.count() > 0) {
				return;
			}
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



	@Bean
	CommandLineRunner initRole(RoleRepository roleRepository){
		return args -> {
			if(roleRepository.count() > 0) {
				return;
			}
			RoleEntity roleEntity = new RoleEntity();
			roleEntity.setId(1L);
			roleEntity.setName("admin");
			roleEntity.setIdentifier("admin");
			roleEntity.setDescription("管理员");
			roleEntity.setPriority(1);
			roleEntity.setCreatedAt(System.currentTimeMillis());
			roleEntity.setUpdatedAt(System.currentTimeMillis());

			roleRepository.save(roleEntity);
		};

	}

	@Bean
	CommandLineRunner initPermission(PermissionRepository permissionRepository) {
		return args -> {
			if(permissionRepository.count() > 0) {
				return;
			}
			PermissionEntity permissionEntity = new PermissionEntity();
			permissionEntity.setId(1L);
			permissionEntity.setName("Create");
			permissionEntity.setDescription("新增");
			permissionRepository.save(permissionEntity);

			permissionEntity = new PermissionEntity();
			permissionEntity.setId(2L);
			permissionEntity.setName("Update");
			permissionEntity.setDescription("修改");
			permissionRepository.save(permissionEntity);
		};
	}

}
