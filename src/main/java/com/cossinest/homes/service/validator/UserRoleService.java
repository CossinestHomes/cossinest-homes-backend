package com.cossinest.homes.service.validator;

import com.cossinest.homes.domain.concretes.user.UserRole;
import com.cossinest.homes.domain.enums.RoleType;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.repository.user.UserRoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.module.ResolutionException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRole getUserRole(RoleType roleType)  {
        return  userRoleRepository.findByEnumRoleEquals(roleType).orElseThrow(
                ()-> new ResourceNotFoundException(ErrorMessages.ROLE_NOT_FOUND)
        );
    }


    public List<UserRole> getAllUserRoles(){
        return userRoleRepository.findAll();
    }
}