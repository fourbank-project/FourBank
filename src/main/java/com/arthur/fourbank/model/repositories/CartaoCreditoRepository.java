package com.arthur.fourbank.model.repositories;


import com.arthur.fourbank.model.entities.CartaoCredito;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartaoCreditoRepository extends JpaRepository<CartaoCredito, Long> {
}
