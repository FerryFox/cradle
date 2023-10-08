package com.fox.cradle.features.stampSystem.model.stamp;

import lombok.*;
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class StampFieldResponse
{
    private Long id;

    private boolean isStamped;

    private String emptyImageUrl;

    private String stampedImageUrl;

    private int index;

    private Long stampCardId;
}