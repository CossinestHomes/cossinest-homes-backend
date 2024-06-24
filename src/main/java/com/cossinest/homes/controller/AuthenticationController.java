package com.cossinest.homes.controller;

import com.cossinest.homes.payload.request.user.ForgetPasswordRequest;
import com.cossinest.homes.payload.request.user.ResetCodeRequest;
import com.cossinest.homes.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor   //Requestmapping lazim mi
public class AuthenticationController {

    private final UserService userService;
    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@Valid @RequestBody ForgetPasswordRequest request){

        return ResponseEntity.ok(userService.forgotPassword(request));
    }


    @PostMapping("/reset-password")
    ResponseEntity<String>resetPassword(@Valid @RequestBody ResetCodeRequest request){
        return userService.resetPassword(request);
    }

}
