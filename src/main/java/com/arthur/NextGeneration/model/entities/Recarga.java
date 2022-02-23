package com.arthur.NextGeneration.model.entities;

import com.arthur.NextGeneration.model.enums.TipoOperadora;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "TB_RECARGA")
@SequenceGenerator(name = "recarga", allocationSize = 1, sequenceName = "sq_recarga")
public class Recarga implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "recarga", strategy = GenerationType.IDENTITY)
    @Column(name = "cd_recarga")
    private Long id;

    private String numero;
    private Double valor;
    private TipoOperadora operadora;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public TipoOperadora getOperadora() {
        return operadora;
    }

    public void setOperadora(TipoOperadora operadora) {
        this.operadora = operadora;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recarga recarga = (Recarga) o;
        return Objects.equals(id, recarga.id) && Objects.equals(numero, recarga.numero) && Objects.equals(valor, recarga.valor) && operadora == recarga.operadora;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, numero, valor, operadora);
    }
}
