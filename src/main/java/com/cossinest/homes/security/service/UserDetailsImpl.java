package com.cossinest.homes.security.service;

import com.cossinest.homes.domain.concretes.user.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @JsonIgnore // password JSONla beraber gönderilmesin, hassas bilgi.
    private String passwordHash;
    private Collection<? extends GrantedAuthority> authorities;

    private Set<UserRole> roles; // Kullanıcı rolleri

    public UserDetailsImpl(Long id, String firstName, String lastName, String email, Set<UserRole> role, String passwordHash) {
        this.id=id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.passwordHash=passwordHash;
        this.authorities = buildGrantedAuthorities(role);

    }

    private static List<SimpleGrantedAuthority> buildGrantedAuthorities(final Set<UserRole> roles) {

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (UserRole role : roles) {
            authorities.add(
                    new SimpleGrantedAuthority(role.getRoleType().name())
            );
        }
        return authorities;

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public boolean equals(Object o){
        if(this== o){
            return true;
        }
        if(o == null || getClass() != o.getClass()){
            return false;
        }

        UserDetailsImpl user= (UserDetailsImpl) o;
        return Objects.equals(id,user.getId());

    }
}
