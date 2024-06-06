package com.cossinest.homes.contactMessage.mapper;

import com.cossinest.homes.contactMessage.dto.ContactMessageRequest;
import com.cossinest.homes.contactMessage.dto.ContactMessageResponse;
import com.cossinest.homes.contactMessage.entity.ContactMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ContactMessageMapper {

    //Request DTO -> POJO
    public ContactMessage requestToContactMessage(ContactMessageRequest contactMessageRequest){
        return ContactMessage.builder()
                .first_name(contactMessageRequest.getFirst_name())
                .last_name(contactMessageRequest.getLast_name())
                .email(contactMessageRequest.getEmail())
                .message(contactMessageRequest.getMessage())
                .create_at(LocalDateTime.now())
                .build();
    }

    // POJO -> RESPONSE DTO

    public ContactMessageResponse contactMessageToResponse (ContactMessage contactMessage){
        return ContactMessageResponse.builder()
                .first_name(contactMessage.getFirst_name())
                .last_name(contactMessage.getLast_name())
                .email(contactMessage.getEmail())
                .message(contactMessage.getMessage())
                .build();
    }


}
