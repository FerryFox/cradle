package com.fox.cradle.features.stampSystem.model.stampcard;

import com.fox.cradle.features.stampSystem.model.stamp.Stamp;
import com.fox.cradle.features.stampSystem.model.template.Template;
import com.fox.cradle.features.stampSystem.model.template.TemplateResponse;
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
    private TemplateResponse templateResponse;
}
