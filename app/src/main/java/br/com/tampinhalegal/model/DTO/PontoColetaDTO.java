package br.com.tampinhalegal.model.DTO;

import br.com.tampinhalegal.model.entidades.PontoColeta;

public class PontoColetaDTO {
    private int id;
    private String endereco;
    private String telefone;
    private String nome;

    public PontoColetaDTO() {

    }

    public PontoColetaDTO(Integer id, String endereco, String telefone, String nome) {
        this.id = id;
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


    public PontoColeta getPontoColeta() {
        PontoColeta p = new PontoColeta();
        p.setId(id);
        p.setEndereco(endereco);
        p.setTelefone(telefone);
        p.setNome(nome);

        return p;
    }

}
