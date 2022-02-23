package com.arthur.NextGeneration.model.services;

import com.arthur.NextGeneration.model.entities.Pix;
import com.arthur.NextGeneration.model.entities.Conta;
import com.arthur.NextGeneration.model.entities.Pix;
import com.arthur.NextGeneration.model.enums.TipoChavePix;
import com.arthur.NextGeneration.model.repositories.ContaRepository;
import com.arthur.NextGeneration.model.repositories.PixRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PixService {

    @Autowired
    private PixRepository repository;

    @Autowired
    private ContaRepository contaRepository;

    private Pix pix = new Pix();

    public PixService(){}

    public PixService(Pix pix){
        this.pix = pix;
    }


    // Search Methods


    public List<Pix> findAll() {
        return repository.findAll();
    }

    public Pix findById(Long id) {
        Optional<Pix> optional = repository.findById(id);
        return optional.get();
    }

    // Create/Update
    public boolean createPix(Pix pix){
        if(pix != null){
            repository.save(pix);
            return true;
        }
        return false;
    }

    // Delete
    public boolean deletePixById(Long id){
        Optional<Pix> pixToDelete = repository.findById(id);
        if(pixToDelete.isPresent()){
            repository.delete(pixToDelete.get());
            return true;
        }
        return false;
    }


    // Validation Methods


    public boolean ativarChave(TipoChavePix chavePix, double valor, String conteudoChave, Conta conta){
        if(conta == null){
            return false;
        }
        pix.setChavePix(chavePix);
        pix.setValor(valor);
        pix.setConteudoChave(conteudoChave);
        pix.setAtivado(true);
        pix.setConta(conta);
        return true;
    }

    public String gerarRandomNumber(){
        Random random = new Random();
        return String.valueOf(random.nextInt(99999999));
    }

    public Pix getPix() {
        return pix;
    }

    public boolean transferirPix(Conta origem, Conta destino, Double valor){
        if(origem == null || destino == null){
            return false;
        }else{
            ContaService cs = new ContaService(origem);
            cs.sacar(valor);
            cs.setConta(destino);
            cs.depositar(valor);
            contaRepository.saveAll(Arrays.asList(origem,destino));
            return true;
        }
    }

    public Pix findByConteudoChave(String conteudChave){
        return repository.findByConteudoChave(conteudChave);
    }
}
