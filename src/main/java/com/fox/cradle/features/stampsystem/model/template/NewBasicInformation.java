package com.fox.cradle.features.stampsystem.model.template;

import com.fox.cradle.features.stampsystem.model.enums.StampCardCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewBasicInformation
{
    @NotBlank(message = "Name is required")
    @Size(max = 20, message = "Name should not be longer than 20 characters")
    private String name;

    @NotBlank(message = "Promise is required")
    @Size(max = 20, message = "Promise should not be longer than 20 characters")
    private String promise;

    @NotBlank(message = "Description is required")
    @Size(max = 80, message = "Description should not be longer than 80 characters")
    private String description;

    @Min(value = 1, message = "Stamps should be at least 1")
    @Max(value = 100, message = "Stamps should not be more than 100")
    private int defaultCount;

    @NotNull(message = "Category is required")
    @Enumerated(EnumType.STRING)
    private StampCardCategory stampCardCategory;
}
