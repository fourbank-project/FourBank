package com.arthur.fourbank.model.repositories;


import com.arthur.fourbank.model.entities.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {
}
