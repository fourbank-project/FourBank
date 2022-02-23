package com.arthur.fourbank.model.repositories;

import com.arthur.fourbank.model.entities.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
}
