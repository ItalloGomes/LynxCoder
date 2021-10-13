package br.com.lynxcoder.model;

import java.util.Date;
import java.util.Objects;

public class Leitura {

    private Integer id;
    private Double porcentagemUsoCPU;
    private Double porcentagemUsoMemoria;
    private Double porcentagemUsoDisco;
    private Date dataHora;
    private Maquina maquina;

    public Leitura() {}

    public Leitura(Double porcentagemUsoCPU, Double porcentagemUsoMemoria,
                   Double porcentagemUsoDisco, Date dataHora, Maquina maquina) {
        this.porcentagemUsoCPU = porcentagemUsoCPU;
        this.porcentagemUsoMemoria = porcentagemUsoMemoria;
        this.porcentagemUsoDisco = porcentagemUsoDisco;
        this.dataHora = dataHora;
        this.maquina = maquina;
    }

    public Leitura(Integer id, Double porcentagemUsoCPU, Double porcentagemUsoMemoria,
                   Double porcentagemUsoDisco, Date dataHora, Maquina maquina) {

        this(porcentagemUsoCPU, porcentagemUsoMemoria, porcentagemUsoDisco, dataHora, maquina);
        this.id = id;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getPorcentagemUsoCPU() {
        return porcentagemUsoCPU;
    }

    public void setPorcentagemUsoCPU(Double porcentagemUsoCPU) {
        this.porcentagemUsoCPU = porcentagemUsoCPU;
    }

    public Double getPorcentagemUsoMemoria() {
        return porcentagemUsoMemoria;
    }

    public void setPorcentagemUsoMemoria(Double porcentagemUsoMemoria) {
        this.porcentagemUsoMemoria = porcentagemUsoMemoria;
    }

    public Double getPorcentagemUsoDisco() {
        return porcentagemUsoDisco;
    }

    public void setPorcentagemUsoDisco(Double porcentagemUsoDisco) {
        this.porcentagemUsoDisco = porcentagemUsoDisco;
    }

    public Date getDataHora() {
        return dataHora;
    }

    public void setDataHora(Date dataHora) {
        this.dataHora = dataHora;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Leitura leitura = (Leitura) o;
        return id.equals(leitura.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
