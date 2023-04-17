package ru.hunt.Request.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Table
@Entity(name = "hunting_order_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HuntingOrderType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String type;

//    @OneToMany(mappedBy = "huntingOrderType")
//    private List<HuntingOrder> huntingOrderList;

//    @OneToMany(mappedBy = "huntingOrderType")
//    private List<Resource> resources;
}