package bsi.ufrpe.br.cared.horario.dominio;

public class Agendamento {
    private Horario horario;
    private String cuidadorId;
    private String pacienteId;
    private double valor;
    private String id;
    private Situacao situacao;

    public Agendamento() {}

    public Agendamento(Horario horario, String cuidadorId, String pacienteId, double valor, String id, Situacao situacao) {
        this.horario = horario;
        this.cuidadorId = cuidadorId;
        this.pacienteId = pacienteId;
        this.valor = valor;
        this.id = id;
        this.situacao = situacao;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public String getCuidadorId() {
        return cuidadorId;
    }

    public void setCuidadorId(String cuidadorId) {
        this.cuidadorId = cuidadorId;
    }

    public String getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(String pacienteId) {
        this.pacienteId = pacienteId;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }
}
