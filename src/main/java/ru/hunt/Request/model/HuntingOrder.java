package ru.hunt.Request.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table
@Entity(name = "hunting_orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HuntingOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hunting_order_type_id")
    private HuntingOrderType huntingOrderType;

    @OneToMany(mappedBy = "huntingOrder")
    private List<HuntingOrderResource> huntingOrderResources;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;
}