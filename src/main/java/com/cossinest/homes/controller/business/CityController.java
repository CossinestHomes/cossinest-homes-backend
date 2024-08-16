package com.cossinest.homes.controller.business;

import com.cossinest.homes.domain.concretes.business.City;
import com.cossinest.homes.payload.request.business.CityAdvertTotalRequest;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.CityAdvertTotalResponse;
import com.cossinest.homes.service.business.CityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static io.micrometer.core.instrument.binder.http.HttpJakartaServletRequestTags.status;
import static java.util.stream.DoubleStream.builder;


@RequiredArgsConstructor
@RequestMapping("/city")
@RestController
public class CityController {

    @Autowired
    private  CityService cityService;

    @GetMapping("/getAll") //http://localhost:8080/city/getAll
    public ResponseMessage<List<City>> getAllCity(){
        List<City> cityList =cityService.getAllCity();
        return ResponseMessage.<List<City>>builder()
                .status(HttpStatus.OK)
                .object(cityList)
                .build();
}
    @GetMapping("/getByCity/{countryId}") //http://localhost:8080/city/getByCity/1
    public ResponseMessage<List<City>> getByCity(@PathVariable Long countryId){
        List<City> cityList =cityService.getByCity(countryId);
        return ResponseMessage.<List<City>>builder()
                .status(HttpStatus.OK)
                .object(cityList)
                .build();
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseMessage<List<CityAdvertTotalResponse>>getCityAdvertTotal(@Valid @RequestBody CityAdvertTotalRequest totalRequest,
                                                                            HttpServletRequest request){
        return ResponseMessage.<List<CityAdvertTotalResponse>>builder()
                .status(HttpStatus.OK)
                .object(cityService.getCitiesAdvertsTotal(totalRequest,request))
                .build();

    }

}
