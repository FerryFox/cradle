package com.fox.cradle.features.stampSystem.model.stamp;

import com.fox.cradle.features.stampSystem.model.stampcard.StampCard;
import jakarta.persistence.*;
import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Builder
public class StampField
{
    @Id
    @GeneratedValue
    private Long id;

    private boolean isStamped;

    private String emptyImageUrl;

    private String stampedImageUrl;
    private int Index;

    @OneToOne(mappedBy = "stampField", cascade = CascadeType.ALL, orphanRemoval = true)
    private Stamp stamp;

    @ManyToOne
    @JoinColumn(name = "stamp_card_id")
    private StampCard stampCard;
}
