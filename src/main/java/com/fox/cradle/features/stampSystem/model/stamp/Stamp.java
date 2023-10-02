package com.fox.cradle.features.stampSystem.model.stamp;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.stampSystem.model.stampcard.StampCard;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Builder
public class Stamp
{
    @Id
    @GeneratedValue
    private long id;

    private Instant createdDate = Instant.now();

    @OneToOne
    @JoinColumn(name="stamp_field_id")
    private StampField stampField;

    @ManyToOne
    @JoinColumn(name="issuer_id")
    private AppUser issuer;



}
