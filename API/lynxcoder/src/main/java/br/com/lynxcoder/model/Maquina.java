package br.com.lynxcoder.model;

import java.util.Objects;

public class Maquina {

    private Integer id;
    private String marca;
    private String modelo;
    private String tipoCPU;
    private Double totalMemoria;
    private Double totalDisco;
    private String sistemaOperacional;
    private Usuario usuario;

    public Maquina() {
    }

    public Maquina(String marca, String modelo, String tipoCPU, Double totalMemoria,
                   Double totalDisco, String sistemaOperacional, Usuario usuario) {
        this.marca = marca;
        this.modelo = modelo;
        this.tipoCPU = tipoCPU;
        this.totalMemoria = totalMemoria;
        this.totalDisco = totalDisco;
        this.sistemaOperacional = sistemaOperacional;
        this.usuario = usuario;
    }

    public Maquina(Integer id, String marca, String modelo, String tipoCPU, Double totalMemoria,
                   Double totalDisco, String sistemaOperacional, Usuario usuario) {

        this(marca, modelo, tipoCPU, totalMemoria, totalDisco, sistemaOperacional, usuario);
        this.id = id;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getTipoCPU() {
        return tipoCPU;
    }

    public void setTipoCPU(String tipoCPU) {
        this.tipoCPU = tipoCPU;
    }

    public Double getTotalMemoria() {
        return totalMemoria;
    }

    public void setTotalMemoria(Double totalMemoria) {
        this.totalMemoria = totalMemoria;
    }

    public Double getTotalDisco() {
        return totalDisco;
    }

    public void setTotalDisco(Double totalDisco) {
        this.totalDisco = totalDisco;
    }

    public String getSistemaOperacional() {
        return sistemaOperacional;
    }

    public void setSistemaOperacional(String sistemaOperacional) {
        this.sistemaOperacional = sistemaOperacional;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Maquina maquina = (Maquina) o;
        return id.equals(maquina.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
