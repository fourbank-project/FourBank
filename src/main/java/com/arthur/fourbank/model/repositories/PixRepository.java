package com.arthur.fourbank.model.repositories;


import com.arthur.fourbank.model.entities.Conta;
import com.arthur.fourbank.model.entities.Pix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PixRepository extends JpaRepository<Pix, Long> {

    ArrayList<Pix> findAllByConta(Conta conta);
    Pix findByConteudoChave(String conteudoChave);
    Pix findByContaClienteCpf(String cpf);
    Pix findByContaClienteEmail(String cpf);
    Pix findByContaClienteTelefone(String cpf);
}
