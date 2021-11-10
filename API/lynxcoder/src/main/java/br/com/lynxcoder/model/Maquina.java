package br.com.lynxcoder.model;

import java.util.Objects;

public class Maquina {

    private Integer id;
    private String hostname;
    private String tipoCPU;
    private String totalMemoria;
    private String totalDisco;
    private String sistemaOperacional;
    private Usuario usuario;

    public Maquina() {
    }

    public Maquina(Integer id){
        this.id = id;
        hashCode();
    }

    public Maquina(String hostname, String tipoCPU, String totalMemoria,
                   String totalDisco, String sistemaOperacional, Usuario usuario) {
        this.hostname = hostname;
        this.tipoCPU = tipoCPU;
        this.totalMemoria = totalMemoria;
        this.totalDisco = totalDisco;
        this.sistemaOperacional = sistemaOperacional;
        this.usuario = usuario;
    }

    public Maquina(Integer id, String hostname, String tipoCPU, String totalMemoria,
                   String totalDisco, String sistemaOperacional, Usuario usuario) {

        this(hostname, tipoCPU, totalMemoria, totalDisco, sistemaOperacional, usuario);
        this.id = id;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoCPU() {
        return tipoCPU;
    }

    public void setTipoCPU(String tipoCPU) {
        this.tipoCPU = tipoCPU;
    }

    public String getTotalMemoria() {
        return totalMemoria;
    }

    public void setTotalMemoria(String totalMemoria) {
        this.totalMemoria = totalMemoria;
    }

    public String getTotalDisco() {
        return totalDisco;
    }

    public void setTotalDisco(String totalDisco) {
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

    @Override
    public String toString() {
        return "Maquina{" +
                "id=" + id +
                ", tipoCPU='" + tipoCPU + '\'' +
                ", totalMemoria=" + totalMemoria +
                ", totalDisco=" + totalDisco +
                ", sistemaOperacional='" + sistemaOperacional + '\'' +
                ", usuario=" + usuario +
                '}';
    }
}
