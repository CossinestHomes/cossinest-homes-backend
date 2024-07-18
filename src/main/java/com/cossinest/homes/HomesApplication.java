package com.cossinest.homes;

import com.cossinest.homes.domain.concretes.business.Category;
import com.cossinest.homes.domain.concretes.user.UserRole;
import com.cossinest.homes.domain.enums.RoleType;
import com.cossinest.homes.payload.request.business.CategoryRequestDTO;
import com.cossinest.homes.payload.request.user.UserRequest;
import com.cossinest.homes.payload.request.user.UserSaveRequest;
import com.cossinest.homes.repository.user.UserRoleRepository;
import com.cossinest.homes.service.business.CategoryService;
import com.cossinest.homes.service.user.UserService;
import com.cossinest.homes.service.validator.UserRoleService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HomesApplication implements CommandLineRunner {



	private final UserRoleService userRoleService;
	private final UserService userService;
	private final UserRoleRepository userRoleRepository;

	private final CategoryService categoryService;

	public HomesApplication (UserRoleService userRoleService,
										UserService userService,
										UserRoleRepository userRoleRepository,CategoryService categoryService) {
		this.userRoleService = userRoleService;
		this.userService = userService;
		this.userRoleRepository = userRoleRepository;
		this.categoryService=categoryService;
	}


	public static void main(String[] args) {
		SpringApplication.run(HomesApplication.class, args);
	}





	@Override
	public void run(String... args) throws Exception {

		if (userRoleService.getAllUserRoles().isEmpty()) {
			UserRole admin = new UserRole();

			admin.setRoleType(RoleType.ADMIN);
			admin.setRoleName("Admin");
			userRoleRepository.save(admin);

			UserRole manager = new UserRole();
			manager.setRoleType(RoleType.MANAGER);
			manager.setRoleName("Manager");
			userRoleRepository.save(manager);

			UserRole customer = new UserRole();
			customer.setRoleType(RoleType.CUSTOMER);
			customer.setRoleName("Customer");
			userRoleRepository.save(customer);

		}

		//!!! Admin yoksa BuiltIn Admin olusturuluyor
		if (userService.countAllAdmins() == 0) {
			UserSaveRequest adminRequest = new UserSaveRequest();
		//	adminRequest.setUsername("SuperAdmin"); // builtIN degeri TRUE olarak setlenmis olacak
			adminRequest.setEmail("admin@admin.com");
			adminRequest.setPassword("A1a@secure");
			adminRequest.setFirstName("Eda");
			adminRequest.setLastName("Duygu");
			adminRequest.setPhone("123-123-123-4444");
			adminRequest.setBuiltIn(true);


			userService.saveUserWithoutRequest(adminRequest);

		}

		if (categoryService.countBuiltInTrue() == 0) {
			CategoryRequestDTO category=new CategoryRequestDTO();
			category.setActive(true);
			category.setIcon("icon1");
			category.setSlug("slug1");
			category.setTitle("Arsa");
			category.setSeq(0);

			categoryService.createCategory(category);

		}
	}
}
