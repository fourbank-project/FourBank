package com.arthur.fourbank.model.repositories;


import com.arthur.fourbank.model.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
