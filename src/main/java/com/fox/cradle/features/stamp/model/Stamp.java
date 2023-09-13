package com.fox.cradle.features.stamp.model;

import com.fox.cradle.features.appuser.model.AppUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class Stamp
{
    @Id
    @GeneratedValue
    private long id;

    private Instant createdDate = Instant.now();

    @ManyToOne
    @JoinColumn(name="user_id")
    private AppUser appUser;
}
