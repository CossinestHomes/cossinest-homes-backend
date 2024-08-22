package com.cossinest.homes.contactMessage.controller;

import com.cossinest.homes.contactMessage.dto.ContactMessageRequest;
import com.cossinest.homes.contactMessage.dto.ContactMessageResponse;
import com.cossinest.homes.contactMessage.service.ContactMessageService;
import com.cossinest.homes.payload.response.ResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/contact-messages")
@RequiredArgsConstructor
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    @PostMapping("/contact-messages")    //http://localhost:8080/contact-messages + POST
    public ResponseMessage<ContactMessageResponse> save(@Valid @RequestBody ContactMessageRequest contactMessageRequest){
        return contactMessageService.save(contactMessageRequest);
    }

    @GetMapping("/contact-messages")  //http://localhost:8080/contact-messages + GET
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public Page<ContactMessageResponse> getAllMessages(
            @RequestParam(required = false) String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size" , defaultValue = "20") int size,
            @RequestParam(value = "sort" , defaultValue = "id") String sort,
            @RequestParam(value = "type" , defaultValue = "asc") String type
    ){
        return contactMessageService.getAllByQuery(query, page, size, sort, type);
    }

    @GetMapping("/contact-messages/{contactMessageId}") // //http://localhost:8080/contact-messages/1 + GET
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ContactMessageResponse getContactMessageById(@PathVariable Long contactMessageId){
        return contactMessageService.getContactMessageById(contactMessageId);
    }

    @DeleteMapping("/contact-messages/{contactMessageId}") // //http://localhost:8080/contact-messages/2 + DELETE
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<String> deleteContactMessageById(@PathVariable Long contactMessageId){
        return ResponseEntity.ok(contactMessageService.deleteById(contactMessageId));
    }


}
