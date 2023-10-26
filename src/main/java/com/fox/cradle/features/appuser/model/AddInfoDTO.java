package com.fox.cradle.features.appuser.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddInfoDTO
{
    private Long id;
    private String name;
    private String bio;
    private String status;
    private String connection;
    private String picture;
}
