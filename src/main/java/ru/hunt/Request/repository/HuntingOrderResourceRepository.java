package ru.hunt.Request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.hunt.Request.model.HuntingOrder;
import ru.hunt.Request.model.HuntingOrderResource;
import ru.hunt.Request.model.Person;
import ru.hunt.Request.model.Status;

import java.util.List;

@Repository
public interface HuntingOrderResourceRepository extends JpaRepository<HuntingOrderResource, Long> {
    @Query("SELECT hor FROM hunting_order_resources hor WHERE hor.huntingOrder = :huntingOrder")
    List<HuntingOrderResource> getResourcesByOrder(@Param("huntingOrder") HuntingOrder huntingOrder);

    @Modifying
    @Query("update hunting_order_resources hor set hor.status = :status where hor.huntingOrder = :huntingOrder")
    void changeResourceStatusByOrder(@Param("status") Status status, @Param("huntingOrder") HuntingOrder huntingOrder);

    @Modifying
    @Query("update hunting_order_resources hor set hor.status = :status where hor.id = :id")
    void changeResourceStatus(@Param("status") Status status, @Param("id") Long id);

    @Query("SELECT hor FROM hunting_order_resources hor WHERE hor.huntingOrder.person = :person AND hor.status = :status")
    List<HuntingOrderResource> getAllResourcesByPersonAndStatus(@Param("person") Person person, Status status);
}