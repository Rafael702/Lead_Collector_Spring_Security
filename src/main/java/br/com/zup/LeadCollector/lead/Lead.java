package br.com.zup.LeadCollector.lead;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Lead {

    @Id
    private String email;
    private String nome;
    private String descricao;

    public Lead() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
