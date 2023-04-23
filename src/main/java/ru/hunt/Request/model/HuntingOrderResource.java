package ru.hunt.Request.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Table
@Entity(name = "hunting_order_resources")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HuntingOrderResource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int amount;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "resource_id", nullable = false)
    private Resource resource;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "hunting_order_id", nullable = false)
    private HuntingOrder huntingOrder;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HuntingOrderResource that = (HuntingOrderResource) o;
        return Objects.equals(resource, that.resource);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resource);
    }
}