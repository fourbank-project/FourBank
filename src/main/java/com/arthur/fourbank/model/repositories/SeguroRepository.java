package com.arthur.fourbank.model.repositories;


import com.arthur.fourbank.model.entities.Seguro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeguroRepository extends JpaRepository<Seguro, Long> {
}
