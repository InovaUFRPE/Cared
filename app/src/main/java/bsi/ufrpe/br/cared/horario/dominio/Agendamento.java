package bsi.ufrpe.br.cared.horario.dominio;

public class Agendamento {
    private Horario horario;
    private String cuidadorId;
    private String pacienteId;

    public Agendamento() {}

    public Agendamento(Horario horario, String cuidadorId, String pacienteId) {
        this.horario = horario;
        this.cuidadorId = cuidadorId;
        this.pacienteId = pacienteId;
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
}
