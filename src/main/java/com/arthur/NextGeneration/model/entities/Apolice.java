package com.arthur.NextGeneration.model.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
public class Apolice implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double valorApolice = 0.0;

    private String descricaoCondicoes;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "seguro_id")
    private Seguro seguro = new Seguro();

    private Date DataAssinatura;

    private Date DataCarencia;

    @Transient
    private String dataString1;

    public String getDataString1() {
        return dataString1;
    }

    public void setDataString1(String dataString) {
        this.dataString1 = dataString;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        try {
            this.DataAssinatura = sdf.parse(dataString);
        } catch (ParseException e) {
            this.DataAssinatura = Calendar.getInstance().getTime();
        }
    }

    @Transient
    private String dataString2;

    public String getDataString2() {
        return dataString2;
    }

    public void setDataString2(String dataString) {
        this.dataString2 = dataString;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        try {
            this.DataCarencia = sdf.parse(dataString);
        } catch (ParseException e) {
            this.DataCarencia = Calendar.getInstance().getTime();
        }
    }

    public Apolice() {}

    public Apolice(double valorApolice, String descricaoCondicoes, Seguro seguro, Date dataAssinatura, Date dataCarencia) {
        this.valorApolice = valorApolice;
        this.descricaoCondicoes = descricaoCondicoes;
        this.seguro = seguro;
        DataAssinatura = dataAssinatura;
        DataCarencia = dataCarencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Apolice apolice = (Apolice) o;
        return id.equals(apolice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValorApolice() {
        return valorApolice;
    }

    public void setValorApolice(Double valorApolice) {
        this.valorApolice = valorApolice;
    }

    public String getDescricaoCondicoes() {
        return descricaoCondicoes;
    }

    public void setDescricaoCondicoes(String descricaoCondicoes) {
        this.descricaoCondicoes = descricaoCondicoes;
    }

    public Seguro getSeguro() {
        return seguro;
    }

    public void setSeguro(Seguro seguro) {
        this.seguro = seguro;
    }

    public Date getDataAssinatura() {
        return DataAssinatura;
    }

    public void setDataAssinatura(Date dataAssinatura) {
        DataAssinatura = dataAssinatura;
    }

    public Date getDataCarencia() {
        return DataCarencia;
    }

    public void setDataCarencia(Date dataCarencia) {
        DataCarencia = dataCarencia;
    }
}
