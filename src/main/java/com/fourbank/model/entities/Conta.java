package com.fourbank.model.entities;


import com.fourbank.model.enums.TipoConta;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Conta implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private Double saldo = 00.00;
    private String senha;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente = new Cliente();
    private TipoConta tipoConta;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cartao_credito_id")
    private CartaoCredito cartaoCredito;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cartao_debito_id")
    private CartaoDebito cartaoDebito;
    private Double taxa;
    private Date dataTaxa = new Date();
    @Transient
    private Boolean correnteBool = false;
    @Transient
    private Boolean poupancaBool = false;
    @Transient
    private String saldoString;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "conta")
    private List<Recarga> list = new ArrayList<>();

    public Conta() {
    }

    public Conta(Double saldo, String senha, Cliente cliente, TipoConta tipoConta, CartaoCredito cartaoCredito, CartaoDebito cartaoDebito, Double taxa, Date dataTaxa) {
        this.saldo = saldo;
        this.senha = senha;
        this.cliente = cliente;
        this.tipoConta = tipoConta;
        this.cartaoCredito = cartaoCredito;
        this.cartaoDebito = cartaoDebito;
        this.taxa = taxa;
        this.dataTaxa = dataTaxa;
    }

    public String getSaldoString() {
        return "R$ " + String.format("%.2f", saldo);
    }

    public void setSaldoString(String saldoString) {
        this.saldoString = "R$ " + String.format(String.valueOf(saldo), "%.2f");
    }

    public Boolean isCorrenteBool() {
        return correnteBool;
    }

    public void setCorrenteBool(Boolean correnteBool) {
        this.correnteBool = correnteBool;
    }

    public Boolean isPoupancaBool() {
        return poupancaBool;
    }

    public void setPoupancaBool(Boolean poupancaBool) {
        this.poupancaBool = poupancaBool;
    }

    public CartaoCredito getCartaoCredito() {
        return cartaoCredito;
    }

    public void setCartaoCredito(CartaoCredito cartaoCredito) {
        this.cartaoCredito = cartaoCredito;
    }

    public CartaoDebito getCartaoDebito() {
        return cartaoDebito;
    }

    public void setCartaoDebito(CartaoDebito cartaoDebito) {
        this.cartaoDebito = cartaoDebito;
    }

    public Double getTaxa() {
        return taxa;
    }

    public void setTaxa(Double taxa) {
        this.taxa = taxa;
    }

    public Date getDataTaxa() {
        return dataTaxa;
    }

    public void setDataTaxa(Date dataTaxa) {
        this.dataTaxa = dataTaxa;
    }

    public TipoConta getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(TipoConta tipoConta) {
        this.tipoConta = tipoConta;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Conta clone() {
        return new Conta(this.saldo, this.senha, this.cliente, this.tipoConta, this.cartaoCredito, this.cartaoDebito, this.taxa, this.dataTaxa);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conta conta = (Conta) o;
        return id == conta.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public List<Recarga> getList() {
        return list;
    }

    public void setList(List<Recarga> list) {
        this.list = list;
    }
}
