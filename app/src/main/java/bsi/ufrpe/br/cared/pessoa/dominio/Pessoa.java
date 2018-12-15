package bsi.ufrpe.br.cared.pessoa.dominio;

import android.net.Uri;

import com.google.android.gms.tasks.Task;

import bsi.ufrpe.br.cared.endereco.dominio.Endereco;
import bsi.ufrpe.br.cared.usuario.dominio.Usuario;

public class Pessoa {
    private String nome;
    private String foto;
    private String cpf;
    private String necessidades;
    private String dataNascimento;
    private String telefone;
    private String userId;
    private Endereco endereco;

    public Pessoa(){}

    public Pessoa(String nome, String foto, String cpf, Endereco endereco, Usuario usuario, String telefone, String userId) {
        this.nome = nome;
        this.foto = foto;
        this.cpf = cpf;
        this.telefone = telefone;
        this.userId = userId;
        this.endereco = endereco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String result) {
        this.foto = result;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNecessidades() {
        return necessidades;
    }

    public void setNecessidades(String necessidades) {
        this.necessidades = necessidades;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
