package com.fox.cradle.features.stampsystem.model.stampcard;

import com.fox.cradle.features.appuser.model.AppUser;

import com.fox.cradle.features.stampsystem.model.stamp.StampField;
import com.fox.cradle.features.stampsystem.model.template.Template;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;

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
    private Instant redeemDate;

    private boolean isCompleted;
    private boolean isRedeemed;

    //Relationships
    @ManyToOne
    @JoinColumn(name="app_user_id")
    private AppUser owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="template_id")
    private Template template;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name="stamp_card_id") // This column will be on the StampField table
    private List<StampField> stampFields;
}
