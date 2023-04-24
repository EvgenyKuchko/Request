package ru.hunt.Request.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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
}