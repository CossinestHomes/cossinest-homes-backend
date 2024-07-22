package com.cossinest.homes.controller;

import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.request.LoginRequest;
import com.cossinest.homes.payload.request.user.CodeRequest;
import com.cossinest.homes.payload.request.user.ForgetPasswordRequest;
import com.cossinest.homes.payload.request.user.ResetCodeRequest;
import com.cossinest.homes.payload.response.ResponseMessage;

import com.cossinest.homes.payload.response.user.AuthenticatedUsersResponse;
import com.cossinest.homes.service.AuthenticationService;
import com.cossinest.homes.service.user.UserService;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/login") //http://localhost:8080/auth/login
    public ResponseEntity<AuthenticatedUsersResponse> authenticateUser(@RequestBody @Valid LoginRequest loginRequest){
        return authenticationService.authenticateUser(loginRequest);
    }






    @PostMapping("/forgot-password")
    public ResponseMessage<String> forgotPassword(@Valid @RequestBody ForgetPasswordRequest request){

        return ResponseMessage.<String>builder()
                .message(SuccesMessages.RESET_CODE_HAS_BEEN_SENT)
                .status(HttpStatus.OK)
                .object( userService.forgotPassword(request))
                .build();
    }


    @PostMapping("/reset-password")
    ResponseEntity<String>resetPassword(@Valid @RequestBody CodeRequest request){
        return userService.resetPassword(request);
    }

}
