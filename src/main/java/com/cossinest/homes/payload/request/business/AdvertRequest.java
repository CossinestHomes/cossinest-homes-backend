package com.cossinest.homes.payload.request.business;

import com.cossinest.homes.domain.concretes.business.AdvertType;
import com.cossinest.homes.domain.enums.Status;
import com.cossinest.homes.payload.request.abstracts.AbstractAdvertRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

//@Data

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class AdvertRequest extends AbstractAdvertRequest {


}