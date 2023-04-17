package ru.hunt.Request.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Table
@Entity(name = "hunting_licenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HuntingLicense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String series;
    private int number;
    private LocalDate issueDate;

//    @OneToOne(mappedBy = "huntingLicense")
//    private Person person;
}