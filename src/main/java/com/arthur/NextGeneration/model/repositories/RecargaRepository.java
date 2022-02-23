package com.arthur.NextGeneration.model.repositories;

import com.arthur.NextGeneration.model.entities.Recarga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecargaRepository extends JpaRepository<Recarga, Long> {

    @Query("Select r from Recarga r where r.conta.id = ?1")
    List<Recarga> findAllByContaId(@Param("id") Long id);
}
