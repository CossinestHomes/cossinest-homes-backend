package com.cossinest.homes.service.helper;

import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.exception.BadRequestException;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.repository.user.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class MethodHelper {

    private UserRepository userRepository ;

    public User findByUserByEmail(String email) {

        return userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_EMAIL, email)));
    }

    public String getEmailByRequest(HttpServletRequest request){
        return (String) request.getAttribute("email");
    }

    public void isBuiltIn(User user) {
        if (user.getBuilt_in()) throw new BadRequestException(ErrorMessages.BUILT_IN_USER_CAN_NOT_UPDATE);
    }


}