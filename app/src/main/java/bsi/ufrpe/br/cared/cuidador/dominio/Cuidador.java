package bsi.ufrpe.br.cared.cuidador.dominio;

import bsi.ufrpe.br.cared.endereco.dominio.Endereco;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;


public class Cuidador {
    private Pessoa pessoa;
    private Endereco endereco;
    private String servico;
    private String disponivelDormir;
    private String possuiCurso;
    private String curso;
    private String experiencia;
    private String resumoExperiencia;
    private String userId;
    private double valor;

    public Cuidador() {}

    public Cuidador(Pessoa pessoa, Endereco endereco, String servico, String disponivelDormir, String possuiCurso,
                    String curso, String experiencia, String resumoExperiencia, String userId, double valor) {
        this.pessoa = pessoa;
        this.endereco = endereco;
        this.servico = servico;
        this.disponivelDormir = disponivelDormir;
        this.possuiCurso = possuiCurso;
        this.curso = curso;
        this.experiencia = experiencia;
        this.resumoExperiencia = resumoExperiencia;
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

    public String getDisponivelDormir() {
        return disponivelDormir;
    }

    public void setDisponivelDormir(String disponivelDormir) {
        this.disponivelDormir = disponivelDormir;
    }

    public String getPossuiCurso() {
        return possuiCurso;
    }

    public void setPossuiCurso(String possuiCurso) {
        this.possuiCurso = possuiCurso;
    }

    public String getCurso() {
        return curso;
    }

    public void setCursosInformado(String curso) {
        this.curso = curso;
    }

    public String getExperiencia() {
        return experiencia;
    }

    public void setPossuiExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public String getResumoExperiencia() {
        return resumoExperiencia;
    }

    public void setResumoExperiencia(String resumoExperiencia) {
        this.resumoExperiencia = resumoExperiencia;
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
