package com.arthur.fourbank.model.repositories;


import com.arthur.fourbank.model.entities.CartaoDebito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoDebitoRepository extends JpaRepository<CartaoDebito, Long> {
}
