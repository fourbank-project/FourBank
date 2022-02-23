package com.arthur.NextGeneration.model.repositories;


import com.arthur.NextGeneration.model.entities.Conta;
import com.arthur.NextGeneration.model.entities.Seguro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeguroRepository extends JpaRepository<Seguro, Long> {
}
