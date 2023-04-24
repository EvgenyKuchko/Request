package ru.hunt.Request.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Table
@Entity(name = "resources")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private int amount;
    private LocalDate start;
    private LocalDate finish;

    @OneToMany(mappedBy = "resource")
    private List<HuntingOrderResource> huntingOrderResources;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hunting_order_type_id", nullable = false)
    private HuntingOrderType huntingOrderType;
}