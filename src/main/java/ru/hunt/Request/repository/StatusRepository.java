package ru.hunt.Request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hunt.Request.model.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Status getByName(String name);
}