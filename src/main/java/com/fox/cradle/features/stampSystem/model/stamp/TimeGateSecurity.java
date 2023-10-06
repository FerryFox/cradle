package com.fox.cradle.features.stampSystem.model.stamp;

import com.fox.cradle.features.stampSystem.model.stampcard.StampCard;
import com.fox.cradle.features.stampSystem.model.template.Template;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TimeGateSecurity
{
    @Id
    @GeneratedValue
    private Long id;

    private Duration timeGateDuration;

    @OneToOne(mappedBy = "timeGateSecurity")
    private Template template;
}