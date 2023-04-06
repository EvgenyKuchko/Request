package ru.hunt.Request.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Table
@Entity(name = "statuses")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @OneToMany(mappedBy = "status")
    private List<HuntingOrder> huntingOrders;

    @OneToMany(mappedBy = "status")
    private List<HuntingOrderResource> huntingOrderResources;
}