package com.fox.cradle.features.stampsystem.model.template;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.stampsystem.model.enums.StampCardCategory;
import com.fox.cradle.features.stampsystem.model.enums.StampCardSecurity;
import com.fox.cradle.features.stampsystem.model.enums.StampCardStatus;
import com.fox.cradle.features.stampsystem.model.stamp.StampInternalSecurity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Builder
public class Template
{
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String promise;
    private String description;

    private String image;

    private String createdBy;
    private int defaultCount;

    private Instant createdDate;
    private Instant lastModifiedDate;
    private String expirationDate;

    @Enumerated(EnumType.STRING)
    private StampCardCategory stampCardCategory;

    @Enumerated(EnumType.STRING)
    private StampCardSecurity stampCardSecurity;

    @Enumerated(EnumType.STRING)
    private StampCardStatus stampCardStatus;

    @ManyToOne
    @JoinColumn(name="user_id")
    private AppUser appUser;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "internal_stamp_security_id")
    private StampInternalSecurity stampInternalSecurity;
}
