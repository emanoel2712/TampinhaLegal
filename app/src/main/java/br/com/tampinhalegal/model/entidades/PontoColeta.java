package br.com.tampinhalegal.model.entidades;

public class PontoColeta {
    private int id;
    private String endereco;
    private String telefone;
    private String nome;

    public PontoColeta() {
    }

    public PontoColeta(String endereco, String telefone, String nome) {
        this.endereco = endereco;
        this.telefone = telefone;
        this.nome = nome;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
