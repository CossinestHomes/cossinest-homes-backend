package com.cossinest.homes.payload.request.business;

import com.cossinest.homes.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AdvertRequest {



    @NotNull(message = "Title must not be empty")
    @Size(min=5, max= 150, message = "Title should be at least 5 chars")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+" ,message="Title must consist of the characters .")
    private String title;

    @NotNull(message = "Description must not be empty")
    @Size(max= 300, message = "Description should be at max 300 chars")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+" ,message="Description must consist of the characters .")
    private String desc;


    @NotNull(message = "Slug must not be empty")
    @Size(min=5, max= 200, message = "Slug url should be at least 5 chars")
    private String slug;

    @NotNull(message = "Price must not be empty")
    @Min(value = 1)
    private Double price;


    @NotNull(message = "Status must not be empty")
    private Status status;

    @NotNull(message = "Built in must not be empty")
    private Boolean builtIn;

    @NotNull(message = "Is active must not be empty")
    private Boolean isActive;

    @NotNull(message = "View count must not be empty")
    private Integer viewCount;

    @NotNull(message = "Please enter location")
    private String location;

    @NotNull(message = "Advert type ID is required")
    @Min(value = 1, message = "Advert type ID must be greater than or equal to 1")
    private int advertTypeId;

    @NotNull(message = "Country ID is required")
    @Min(value = 1, message = "Country ID must be greater than or equal to 1")
    private int countryId;

    @NotNull(message = "City ID is required")
    @Min(value = 1, message = "City ID must be greater than or equal to 1")
    private int cityId;

    @NotNull(message = "District ID is required")
    @Min(value = 1, message = "District ID must be greater than or equal to 1")
    private int districtId;

    @NotNull(message = "User ID is required")
    @Min(value = 1, message = "User ID must be greater than or equal to 1")
    private int userId;

    @NotNull(message = "Category ID is required")
    @Min(value = 1, message = "Category ID must be greater than or equal to 1")
    private int categoryId;


    //TODO: favourites,images
}
