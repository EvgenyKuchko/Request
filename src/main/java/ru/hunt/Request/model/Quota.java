package ru.hunt.Request.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Table
@Entity(name = "quotas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Quota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int amount;
    private LocalDate start;
    private LocalDate finish;

    @OneToOne(mappedBy = "quota")
    private Resource resource;
}