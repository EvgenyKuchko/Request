package ru.hunt.Request.model;

import lombok.*;

import javax.persistence.*;

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
}