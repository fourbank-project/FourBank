package com.arthur.NextGeneration.model.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "TB_EMPRESTIMO")
@SequenceGenerator(name = "emprestimo", allocationSize = 1, sequenceName = "sq_emprestimo")
public class Emprestimo implements Serializable {


    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "emprestimo", strategy = GenerationType.IDENTITY)
    @Column(name = "cd_emprestimo")
    private Long id;

    private double valorEmprestimo;
    private int qtdParcelas;
    private int dataPagamento;

    public Emprestimo() {}

    public Emprestimo(double valorEmprestimo, int qtdParcelas, int dataPagamento) {
        this.valorEmprestimo = valorEmprestimo;
        this.qtdParcelas = qtdParcelas;
        this.dataPagamento = dataPagamento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQtdParcelas() {
        return qtdParcelas;
    }

    public void setQtdParcelas(int qtdParcelas) {
        this.qtdParcelas = qtdParcelas;
    }

    public int getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(int dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public double getValorEmprestimo() {
        return valorEmprestimo;
    }

    public void setValorEmprestimo(double valorEmprestimo) {
        this.valorEmprestimo = valorEmprestimo;
    }
}
