package com.fox.cradle.features.stampsystem.model.template;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.stampsystem.model.enums.StampCardCategory;
import com.fox.cradle.features.stampsystem.model.enums.StampCardSecurity;
import com.fox.cradle.features.stampsystem.model.enums.StampCardStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewTemplate
{
    private Integer id;

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

    @NotNull(message = "Security is required")
    @Enumerated(EnumType.STRING)
    private StampCardSecurity stampCardSecurity;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private StampCardStatus stampCardStatus;

    @NotBlank(message = "File is required")
    private String image;
    private String fileName;

    private String expirationDate;

    private NewSecurityTimeGate securityTimeGate;

    //will set in Service
    private AppUser appUser;
}
