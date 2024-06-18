package com.cossinest.homes.payload.request.business;

import com.cossinest.homes.domain.concretes.business.Images;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class CreateAdvertRequest {

@NotNull
@Size(max = 150)
private String title;

private String desc;

private Long cityId;

private Long countryId;

private Long categoryId;

private Long districtId;

private Long advertTypeId;

private Double price;

private String location;

private Images images; //TODO burda ne alalim id mi image mi?

private List<CreateAdvertPropertyRequest> advertPropertyRequest;
}
