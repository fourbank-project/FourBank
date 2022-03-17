package com.fourbank.model.repositories;


import com.fourbank.model.entities.CartaoDebito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoDebitoRepository extends JpaRepository<CartaoDebito, Long> {
}
