package com.cossinest.homes.payload.request.business;

import com.cossinest.homes.payload.request.abstracts.BaseAdvertRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class AdvertRequestForAdmin extends BaseAdvertRequest {
}
