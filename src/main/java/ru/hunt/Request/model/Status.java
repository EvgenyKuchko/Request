package ru.hunt.Request.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table
@Entity(name = "statuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

//    @OneToMany(mappedBy = "status")
//    private List<HuntingOrder> huntingOrders;
//
//    @OneToMany(mappedBy = "status")
//    private List<HuntingOrderResource> huntingOrderResources;
}