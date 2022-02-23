package com.arthur.NextGeneration.model.services;

import com.arthur.NextGeneration.model.entities.CartaoCredito;
import com.arthur.NextGeneration.model.entities.CartaoCredito;
import com.arthur.NextGeneration.model.repositories.CartaoCreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartaoCreditoService {

    @Autowired
    private CartaoCreditoRepository repository;

    public List<CartaoCredito> findAll() {
        return repository.findAll();
    }

    public CartaoCredito findById(Long id) {
        Optional<CartaoCredito> optional = repository.findById(id);
        return optional.get();
    }

    // Create/Update
    public boolean createCartaoCredito(CartaoCredito cartaoCredito){
        if(cartaoCredito != null){
            repository.save(cartaoCredito);
            return true;
        }
        return false;
    }

    // Delete
    public boolean deleteCartaoCreditoById(Long id){
        Optional<CartaoCredito> cartaoCreditoToDelete = repository.findById(id);
        if(cartaoCreditoToDelete.isPresent()){
            repository.delete(cartaoCreditoToDelete.get());
            return true;
        }
        return false;
    }
}
