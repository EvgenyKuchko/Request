package ru.hunt.Request.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
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
    private LocalDate date;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", nullable = false)
    private Person person;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hunting_order_type_id")
    private HuntingOrderType huntingOrderType;

//    @OneToMany(mappedBy = "huntingOrder")
//    private List<HuntingOrderResource> huntingOrderResourceList;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;
}