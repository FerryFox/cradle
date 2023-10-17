package com.fox.cradle.features.stampsystem.model.stamp;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class StampThisResponse
{
    private boolean isStampAble;

    public String stampMessage;
}
