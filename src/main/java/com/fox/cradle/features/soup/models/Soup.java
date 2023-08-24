package com.fox.cradle.features.soup.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Soup {
    private Long id;
    private String name;
    private String ingredients;

    public Soup(){}
}
