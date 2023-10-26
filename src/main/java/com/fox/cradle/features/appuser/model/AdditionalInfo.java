package com.fox.cradle.features.appuser.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalInfo
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bio;
    private String status;
    private String connection;
    private String pictureId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_user_id",  nullable = true)
    private AppUser appUser;
}
