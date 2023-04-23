package ru.hunt.Request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.hunt.Request.model.HuntingOrder;
import ru.hunt.Request.model.Status;

import java.util.List;

@Repository
public interface HuntingOrderRepository extends JpaRepository<HuntingOrder, Long> {
    @Query("SELECT ho FROM hunting_orders ho WHERE ho.status = :status")
    List<HuntingOrder> getOrderByStatus(@Param("status") Status status);

    @Modifying
    @Query("update hunting_orders ho set ho.status = :status where ho.id = :id")
    void changeOrderStatus(@Param("status") Status status, @Param("id") Long id);
}