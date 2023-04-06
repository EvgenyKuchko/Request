package ru.hunt.Request.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table
@Entity(name = "resources")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "quota_id", referencedColumnName = "id")
    private Quota quota;

    @OneToMany(mappedBy = "resource")
    private List<HuntingOrderResource> huntingOrderResources;
}