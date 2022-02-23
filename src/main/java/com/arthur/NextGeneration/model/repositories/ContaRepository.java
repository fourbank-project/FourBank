package com.arthur.NextGeneration.model.repositories;


import com.arthur.NextGeneration.model.entities.Conta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface ContaRepository extends JpaRepository<Conta, Long> {

    Conta findByClienteCpf(String cpf);

    Conta findBySenhaAndClienteEmail(String senha,String email);

    ArrayList<Conta> findAllByClienteCpf(String cpf);
}
