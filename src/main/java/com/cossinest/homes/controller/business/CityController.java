package com.cossinest.homes.controller.business;

import com.cossinest.homes.domain.concretes.business.City;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.service.business.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static io.micrometer.core.instrument.binder.http.HttpJakartaServletRequestTags.status;
import static java.util.stream.DoubleStream.builder;


@RequiredArgsConstructor
@RequestMapping("/city")
@RestController
public class CityController {

    @Autowired
    private  CityService cityService;

    @GetMapping("/getAll") //http://localhost:8080/cities/getAll
    public ResponseMessage<List<City>> getAllCity(){
        List<City> cityList =cityService.getAllCity();
        return ResponseMessage.<cityList>builder()
                .status(HttpStatus.OK)
                .object(cityList)
                .build();
}

}
