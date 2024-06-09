package com.cossinest.homes.controller.business;
import com.cossinest.homes.domain.concretes.business.Log;
import com.cossinest.homes.payload.response.business.LogStaticResponse;
import com.cossinest.homes.service.business.LogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class LogController {

private final LogService logService;

@GetMapping
//@PreAuthorized(hasAnyAuthority('MANAGER','ADMIN')
public ResponseEntity<Map<String,Long>>getStaticts(HttpServletRequest request){

return logService.getStaticts(request);

}


}
