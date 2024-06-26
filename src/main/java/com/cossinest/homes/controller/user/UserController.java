package com.cossinest.homes.controller.user;


import com.cossinest.homes.payload.request.user.*;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.user.AuthenticatedUsersResponse;
import com.cossinest.homes.payload.response.user.UserPageableResponse;
import com.cossinest.homes.payload.response.user.UserResponse;
import com.cossinest.homes.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @GetMapping("/auth")
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','CUSTOMER')")
    public ResponseEntity<AuthenticatedUsersResponse> getAuthenticatedUser(HttpServletRequest request) {

        AuthenticatedUsersResponse response = userService.getAuthenticatedUser(request);
        return ResponseEntity.ok(response);


    }


    @PutMapping("/auth")
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','CUSTOMER')")
    public ResponseEntity<AuthenticatedUsersResponse> updateAuthenticatedUser(@Valid @RequestBody AuthenticatedUsersRequest request, HttpServletRequest auth) {

        return userService.updateAuthenticatedUser(request, auth);

    }

    @PatchMapping("/auth")
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','CUSTOMER')")
    public ResponseEntity<String> updateUserPassword(@Valid @RequestBody UserPasswordRequest request, HttpServletRequest auth) {

        return userService.updateUserPassword(request, auth);

    }


    @DeleteMapping("auth/customer")
    //@PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<String> deleteCustomer(HttpServletRequest auth, CustomerRequest request) {

        String message = userService.deleteCustomer(auth, request);
        return ResponseEntity.ok(message);

    }


    @GetMapping("/admin/qr")
    //@PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseMessage<Page<UserPageableResponse>> getAllUsersByPageQueries(
            HttpServletRequest request,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "surname", required = false) String surname,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "0") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        return userService.getAllAdminAndManagerQueriesByPage(request, name, surname, email, phone, page, size, sort, type);

    }


    @GetMapping("/admin")
    //@PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseEntity<Page<UserPageableResponse>>getAllUsersByPage(
            HttpServletRequest request,
            @RequestParam(value = "q", required = false) String q,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "0") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ){
        return userService.getAllUsersByPage(request,q,page,size,sort,type);
    }


    @GetMapping("/{id}/admin")
    //@PreAuthorize("hasAnyAuthority('MANAGER',ADMIN')")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id, HttpServletRequest request) {

        return userService.getUserById(id, request);

    }


    @PutMapping("/{id}/admin")
    //@PreAuthorize("hasAnyAuthority('MANAGER',ADMIN')")
    public ResponseMessage<UserResponse> updateUser(@PathVariable Long id, UsersUpdateRequest request, HttpServletRequest auth) {

        return userService.updateUser(id, request, auth);

    }

    @DeleteMapping("auth/user")
    //@PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseEntity<UserResponse> deleteUser(@PathVariable Long id, HttpServletRequest auth) {

        UserResponse response = userService.deleteUserBusiness(id, auth);
        return ResponseEntity.ok(response);

    }

}



