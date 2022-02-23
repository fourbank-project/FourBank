package com.arthur.NextGeneration.model.repositories;


import com.arthur.NextGeneration.model.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
