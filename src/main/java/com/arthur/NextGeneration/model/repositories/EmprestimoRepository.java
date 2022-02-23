package com.arthur.NextGeneration.model.repositories;

import com.arthur.NextGeneration.model.entities.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
}
