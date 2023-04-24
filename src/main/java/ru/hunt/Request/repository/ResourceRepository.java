package ru.hunt.Request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.hunt.Request.model.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
    @Modifying
    @Query("update resources r set r.amount = :amount where r.id = :id")
    void changeAmount(@Param("amount") Integer amount, @Param("id") Long id);
}