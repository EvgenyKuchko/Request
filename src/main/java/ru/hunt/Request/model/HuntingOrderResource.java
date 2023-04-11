package ru.hunt.Request.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Table
@Entity(name = "hunting_order_resources")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HuntingOrderResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int amount;

    @ManyToMany
    @JoinTable(
            name = "hunting_order_resources_districts",
            joinColumns = @JoinColumn(name = "hunting_order_resource_id"),
            inverseJoinColumns = @JoinColumn(name = "district_id"))
    private Set<District> districts;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resource_id")
    private Resource resource;

    @ManyToMany(mappedBy = "huntingOrderResources")
    private Set<HuntingOrder> huntingOrders;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;
}