package com.fox.cradle.features.stampSystem.model.stampcard;

import com.fox.cradle.features.appuser.model.AppUser;

import com.fox.cradle.features.stampSystem.model.stamp.TimeGateSecurity;
import com.fox.cradle.features.stampSystem.model.template.Template;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Builder
@ToString
public class StampCard
{
    @Id
    @GeneratedValue
    private long id;

    private Instant createdDate;
    private Instant lastStampDate;

    private boolean isCompleted;
    private boolean isRedeemed;

    //Relationships
    @ManyToOne
    @JoinColumn(name="app_user_id")
    private AppUser owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="template_id")
    private Template template;

}
