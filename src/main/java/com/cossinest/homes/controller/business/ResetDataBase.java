package com.cossinest.homes.controller.business;

import com.cossinest.homes.contactMessage.service.ContactMessageService;
import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.service.business.*;
import com.cossinest.homes.service.user.UserService;
import com.cossinest.homes.service.validator.UserRoleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("settings")
@RequiredArgsConstructor
public class ResetDataBase {

    private final ResetDbService resetDbService;

/*
private final ResetDbService resetDbService;

    @PostMapping("/db-reset")
    //@PreAuthorized(hasAnyAuthority('Admin'))
    public ResponseMessage<String>resetDataBase(HttpServletRequest request){

        resetDbService.resetDb(request);

        return ResponseMessage.<String>builder()
                .status(HttpStatus.OK)
                .message(SuccesMessages.DB_HAS_SUCCESSFULLY_RESET)
                .build();
    }
*/

    @DeleteMapping("/db-reset")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<String> resetDatabase() {
        return resetDbService.resetDatabase();
    }


}
