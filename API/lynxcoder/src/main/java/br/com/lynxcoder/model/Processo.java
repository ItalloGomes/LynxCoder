package br.com.lynxcoder.model;

import java.util.Date;
import java.util.Objects;

public class Processo {

    private Integer id;
    private String PID;
    private String nome;
    private Date dataHora;
    private Maquina maquina;

    public Processo() {}

    public Processo(String PID, String nome,
                    Date dataHora, Maquina maquina) {
        this.PID = PID;
        this.nome = nome;
        this.dataHora = dataHora;
        this.maquina = maquina;
    }

    public Processo(Integer id, String PID, String nome,
                    Date dataHora, Maquina maquina) {

        this(PID, nome, dataHora, maquina);
        this.id = id;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPID() {
        return PID;
    }

    public void setPID(String PID) {
        this.PID = PID;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getdataHora() {
        return dataHora;
    }

    public void setdataHora(Date dataHora) {
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
        Processo processo = (Processo) o;
        return id.equals(processo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Processo{" +
                "id=" + id +
                ", PID='" + PID + '\'' +
                ", nome='" + nome + '\'' +
                ", dataHora=" + dataHora +
                ", maquina=" + maquina +
                '}';
    }
}
