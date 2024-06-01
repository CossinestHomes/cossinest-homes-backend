package com.cossinest.homes.repository.user;

import com.cossinest.homes.domain.concretes.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail();

    boolean existsByPhone();



    @Query("SELECT u FROM User u WHERE (:name IS NULL OR u.firstName=:name)AND" +
            "(:surname IS NULL OR u.lastName=:surname)AND" +
            "(:email IS NULL OR u.email=:email)OR" +
            "(:phone IS NULL OR u.phone=:phone)")
    Page<User> findAll(@Param(value = "name") String name,
                       @Param(value = "surname") String surname,
                       @Param(value = "email") String email,
                       @Param(value = "phone") String phone,
                       Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.resetPasswordCode=:resetPasswordCode")
    Optional<User>resetPasswordWithCode(@Param(value ="resetPasswordCode" ) String resetPasswordCode);
}
