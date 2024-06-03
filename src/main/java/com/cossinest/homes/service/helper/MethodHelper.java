package com.cossinest.homes.service.helper;

import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.concretes.user.UserRole;
import com.cossinest.homes.domain.enums.RoleType;
import com.cossinest.homes.exception.BadRequestException;
import com.cossinest.homes.exception.ConflictException;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.request.user.AuthenticatedUsersRequest;
import com.cossinest.homes.payload.request.user.CustomerRequest;
import com.cossinest.homes.payload.response.user.AuthenticatedUsersResponse;
import com.cossinest.homes.repository.user.UserRepository;
import com.cossinest.homes.service.validator.UserRoleService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.management.relation.Role;
import java.util.*;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class MethodHelper {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;

    public User findByUserByEmail(String email) {

        return userRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException(String.format(ErrorMessages.NOT_FOUND_USER_EMAIL, email)));
    }

    public String getEmailByRequest(HttpServletRequest request) {
        return (String) request.getAttribute("email");
    }

    public boolean isBuiltIn(User user) {

        return user.getBuilt_in();
    }


    public User getUserByHttpRequest(HttpServletRequest request) {
        return findByUserByEmail(getEmailByRequest(request));

    }


    public void checkDuplicate(String email, String phone) {

        if (userRepository.existsByEmail()) {
            throw new ConflictException(String.format(ErrorMessages.THIS_EMAIL_IS_ALREADY_TAKEN, email));
        }
        if (userRepository.existsByPhone()) {
            throw new ConflictException(String.format(ErrorMessages.THIS_PHONE_NUMBER_IS_ALREADY_TAKEN, phone));

        }

    }


    public void checkUniqueProperties(User user, AuthenticatedUsersRequest request) {

        boolean changed = false;
        String changedEmail = "";
        String changedPhone = "";

        if (user.getEmail().equalsIgnoreCase(request.getEmail())) {
            changed = true;
            changedEmail = request.getEmail();
        }

        if (user.getPhone().equalsIgnoreCase(request.getPhone())) {
            changed = true;
            changedPhone = request.getEmail();
        }

        if (changed) {
            checkDuplicate(changedEmail, changedPhone);
        }


    }


    public void checkEmailAndPassword(User user, CustomerRequest request) {

        if (!(user.getEmail().equals(request.getEmail())))
            throw new BadRequestException(String.format(ErrorMessages.EMAIL_IS_INCORRECT, request.getEmail()));
        if (!(Objects.equals(user.getPasswordHash(), request.getPassword())))
            throw new BadRequestException(ErrorMessages.PASSWORD_IS_NOT_CORRECT);

    }

    public User findUserWithId(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(String.format(ErrorMessages.USER_IS_NOT_FOUND, id)));
    }


    public void checkRoles(User user, RoleType... roleTypes) {

        Set<RoleType> roles = new HashSet<>();
        Collections.addAll(roles,roleTypes);

       for (UserRole userRole:user.getUserRole()){
           if (roles.contains(userRole.getRoleType())) return;
       }
       throw new ResourceNotFoundException(ErrorMessages.ROLE_NOT_FOUND);
    }



    public Set<UserRole> roleStringToUserRole(Set<String> request) {

        return request.stream().map(item -> userRoleService.getUserRole(RoleType.valueOf(item))).collect(Collectors.toSet());
    }


    public void UpdatePasswordControl(String password, String reWritePassword) {
        if(!Objects.equals(password,reWritePassword)){
            throw new BadRequestException(ErrorMessages.PASSWORDS_DID_NOT_MATCH);
        }
    }

    //Advert
    public int calculatePopularityPoint(int advertTourRequestListSize,int advertViewCount){
        return (3*advertTourRequestListSize)+advertViewCount;
    }
}
