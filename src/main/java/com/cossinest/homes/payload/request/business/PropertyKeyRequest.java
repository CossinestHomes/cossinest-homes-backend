package com.cossinest.homes.payload.request.business;

import com.cossinest.homes.domain.concretes.business.Category;
import com.cossinest.homes.domain.concretes.business.CategoryPropertyValue;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PropertyKeyRequest {

    private String propertyName;

    private Boolean builtIn = false;

    private Category category;

    private List<CategoryPropertyValue> categoryPropertyValues = new ArrayList<>();


}
