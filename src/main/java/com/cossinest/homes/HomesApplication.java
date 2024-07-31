package com.cossinest.homes;

import com.cossinest.homes.domain.concretes.user.UserRole;
import com.cossinest.homes.domain.enums.Cities;
import com.cossinest.homes.domain.enums.RoleType;
import com.cossinest.homes.payload.request.business.CategoryRequestDTO;
import com.cossinest.homes.payload.request.business.CityRequest;
import com.cossinest.homes.payload.request.business.CountryRequest;
import com.cossinest.homes.payload.request.business.DistrictRequest;
import com.cossinest.homes.payload.request.user.UserSaveRequest;
import com.cossinest.homes.repository.user.UserRoleRepository;
import com.cossinest.homes.service.business.CategoryService;
import com.cossinest.homes.service.business.CityService;
import com.cossinest.homes.service.business.CountryService;
import com.cossinest.homes.service.business.DistrictService;
import com.cossinest.homes.service.user.UserService;
import com.cossinest.homes.service.validator.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class HomesApplication implements CommandLineRunner {


	private final PasswordEncoder passwordEncoder;
	private final UserRoleService userRoleService;
	private final UserService userService;
	private final UserRoleRepository userRoleRepository;
	private  final CityService cityService;

	private final CategoryService categoryService;
	private final CountryService countryService;
	private final DistrictService districtService;

	public HomesApplication (UserRoleService userRoleService,
							 UserService userService,
							 UserRoleRepository userRoleRepository, CategoryService categoryService, PasswordEncoder passwordEncoder, CityService cityService, CountryService countryService, DistrictService districtService) {
		this.userRoleService = userRoleService;
		this.userService = userService;
		this.userRoleRepository = userRoleRepository;
		this.categoryService=categoryService;
		this.passwordEncoder=passwordEncoder;
		this.cityService = cityService;
		this.countryService = countryService;

		this.districtService = districtService;
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
			adminRequest.setPassword(passwordEncoder.encode("A1a@secure"));
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

		if (countryService.countAllCountries() == 0) {
			CountryRequest countryRequest = new CountryRequest();
			countryRequest.setName("Türkiye");

			countryService.setBuiltInForCountry();
		}

		if (cityService.countAllCities() == 0) {
			CityRequest cityRequest = new CityRequest();
			cityRequest.setName("Istanbul");
			cityRequest.setCountry_id(1);

			cityService.setBuiltInForCity();

		}

		if (districtService.countAllDistricts() == 0) {
			DistrictRequest districtRequest = new DistrictRequest();
			districtRequest.setName("Bakırköy");
			districtRequest.setDistrict_id(1);

			districtService.setBuiltInForDistrict();
		}


	}
}
