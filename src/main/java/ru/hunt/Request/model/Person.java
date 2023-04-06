package ru.hunt.Request.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table
@Entity(name = "persons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String fullName;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hunting_license_id", referencedColumnName = "id")
    private HuntingLicense huntingLicense;

    @OneToMany(mappedBy = "person")
    private List<HuntingOrder> huntingOrderList;
}