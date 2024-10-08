package com.cossinest.homes;

import com.cossinest.homes.domain.concretes.business.*;
import com.cossinest.homes.domain.concretes.user.UserRole;
import com.cossinest.homes.domain.enums.Cities;
import com.cossinest.homes.domain.enums.RoleType;
import com.cossinest.homes.domain.enums.Status;
import com.cossinest.homes.payload.request.business.CategoryRequestDTO;
import com.cossinest.homes.payload.request.business.CityRequest;
import com.cossinest.homes.payload.request.business.CountryRequest;
import com.cossinest.homes.payload.request.business.DistrictRequest;
import com.cossinest.homes.payload.request.user.UserSaveRequest;
import com.cossinest.homes.repository.business.ImagesRepository;
import com.cossinest.homes.repository.user.UserRoleRepository;
import com.cossinest.homes.service.business.*;
import com.cossinest.homes.service.helper.MethodHelper;
import com.cossinest.homes.service.user.UserService;
import com.cossinest.homes.service.validator.UserRoleService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


@SpringBootApplication
public class HomesApplication implements CommandLineRunner {


	private final PasswordEncoder passwordEncoder;
	private final UserRoleService userRoleService;
	private final UserService userService;
	private final UserRoleRepository userRoleRepository;
	private  final CityService cityService;
	private final CategoryPropertyKeyService categoryPropertyKeyService;
	private final AdvertTypesService advertTypesService;
	private final CategoryService categoryService;
	private final CountryService countryService;
	private final DistrictService districtService;
	private final AdvertService advertService;
	private final ImagesRepository imagesRepository;
	private final MethodHelper methodHelper;

	public HomesApplication (AdvertService advertService, AdvertTypesService advertTypesService, UserRoleService userRoleService,
                             UserService userService,
                             UserRoleRepository userRoleRepository, CategoryService categoryService, PasswordEncoder passwordEncoder, CityService cityService, CategoryPropertyKeyService categoryPropertyKeyService, CountryService countryService, DistrictService districtService, ImagesService imagesService, ImagesRepository imagesRepository, MethodHelper methodHelper) {
		this.userRoleService = userRoleService;
		this.userService = userService;
		this.userRoleRepository = userRoleRepository;
		this.categoryService=categoryService;
		this.passwordEncoder=passwordEncoder;
		this.cityService = cityService;
        this.categoryPropertyKeyService = categoryPropertyKeyService;
        this.countryService = countryService;
		this.advertTypesService=advertTypesService;
		this.districtService = districtService;
		this.advertService=advertService;

        this.imagesRepository = imagesRepository;
        this.methodHelper = methodHelper;
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
			adminRequest.setFirstName("Super");
			adminRequest.setLastName("Admin");
			adminRequest.setPhone("123-123-123-4444");
			adminRequest.setBuiltIn(true);

			userService.saveUserWithoutRequest(adminRequest);

		}

		categoryService.generateCategory();
		categoryPropertyKeyService.generateCategoryPropertyKeys();


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

		if (advertTypesService.getAllAdvertTypes().size() == 0) {
			AdvertType advertType = new AdvertType();
			advertType.setBuiltIn(true);
			advertType.setTitle("rent");
			//	advertType.setAdvertList();
			advertTypesService.saveAdvertTypeRunner(advertType);

			AdvertType advertType2 = new AdvertType();
			advertType2.setBuiltIn(true);
			advertType2.setTitle("sell");
			//	advertType2.setAdvertList();
			advertTypesService.saveAdvertTypeRunner(advertType2);
		}

		if (advertService.getAllAdverts().size() == 0) {
			Object[] arr1 = {"Şehir Merkezinde Modern Daire", "3+1 odalı, geniş balkonlu ve yerden ısıtmalı modern daire. Merkezi konum, kapalı otopark ve güvenlik mevcut", "Ankara", 2500000.00, 1L, 2L};
			Object[] arr2 = {"Deniz Manzaralı Ev", "Denize sıfır, 4+1 odalı, özel havuzlu villa. Eşsiz manzara, geniş bahçe ve lüks iç mekan.", "İzmir", 10000000.00, 1L, 3L};
			Object[] arr3 = {"Doğa İçinde Ferah Ev", "2+1 odalı, bahçeli köy evi. Şehrin gürültüsünden uzak, huzurlu yaşam için ideal. Ahşap detaylarla sıcak bir atmosfer.", "Bursa", 12000000.00, 1L, 4L};
			Object[] arr4 = {"Lüks Şehir Evi", "Antalya'nın prestijli bölgesinde 5+1 odalı lüks şehir evi. Akıllı ev sistemi, geniş bahçe ve muhteşem şehir manzarası.", "Antalya", 45000000.00, 1L, 5L};

			List<Object[]> array = new ArrayList<>(new ArrayList<>(Arrays.asList(arr1, arr2, arr3, arr4)));

			for (Object[] o : array) {
				Advert advert = new Advert();
				advert.setTitle((String) o[0]);
				advert.setDescription((String) o[1]);
				advert.setLocation((String) o[2]);
				advert.setPrice((Double) o[3]);
				advert.setCountry(countryService.getById(1L));
				advert.setDistrict(districtService.getDistrictByIdForAdvert((Long) o[4]));
				advert.setCity(cityService.getCityById((Long) o[5]));
				advert.setCategory(categoryService.getCategoryById(1L));
				advert.setAdvertType(advertTypesService.findByIdAdvertType(1L));
				advert.setCreatedAt(LocalDateTime.now());
				advert.setBuiltIn(true);
				advert.setUser(methodHelper.findUserWithId(1L));
				advert.setStatus(Status.ACTIVATED.getValue());
				advert.setIsActive(true);

				advertService.saveRunner(advert);

				String[] imageNames = {"mustakil.jpg", "yatak.jpg", "banyo.jpg", "mutfak.jpg"}; // Bu diziyi fotoğraf isimleriyle doldur

				for (String imageName : imageNames) {
					Path path = Paths.get("src/main/resources/static/images/" + imageName);
					byte[] imageData = Files.readAllBytes(path);

					Images image = new Images();
					image.setName(imageName);
					image.setData(imageData);
					image.setAdvert(advert);

					imagesRepository.save(image);
				}

			}

		}

	}}
