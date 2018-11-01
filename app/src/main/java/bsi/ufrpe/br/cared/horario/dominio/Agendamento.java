package bsi.ufrpe.br.cared.horario.dominio;

public class Agendamento {
    private Horario horario;
    private String cuidadorId;
    private String pacienteId;
    private double valor;

    public Agendamento() {}

    public Agendamento(Horario horario, String cuidadorId, String pacienteId, double valor) {
        this.horario = horario;
        this.cuidadorId = cuidadorId;
        this.pacienteId = pacienteId;
        this.valor = valor;
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
}
