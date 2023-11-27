package com.fox.cradle.features.stampsystem.model.template;

import com.fox.cradle.features.stampsystem.model.enums.StampCardSecurity;
import com.fox.cradle.features.stampsystem.model.enums.StampCardStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewTemplateSecurity
{
    private String expirationDate;

    private int securityTimeGateDurationInHour;

    @NotNull(message = "Security is required")
    @Enumerated(EnumType.STRING)
    private StampCardSecurity stampCardSecurity;

    @NotNull(message = "Status is required")
    @Enumerated(EnumType.STRING)
    private StampCardStatus stampCardStatus;
}
