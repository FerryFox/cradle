package com.fox.cradle.features.appuser.model;

import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddInfoDTO
{
    private Long id;
    private String name;

    @Size(max = 250, message = "Bio must be less than 251 characters")
    private String bio;

    @Size(max = 34, message = "Status must be less than 35 characters")
    private String status;

    private String connection;
    private String picture;
}
