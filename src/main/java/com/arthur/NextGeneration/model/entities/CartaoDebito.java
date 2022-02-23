package com.arthur.NextGeneration.model.entities;


import javax.persistence.Entity;

@Entity
public class CartaoDebito extends Cartao {

    private Double limitePorTransacao;

    public CartaoDebito() {
    }

    public CartaoDebito(String bandeira, String senha, Double limitePorTransacao) {
        super(bandeira, senha, true);
        this.limitePorTransacao = limitePorTransacao;
    }

    public Double getLimitePorTransacao() {
        return limitePorTransacao;
    }

    public void setLimitePorTransacao(Double limitePorTransacao) {
        this.limitePorTransacao = limitePorTransacao;
    }
}
