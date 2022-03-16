package com.fourbank.model.repositories;


import com.fourbank.model.entities.Seguro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeguroRepository extends JpaRepository<Seguro, Long> {
}
