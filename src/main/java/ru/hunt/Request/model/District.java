package ru.hunt.Request.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Table
@Entity(name = "districts")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @OneToMany(mappedBy = "district")
    private List<HuntingOrderResource> huntingOrderResources;
}