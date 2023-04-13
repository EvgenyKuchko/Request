package ru.hunt.Request.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hunt.Request.model.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Status getByName(String name);
}