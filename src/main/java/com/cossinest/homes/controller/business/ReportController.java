package com.cossinest.homes.controller.business;

import com.cossinest.homes.service.business.ReportService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService logService;


    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN,MANAGER')")
    public ResponseEntity<Map<String, Long>> getStaticts(HttpServletRequest request) {

        return logService.getStaticts(request);

    }


    @GetMapping("/adverts")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseEntity<byte[]> getAdverts(@RequestParam(value = "date1", required = false) String date1,
                                             @RequestParam(value = "date2", required = false) String date2,
                                             @RequestParam(value = "category", required = false) String category,
                                             @RequestParam(value = "type", required = false) String type,
                                             @RequestParam(value = "status", required = false) String status,
                                             HttpServletRequest request) {

        return logService.getAdverts(request, date1, date2, category, type, status);
    }


    @GetMapping("/most-popular-properties")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseEntity<byte[]> getReportOfPopulerAdverts(@RequestParam(value = "amount") int amount, HttpServletRequest request) {

        return logService.getPopulerAdvertsReport(amount, request);

    }


    @GetMapping("/users")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseEntity<byte[]> getUsers(@RequestParam("rol") String rol, HttpServletRequest request) {

        return logService.getUsersWithRol(rol, request);


    }


    @GetMapping("/tour-requests")
    @PreAuthorize("hasAnyAuthority('MANAGER','ADMIN')")
    public ResponseEntity<byte[]> getTourRequest(@RequestParam(value = "date1", required = false) String date1,
                                                 @RequestParam(value = "date2", required = false) String date2,
                                                 @RequestParam(value = "status", required = false) String status,
                                                 HttpServletRequest request) {


        return logService.getTourRequest(request, date1, date2, status);

    }


}
