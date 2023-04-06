package ru.hunt.Request.model;

import lombok.*;

import javax.persistence.*;

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
    private String district;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resource_id")
    private Resource resource;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hunting_order_id")
    private HuntingOrder huntingOrder;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;
}