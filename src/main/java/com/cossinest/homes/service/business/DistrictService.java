package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.City;
import com.cossinest.homes.domain.concretes.business.Country;
import com.cossinest.homes.domain.concretes.business.District;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.mappers.DistrictMapper;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.request.business.DistrictRequest;
import com.cossinest.homes.payload.response.business.DistrictResponse;
import com.cossinest.homes.repository.business.DistrictRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;
    private final CityService cityService;

    public List<DistrictResponse> getAllDistricts() {
            return districtRepository.findAll()
                    .stream()
                    .map(districtMapper::mapDistrictToDistrictResponse)
                    .collect(Collectors.toList());
    }

    public District getDistrictByIdForAdvert(Long districtId) {
       return districtRepository.findById(districtId).orElseThrow(()->new ResourceNotFoundException(ErrorMessages.DISTRICT_NOT_FOUND));
    }

    public void resetDistrictTables() {
        districtRepository.deleteAll();
    }

    public int countAllDistricts() {
       return districtRepository.countAllDistricts();
    }

    public void setBuiltInForDistrict() {
        // Türkiye'nin ID'si 1 olduğundan emin olun
        Long districtId = 1L;

        District district = districtRepository.findById(districtId).orElseThrow(() -> new RuntimeException(ErrorMessages.DISTRICT_NOT_FOUND));
        district.setBuilt_in(Boolean.TRUE);
        districtRepository.save(district);
    }

//    @Transactional
//    public District save(DistrictRequest districtRequest) {
//        District district = new District();
//
//        City city = cityService.getCityById((long) districtRequest.getCity_id());
//
//        if (city != null) {
//            district.setCity(city);
//        } else {
//
//            throw new ResourceNotFoundException("City not found with id: " + districtRequest.getCity_id());
//        }
//
//        district.setName(districtRequest.getName());
//
//        return districtRepository.save(district);
//    }
}
