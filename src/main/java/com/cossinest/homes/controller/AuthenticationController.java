package com.cossinest.homes.controller;

import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.request.user.ForgetPasswordRequest;
import com.cossinest.homes.payload.request.user.ResetCodeRequest;
import com.cossinest.homes.payload.response.ResponseMessage;

import com.cossinest.homes.service.user.UserService;
import jakarta.validation.Valid;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/forgot-password")
    public ResponseMessage<String> forgotPassword(@Valid @RequestBody ForgetPasswordRequest request){

        return ResponseMessage.<String>builder()
                .message(SuccesMessages.RESET_CODE_HAS_BEEN_SENT)
                .status(HttpStatus.OK)
                .object( userService.forgotPassword(request))
                .build();
    }


    @PostMapping("/reset-password")
    ResponseEntity<String>resetPassword(@Valid @RequestBody ResetCodeRequest request){
        return userService.resetPassword(request);
    }

}
