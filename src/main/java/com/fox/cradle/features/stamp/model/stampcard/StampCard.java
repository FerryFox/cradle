package com.fox.cradle.features.stamp.model.stampcard;

import com.fox.cradle.features.appuser.model.AppUser;
import com.fox.cradle.features.stamp.model.stamp.Stamp;
import com.fox.cradle.features.stamp.model.template.Template;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Builder
@ToString
public class StampCard
{
    @Id
    @GeneratedValue
    private long id;

    private Instant createdDate = Instant.now();

    //Relationships
    @ManyToOne
    @JoinColumn(name="app_user_id")
    private AppUser owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="template_id")
    private Template template;

    @OneToMany(mappedBy = "stampCard", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stamp> stamps = new ArrayList<>();

    public void smallPrint()
    {
        System.out.println("stamp card : " + getTemplate().getName());
    }
}
