package com.cossinest.homes.repository.user;

import com.cossinest.homes.domain.concretes.user.User;
import com.cossinest.homes.domain.concretes.user.UserRole;
import com.cossinest.homes.domain.enums.RoleType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);



    @Query("SELECT u FROM User u WHERE (:name IS NULL OR u.firstName=:name)OR" +
            "(:surname IS NULL OR u.lastName=:surname)OR" +
            "(:email IS NULL OR u.email=:email)OR" +
            "(:phone IS NULL OR u.phone=:phone)")
    Page<User> findAll(@Param(value = "name") String name,
                       @Param(value = "surname") String surname,
                       @Param(value = "email") String email,
                       @Param(value = "phone") String phone,
                       Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.resetPasswordCode=:resetPasswordCode")
    Optional<User>resetPasswordWithCode(@Param(value ="resetPasswordCode" ) String resetPasswordCode);

    @Query("SELECT u FROM User u WHERE " +
            "(:query IS NULL OR LOWER(u.firstName) LIKE :query OR " +
            "LOWER(u.lastName) LIKE :query OR " +
            "LOWER(u.email) LIKE :query OR " +
            "LOWER(u.phone) LIKE :query)")

    Page<User> findAll(@Param("query") String query, Pageable pageable);

 /*   @Query("SELECT u FROM User u WHERE u.userRole=?1")
    List<User> findByUserRole(UserRole userRole);*/

  /*  @Query("SELECT u FROM User u WHERE u.userRole.roleType = :roleType")
    List<User> findByUserRole(@Param("roleType") RoleType roleType);*/

    @Modifying
    @Query("DELETE FROM User u WHERE u.builtIn = ?1")
    void deleteByBuiltIn(boolean b);

    List<User> findByUserRole_RoleType(RoleType roleType);

    @Query("SELECT COUNT(u) FROM User u JOIN u.userRole r WHERE r.roleType=?1")
    Long countAllAdmins(RoleType roleType);
}
