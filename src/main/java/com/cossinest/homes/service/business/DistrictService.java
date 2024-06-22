package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.District;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.mappers.DistrictMapper;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.response.business.DistrictResponse;
import com.cossinest.homes.repository.business.DistrictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DistrictService {

    private final DistrictRepository districtRepository;
    private final DistrictMapper districtMapper;

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
}
