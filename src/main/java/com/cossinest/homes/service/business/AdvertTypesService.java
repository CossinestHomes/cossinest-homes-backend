package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.AdvertType;
import com.cossinest.homes.exception.BadRequestException;
import com.cossinest.homes.exception.ConflictException;
import com.cossinest.homes.exception.ResourceNotFoundException;
import com.cossinest.homes.payload.mappers.AdvertTypeMapper;
import com.cossinest.homes.payload.messages.ErrorMessages;
import com.cossinest.homes.payload.messages.SuccesMessages;
import com.cossinest.homes.payload.request.business.AdvertTypeRequest;
import com.cossinest.homes.payload.response.ResponseMessage;
import com.cossinest.homes.payload.response.business.AdvertTypeResponse;
import com.cossinest.homes.repository.business.AdvertTypesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdvertTypesService {

    private final AdvertTypesRepository advertTypesRepository;
    private final AdvertTypeMapper advertTypeMapper;

    public List<AdvertTypeResponse> getAllAdvertTypes() {
        return advertTypesRepository.findAll()
                .stream()
                .map(advertTypeMapper::mapAdvertTypeToAdvertTypeResponse)
                .collect(Collectors.toList());
    }

    public AdvertTypeResponse getAdvertTypeById(Long id) {
        AdvertType advertType= isAdvertTypeExists(id);
        return advertTypeMapper.mapAdvertTypeToAdvertTypeResponse(advertType);
    }

    //yardımcı metod: id ile advert type arama
    private AdvertType isAdvertTypeExists(Long id){
            return advertTypesRepository.findById(id).orElseThrow(()->
                    new ResourceNotFoundException(String.format(ErrorMessages.ADVERT_TYPE_NOT_FOUND_BY_ID , id)));
    }

    public ResponseMessage<AdvertTypeResponse> saveAdvertType(AdvertTypeRequest advertTypeRequest) {
        isAdvertTypeExistsByTitle(advertTypeRequest.getTitle());

        AdvertType savedAdvertType= advertTypesRepository.save(advertTypeMapper.mapAdvertTypeRequestToAdvertType(advertTypeRequest));

        return ResponseMessage.<AdvertTypeResponse>builder()
                .message(SuccesMessages.ADVERT_TYPE_SAVED)
                .status(HttpStatus.CREATED)
                .object(advertTypeMapper.mapAdvertTypeToAdvertTypeResponse(savedAdvertType))
                .build();
    }

    //yardımcı metod: title ile advert type arama
        public boolean isAdvertTypeExistsByTitle(String title){
                boolean advertTypeTitleExist= advertTypesRepository.existsByTitle(title);

                if (advertTypeTitleExist){
                    throw new ConflictException(ErrorMessages.ADVERT_TYPE_ALREADY_EXIST);
                }else {
                    return false;
                }
            }


    public AdvertTypeResponse updateAdvertTypeById(Long id, AdvertTypeRequest advertTypeRequest) {
        AdvertType advertType= isAdvertTypeExists(id);

        if(
                !(advertType.getTitle().equalsIgnoreCase(advertTypeRequest.getTitle())) &&
                        (advertTypesRepository.existsByTitle(advertTypeRequest.getTitle()))){
                    throw new ConflictException(ErrorMessages.ADVERT_TYPE_ALREADY_EXIST);
        }

        AdvertType updatedAdvertType= advertTypeMapper.mapAdvertRequestToUpdatedAdvertType(id, advertTypeRequest);

        AdvertType savedAdvertType= advertTypesRepository.save(updatedAdvertType);

        return advertTypeMapper.mapAdvertTypeToAdvertTypeResponse(savedAdvertType);
    }

    public ResponseMessage deleteAdvertTypeById(Long id) {
        isAdvertTypeExists(id);
        advertTypesRepository.deleteById(id);

        return ResponseMessage.builder()
                .message(SuccesMessages.ADVERT_TYPE_DELETED)
                .status(HttpStatus.OK)
                .build();

    }

    //Advert için yazıldı
    public AdvertType getAdvertTypeByIdForAdvert(Long advertTypeId) {
        return isAdvertTypeExists(advertTypeId);
    }

    @Transactional
    public void resetAdvertTypesTables() {
   //     advertTypesRepository.deleteByBuiltIn(false);
    }

    public AdvertType findByTitle(String type) {

      return   advertTypesRepository.findByTitle(type).orElseThrow(()-> new BadRequestException("AdvertType is not found."));

    }

    public void saveAdvertTypeRunner(AdvertType advertType) {
        advertTypesRepository.save(advertType);
    }

    public AdvertType findByIdAdvertType(long l) {
       return advertTypesRepository.findById(l).orElseThrow(()->new ResourceNotFoundException("AdvertType is not found"));
    }
}
