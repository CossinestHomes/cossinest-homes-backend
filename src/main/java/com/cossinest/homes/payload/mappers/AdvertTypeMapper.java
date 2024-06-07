package com.cossinest.homes.payload.mappers;

import com.cossinest.homes.domain.concretes.business.AdvertType;
import com.cossinest.homes.payload.request.business.AdvertTypeRequest;
import com.cossinest.homes.payload.response.business.AdvertTypeResponse;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Component
public class AdvertTypeMapper {

        //DTO-> POJO

    public AdvertType mapAdvertTypeRequestToAdvertType(AdvertTypeRequest advertTypeRequest){
            return AdvertType.builder()
                    .title(advertTypeRequest.getTitle())
                    .build();
    }

        //POJO -> DTO

    public AdvertTypeResponse mapAdvertTypeToAdvertTypeResponse(AdvertType advertType){
        return AdvertTypeResponse.builder()
                .id(advertType.getId())
                .title(advertType.getTitle())
                .build();
    }

    //request -> updated pojo

    public AdvertType mapAdvertRequestToUpdatedAdvertType(Long id, AdvertTypeRequest advertTypeRequest){
        return AdvertType.builder()
                .id(id)
                .title(advertTypeRequest.getTitle())
                .build();
}


}
