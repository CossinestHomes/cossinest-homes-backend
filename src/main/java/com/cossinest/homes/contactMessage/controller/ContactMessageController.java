package com.cossinest.homes.contactMessage.controller;

import com.cossinest.homes.contactMessage.dto.ContactMessageRequest;
import com.cossinest.homes.contactMessage.dto.ContactMessageResponse;
import com.cossinest.homes.contactMessage.service.ContactMessageService;
import com.cossinest.homes.payload.response.ResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
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
    //TODO: manager ve admin yapacak
    public Page<ContactMessageResponse> getAllMessages(
            @RequestParam(required = false) String q,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size" , defaultValue = "20") int size,
            @RequestParam(value = "sort" , defaultValue = "category_id") Long sort,
            @RequestParam(value = "type" , defaultValue = "ASC") String type
    ){
        return contactMessageService.getAllByQuery(q, page, size, sort, type);
    }

    @GetMapping("/contact-messages/{contactMessageId}") // //http://localhost:8080/contact-messages/1 + GET
    //TODO: manager ve admin yapacak
    public ContactMessageResponse getContactMessageById(@PathVariable Long contactMessageId){
        return contactMessageService.getContactMessageById(contactMessageId);
    }

    @DeleteMapping("/contact-messages/{contactMessageId}") // //http://localhost:8080/contact-messages/2 + DELETE
    //TODO: manager ve admin yapacak
    public ResponseEntity<String> deleteContactMessageById(@PathVariable Long contactMessageId){
        return ResponseEntity.ok(contactMessageService.deleteById(contactMessageId));
    }


}
