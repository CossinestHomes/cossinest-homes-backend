package com.cossinest.homes.controller.user;


import com.cossinest.homes.payload.request.user.UserRequest;
import com.cossinest.homes.payload.request.user.UserRequestWithoutPassword;
import com.cossinest.homes.payload.request.user.UserPasswordAndResetRequest;
import com.cossinest.homes.payload.response.ResponseMessage;
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
    public ResponseEntity<UserResponse>getAuthenticatedUser(HttpServletRequest request){

        // UserResponse response = userService.getAuthenticatedUser(request);
        // return ResponseEntity.ok(response);

        return null;
    }



    @PutMapping("/auth") //request dto da sifre olcak mi
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','CUSTOMER')")
        public ResponseEntity<UserResponse>updateAuthenticatedUser(@Valid @RequestBody UserRequestWithoutPassword request, HttpServletRequest auth ){

      //  return userService.updateAuthenticatedUser(request,auth);
        return null;
    }

    @PatchMapping("/auth")
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','CUSTOMER')")
    public ResponseMessage<String>updateUserPassword(@Valid @RequestBody UserPasswordAndResetRequest request, HttpServletRequest auth){

       // return userService.updateUserPassword(request,auth);
        return null;
    }


    @DeleteMapping("auth") // bunun icin ayri dto mu acayim sadece password olan yoksa
    //@PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<String>deleteCustomer(HttpServletRequest auth, UserPasswordAndResetRequest request){

       //String message=userService.deleteCustomer(auth);
       // return ResponseEntity.ok(message);
        return null;
    }


    @GetMapping("/admin")
    //@PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseMessage<Page<UserResponse>>getAllAdminAndManagerByPage(
            HttpServletRequest request,
            @RequestParam(value = "q",required = false)String query,
            @RequestParam(value = "page",defaultValue = "0")int page,
            @RequestParam(value = "size",defaultValue = "0")int size,
            @RequestParam(value = "sort",defaultValue = "name")String sort,
            @RequestParam(value = "type",defaultValue = "desc")String type
    ){
       // return userService.getAllAdminAndManagerByPage(request,query,page,size,sort,type);
        return null;
    }

    @GetMapping("/{id}/admin")
    //@PreAuthorize("hasAnyAuthority('MANAGER',ADMIN')")
    public ResponseEntity<UserResponse>getUserById(@PathVariable Long id,HttpServletRequest request){

     // return userService.getUserById(id,request);
        return null;
    }



    @PutMapping("/{id}/admin")
    //@PreAuthorize("hasAnyAuthority('MANAGER',ADMIN')")
    public ResponseEntity<UserResponse>updateUser(@PathVariable Long id, UserRequest request,HttpServletRequest auth){

        // return userService.getUserById(id,request,auth);
        return null;
    }

    @DeleteMapping("auth")
    //@PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseEntity<String>deleteUser(@PathVariable Long id, HttpServletRequest auth){

        //String message=userService.deleteCustomer(id,auth);
        // return ResponseEntity.ok(message);
        return null;
    }

}



 //if (query != null && !query.isEmpty()) {}