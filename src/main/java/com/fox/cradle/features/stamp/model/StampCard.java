package com.fox.cradle.features.stamp.model;

import com.fox.cradle.features.appuser.model.AppUser;
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
public class StampCard
{
    @Id
    @GeneratedValue
    private long id;

    private String name;
    private String description;
    private String image;

    private Instant createdDate = Instant.now();

    @Enumerated(EnumType.STRING)
    private StampCardCategory stampCardCategory;

    @Enumerated(EnumType.STRING)
    private StampCardSecurity stampCardSecurity;

    //Relationships
    @ManyToOne
    @JoinColumn(name="app_user_id")
    private AppUser owner;

    @OneToOne(fetch = FetchType.EAGER)
    private StampCardTemplate stampCardTemplate;

    @OneToMany(mappedBy = "stampCard", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Stamp> stamps = new ArrayList<>();


    public void smallPrint()
    {
        System.out.println("stamp card : " + getStampCardTemplate().getName());
    }

    @Override
    public String toString() {
        return "StampCard{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", createdDate=" + createdDate +
                ", appUser=" + (owner != null ? owner.getAppUserName() : "null") +  // assuming AppUser has a getUsername() method
                ", stampCardTemplateName=" + (stampCardTemplate != null ? stampCardTemplate.getName() : "null") + // assuming StampCardTemplate has a getName() method
                ", stampCardTemplateOwner=" + (stampCardTemplate != null ? stampCardTemplate.getAppUser() : "null") +
                ", stampCount=" + stamps.size() + // just displaying count of stamps instead of full details to avoid recursion
                ", stampCardCategory=" + stampCardCategory +
                ", stampCardSecurity=" + stampCardSecurity +
                '}';
    }





}
