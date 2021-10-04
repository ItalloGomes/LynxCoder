package br.com.lynxcoder.model;

import java.util.Objects;

public class Empresa {

    private Integer id;
    private String nome;
    private String CNPJ;
    private String telefone;
    private String estado;
    private String cidade;
    private String CEP;
    private String logradouro;
    private String numero;

    public Empresa(Integer id, String nome, String CNPJ, String telefone, String estado,
                   String cidade, String CEP, String logradouro, String numero) {

        this.id = id;
        this.nome = nome;
        this.CNPJ = CNPJ;
        this.telefone = telefone;
        this.estado = estado;
        this.cidade = cidade;
        this.CEP = CEP;
        this.logradouro = logradouro;
        this.numero = numero;

    }

    public Empresa(String nome, String CNPJ, String telefone, String estado,
                   String cidade, String CEP, String logradouro, String numero) {

        this.nome = nome;
        this.CNPJ = CNPJ;
        this.telefone = telefone;
        this.estado = estado;
        this.cidade = cidade;
        this.CEP = CEP;
        this.logradouro = logradouro;
        this.numero = numero;

    }

    public Integer getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getCEP() {
        return CEP;
    }

    public void setCEP(String CEP) {
        this.CEP = CEP;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Empresa empresa = (Empresa) o;
        return Objects.equals(id, empresa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
