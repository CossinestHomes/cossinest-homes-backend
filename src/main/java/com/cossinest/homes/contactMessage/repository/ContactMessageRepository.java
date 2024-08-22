package com.cossinest.homes.contactMessage.repository;

import aj.org.objectweb.asm.commons.Remapper;
import com.cossinest.homes.contactMessage.entity.ContactMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ContactMessageRepository extends JpaRepository<ContactMessage , Long> {


    @Query("SELECT c FROM ContactMessage c WHERE " +
            "(:query IS NULL OR LOWER(c.message) LIKE LOWER(CONCAT('%', :query, '%')))")
    Page<ContactMessage> getAllMessageByQuery(@Param("query") String query, Pageable pageable);

}