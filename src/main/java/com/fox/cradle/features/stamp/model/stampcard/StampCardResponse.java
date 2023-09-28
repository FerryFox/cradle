package com.fox.cradle.features.stamp.model.stampcard;

import com.fox.cradle.features.stamp.model.stamp.Stamp;
import com.fox.cradle.features.stamp.model.template.Template;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StampCardResponse
{
    private long id;
    private String createdDate;
    private Template template;
    private List<Stamp> stamps = new ArrayList<>();
}
