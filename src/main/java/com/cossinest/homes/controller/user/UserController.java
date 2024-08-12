package com.cossinest.homes.controller.user;


import com.cossinest.homes.payload.request.user.*;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.user.AuthenticatedUsersResponse;
import com.cossinest.homes.payload.response.user.SignInResponse;
import com.cossinest.homes.payload.response.user.UserPageableResponse;
import com.cossinest.homes.payload.response.user.UserResponse;
import com.cossinest.homes.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;





    @PostMapping("/register") //http://localhost:8080/users/register
    public ResponseEntity<SignInResponse> registerUser (@RequestBody @Valid SignInRequest signInRequest){
       return userService.registerUser(signInRequest);

    }

    @GetMapping("/auth") //http://localhost:8080/users/auth ++
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','CUSTOMER')")
    public ResponseEntity<AuthenticatedUsersResponse> getAuthenticatedUser(HttpServletRequest request) {

        AuthenticatedUsersResponse response = userService.getAuthenticatedUser(request);
        return ResponseEntity.ok(response);

    }


    @PutMapping("/auth") //http://localhost:8080/users/auth ++
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','CUSTOMER')")
    public ResponseEntity<AuthenticatedUsersResponse> updateAuthenticatedUser(@Valid @RequestBody AuthenticatedUsersRequest request, HttpServletRequest auth) {

        return userService.updateAuthenticatedUser(request, auth);

    }

    @PatchMapping("/auth") //http://localhost:8080/users/auth ++
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','CUSTOMER')")
    public ResponseEntity<String> updateUserPassword(@Valid @RequestBody UserPasswordRequest request, HttpServletRequest auth) {

        return userService.updateUserPassword(request, auth);

    }


    @DeleteMapping("auth/customer") //http://localhost:8080/users/auth/customer ++
    @PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<String> deleteCustomer(HttpServletRequest auth, @RequestBody @Valid CustomerRequest request) {

        String message = userService.deleteCustomer(auth, request);
        return ResponseEntity.ok(message);

    }


    @GetMapping("/admin/qr") //http://localhost:8080/users/admin/qr?name=John&page=0&size=10&sort=firstName&type=desc ++
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseMessage<Page<UserPageableResponse>> getAllUsersByPageQueries(
            HttpServletRequest request,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "surname", required = false) String surname,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "createdAt") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        return userService.getAllAdminAndManagerQueriesByPage(request, name, surname, email, phone, page, size, sort, type);

    }


    @GetMapping("/admin") //http://localhost:8080/users/admin?q=John&page=0&size=10&sort=firstName&type=desc ++
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public Page<UserPageableResponse>getAllUsersByPage(
            HttpServletRequest request,
            @RequestParam(defaultValue = "") String q,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size,
            @RequestParam(value = "sort", defaultValue = "createdAt") String sort,
            @RequestParam(value = "type", defaultValue = "asc") String type)
    {
        return userService.getAllUsersByPage(request,q,page,size,sort,type);
    }


    @GetMapping("/{id}/admin") //http://localhost:8080/users/1/admin ++
    @PreAuthorize("hasAnyAuthority('MANAGER',ADMIN')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id, HttpServletRequest request) {

        return userService.getUserById(id, request);

    }


    @PutMapping("/{id}/admin") //http://localhost:8080/users/2/admin ++
    @PreAuthorize("hasAnyAuthority('MANAGER',ADMIN')")
    public ResponseMessage<UserResponse> updateUser(@PathVariable Long id, @RequestBody @Valid UsersUpdateRequest request, HttpServletRequest auth) {

        return userService.updateUser(id, request, auth);

    }

    @DeleteMapping("/{id}/admin") //http://localhost:8080/users/2/admin ++
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long id, HttpServletRequest auth) {

        UserResponse response = userService.deleteUserBusiness(id, auth);
        return ResponseEntity.ok(response);

    }

}



