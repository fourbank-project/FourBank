package com.arthur.NextGeneration.model.services;

import com.arthur.NextGeneration.model.entities.CartaoDebito;
import com.arthur.NextGeneration.model.entities.CartaoDebito;
import com.arthur.NextGeneration.model.repositories.CartaoDebitoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartaoDebitoService {

    @Autowired
    private CartaoDebitoRepository repository;

    public List<CartaoDebito> findAll() {
        return repository.findAll();
    }

    public CartaoDebito findById(Long id) {
        Optional<CartaoDebito> optional = repository.findById(id);
        return optional.get();
    }

    // Create/Update
    public boolean createCartaoDebito(CartaoDebito cartaoDebito){
        if(cartaoDebito != null){
            repository.save(cartaoDebito);
            return true;
        }
        return false;
    }

    // Delete
    public boolean deleteCartaoDebitoById(Long id){
        Optional<CartaoDebito> cartaoDebitoToDelete = repository.findById(id);
        if(cartaoDebitoToDelete.isPresent()){
            repository.delete(cartaoDebitoToDelete.get());
            return true;
        }
        return false;
    }
}
