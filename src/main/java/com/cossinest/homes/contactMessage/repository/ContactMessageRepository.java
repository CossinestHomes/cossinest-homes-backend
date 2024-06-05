package com.cossinest.homes.contactMessage.repository;

import com.cossinest.homes.contactMessage.entity.ContactMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;



public interface ContactMessageRepository extends JpaRepository<ContactMessage , Long> {

    @Query("SELECT c FROM ContactMessage c WHERE" +
    ":q IS NULL OR LOWER(c.message) LIKE LOWER(CONCAT('%', :q, '%'))")
    Page <ContactMessage> getAllMessageByQuery(String q, Pageable pageable);
}