package com.cossinest.homes.controller.user;

import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.request.user.UserRequest;
import com.cossinest.homes.payload.request.user.UserRequestWithoutPassword;
import com.cossinest.homes.payload.request.user.UserUpdatePasswordRequest;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.user.UserResponse;
import com.cossinest.homes.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

private UserService userService;

    @GetMapping("/auth")
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','CUSTOMER')")
    public ResponseEntity<UserResponse>getUserByEmail(HttpServletRequest request){

     // UserResponse response = userService.getUserByEmail(request);
     // return ResponseEntity.ok(response);

      return null;
    }

    @PutMapping("/auth")
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','CUSTOMER')")
        public ResponseEntity<UserResponse>updateUser(@Valid @RequestBody UserRequestWithoutPassword request, HttpServletRequest auth ){

      //  return userService.updateAuthenticatedUser(request,auth);
        return null;
    }

    @PatchMapping("/auth")
    //@PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','CUSTOMER')")
    public ResponseMessage<UserResponse>updateUserPassword(@Valid @RequestBody UserRequest request){

       // return userService.updateUserPassword(request);
        return null;
    }


    @DeleteMapping("auth")
    //@PreAuthorize("hasAnyAuthority('CUSTOMER')")
    public ResponseEntity<String>deleteCustomer(@Valid @RequestBody UserUpdatePasswordRequest request,HttpServletRequest auth){

       //String message=userService.deleteCustomer(request,auth);
       // return ResponseEntity.ok(message);
        return null;
    }


    @GetMapping("/admin")
    //@PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseMessage<Page<ResponseMessage>>getAllAdminAndManagerByPage(
            HttpServletRequest request,
            @RequestParam(value = "page",defaultValue = "0")int page,
            @RequestParam(value = "size",defaultValue = "0")int size,
            @RequestParam(value = "sort",defaultValue = "name")String sort,
            @RequestParam(value = "type",defaultValue = "desc")String type
    ){
       // return userService.getAllAdminAndManagerByPage(request, page,size,sort,type);
        return null;
    }

}
