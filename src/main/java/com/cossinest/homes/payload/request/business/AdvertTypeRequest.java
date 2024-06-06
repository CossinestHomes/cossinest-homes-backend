package com.cossinest.homes.payload.request.business;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AdvertTypeRequest {

    @NotNull(message = "Please enter title")
    @Size(min = 2 , max = 30 , message = "Title must have min 2 chars and max 30 chars")
    @Pattern(regexp = "\\A(?!\\s*\\Z).+" ,message="Title must consist of the characters .")
    private String title;
}
