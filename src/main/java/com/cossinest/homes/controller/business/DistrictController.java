package com.cossinest.homes.controller.business;

import com.cossinest.homes.payload.response.business.DistrictResponse;
import com.cossinest.homes.service.business.DistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/district")
@RequiredArgsConstructor
public class DistrictController {

    private final DistrictService districtService;

    @GetMapping("/getAll")
    public List<DistrictResponse> getAllDistricts(){
        return districtService.getAllDistricts();
    }
}
