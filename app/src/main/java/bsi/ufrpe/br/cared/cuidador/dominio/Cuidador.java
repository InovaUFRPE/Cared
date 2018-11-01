package bsi.ufrpe.br.cared.cuidador.dominio;

import bsi.ufrpe.br.cared.endereco.dominio.Endereco;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;


public class Cuidador {
    private Pessoa pessoa;
    private Endereco endereco;
    private String servico;
    private String userId;
    private double valor;

    public Cuidador() {}

    public Cuidador(Pessoa pessoa, Endereco endereco, String servico, String userId, double valor) {
        this.pessoa = pessoa;
        this.endereco = endereco;
        this.servico = servico;
        this.userId = userId;
        this.valor = valor;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
