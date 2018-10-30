package bsi.ufrpe.br.cared.horario.dominio;

public class HorarioDisponivel {
    private Horario horario;
    private String userId;

    public HorarioDisponivel() {
    }

    public HorarioDisponivel(Horario horario, String userId) {
        this.horario = horario;
        this.userId = userId;
    }

    public Horario getHorario() {
        return horario;
    }

    public void setHorario(Horario horario) {
        this.horario = horario;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
