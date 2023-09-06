package com.fox.cradle.features.stamp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

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

    private String name;
    private String issuer;
}
