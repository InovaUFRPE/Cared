package bsi.ufrpe.br.cared.avaliacao.dominio;

public class AvaliacaoForm {
    private String idAvaliacao;
    private double nota;
    private String comentario;
    private String idAvaliador;
    private String idAvaliado;
    private String idAgendamento;

    public AvaliacaoForm(){}

    public AvaliacaoForm(double nota, String comentario, String idAvaliador, String idAvaliado, String idAgendamento, String idAvaliacao){
        this.nota = nota;
        this.idAvaliacao = idAvaliacao;
        this.comentario = comentario;
        this.idAvaliador = idAvaliador;
        this.idAvaliado = idAvaliado;
        this.idAgendamento = idAgendamento;
    }

    public String getIdAvaliacao() {
        return idAvaliacao;
    }

    public void setIdAvaliacao(String idAvaliacao) {
        this.idAvaliacao = idAvaliacao;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getIdAvaliador() {
        return idAvaliador;
    }

    public void setIdAvaliador(String idAvaliador) {
        this.idAvaliador = idAvaliador;
    }

    public String getIdAvaliado() {
        return idAvaliado;
    }

    public void setIdAvaliado(String idAvaliado) {
        this.idAvaliado = idAvaliado;
    }

    public String getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(String idAgendamento) {
        this.idAgendamento = idAgendamento;
    }
}
