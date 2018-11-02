package bsi.ufrpe.br.cared.horario.dominio;

public class HorarioDisponivel {
    private Horario horario;
    private String userId;
    private String id;

    public HorarioDisponivel() {
    }

    public HorarioDisponivel(Horario horario, String userId, String id) {
        this.horario = horario;
        this.userId = userId;
        this.id = id;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
