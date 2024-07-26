package com.cossinest.homes.service;

import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.exception.MailServiceException;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.mappers.UserMapper;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.request.LoginRequest;
import com.cossinest.homes.payload.response.user.AuthenticatedUsersResponse;
import com.cossinest.homes.payload.response.user.UserResponse;
import com.cossinest.homes.repository.user.UserRepository;
import com.cossinest.homes.security.jwt.JwtUtils;
import com.cossinest.homes.security.service.UserDetailsImpl;
import com.cossinest.homes.service.user.EmailService;
import com.cossinest.homes.service.user.EmailServiceInterface;
import com.cossinest.homes.utils.MailUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    //TODO loginde phone null geliyor

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final EmailServiceInterface emailServiceInterface;

    public ResponseEntity<AuthenticatedUsersResponse> authenticateUser(LoginRequest loginRequest){
        //login için gerekli olan email ve password LoginRequest classı üzerinden alınıyor.
        String email= loginRequest.getEmail();
        String password= loginRequest.getPassword();
        // authenticationManager user ı valide ediyor
        Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
        // valide edilen user Security Contexte atılıyor. Bu, uygulamanın geri kalanında kimlik doğrulamanın geçerli olmasını sağlar.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // JWT token oluşturuluyor
        String token="Bearer " + jwtUtils.generateJwtToken(authentication);
        // authentication nesnesinden doğrulanan kullanıcının detayları alınır getPrincipal() ile. Bu, UserDetailsImpl sınıfına dönüştürülür.
        UserDetailsImpl userDetails= (UserDetailsImpl) authentication.getPrincipal();
        //Kullanıcının sahip olduğu roller (GrantedAuthority nesneleri), String nesnelerine dönüştürülerek bir Set içine toplanır.
        Set<String> roles= userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        // roles koleksiyonundan ilk rol (varsa) alınır ve bir Optional nesnesine konur.
        Optional<String> role= roles.stream().findFirst();

        AuthenticatedUsersResponse.AuthenticatedUsersResponseBuilder authResponse= AuthenticatedUsersResponse.builder();
        authResponse.id(userDetails.getId());

        authResponse.built_in(userDetails.getBuilt_in());

        authResponse.email(userDetails.getEmail());
        authResponse.token(token.substring(7));
        authResponse.firstName(userDetails.getFirstName());
        authResponse.lastName(userDetails.getLastName());
        authResponse.userRole(roles);
        authResponse.phone(userDetails.getPhone());
        authResponse.built_in(userDetails.getBuiltIn());


        try {
            MimeMessagePreparator registrationEmail = MailUtil.buildRegistrationEmail(userDetails.getEmail());
            emailServiceInterface.sendEmail(registrationEmail);
        } catch (Exception e) {
            throw new MailServiceException(e.getMessage());
        }

        return ResponseEntity.ok(authResponse.build());

    }

}
