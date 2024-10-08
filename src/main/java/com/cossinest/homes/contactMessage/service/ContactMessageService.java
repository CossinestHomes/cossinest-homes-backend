package com.cossinest.homes.contactMessage.service;

import com.cossinest.homes.contactMessage.dto.ContactMessageRequest;
import com.cossinest.homes.contactMessage.dto.ContactMessageResponse;
import com.cossinest.homes.contactMessage.entity.ContactMessage;
import com.cossinest.homes.contactMessage.mapper.ContactMessageMapper;
import com.cossinest.homes.contactMessage.messages.Messages;
import com.cossinest.homes.contactMessage.repository.ContactMessageRepository;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.service.helper.PageableHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ContactMessageService {

    private final ContactMessageRepository contactMessageRepository;
    private final ContactMessageMapper contactMessageMapper;
    private final PageableHelper pageableHelper;

    public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest) {
        ContactMessage contactMessage = contactMessageMapper.requestToContactMessage(contactMessageRequest);
        ContactMessage savedMessage = contactMessageRepository.save(contactMessage);

        return ResponseMessage.<ContactMessageResponse>builder()
                .message("Contact message is created")
                .status(HttpStatus.CREATED)
                .object(contactMessageMapper.contactMessageToResponse(savedMessage))
                .build();
    }



    public ContactMessageResponse getContactMessageById(Long contactMessageId) {
        return contactMessageRepository.findById(contactMessageId).map(contactMessageMapper::contactMessageToResponse).orElseThrow(()->
                new ResourceNotFoundException(Messages.NOT_FOUND_CONTACT_MESSAGE_BY_ID));
    }

    public String deleteById(Long contactMessageId) {
        getContactMessageById(contactMessageId);
        contactMessageRepository.deleteById(contactMessageId);
        return Messages.DELETE_CONTACT_MESSAGE_BY_ID;
    }

    public Page<ContactMessageResponse> getAllByQuery(String query, int page, int size, String sort, String type) {
            Pageable pageable= pageableHelper.getPageableWithProperties(page, size, sort, type);

                Page<ContactMessageResponse> messages = contactMessageRepository.getAllMessageByQuery(query, pageable).map(contactMessageMapper::contactMessageToResponse);

                return messages;
    }

    public void resetContactMessageTables() {
        contactMessageRepository.deleteAll();
    }
}

