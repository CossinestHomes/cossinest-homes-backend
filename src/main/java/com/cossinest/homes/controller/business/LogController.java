package com.cossinest.homes.controller.business;
import com.cossinest.homes.domain.concretes.business.Log;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class LogController {

private final Log log;

@GetMapping
//@PreAuthorized()
public ResponseEntity<>getStaticts(){}


}
