package bsi.ufrpe.br.cared.horario.dominio;

import java.util.Calendar;

public class Horario {
    private Calendar inicio;
    private Calendar fim;

    public Horario() {
    }

    public Horario(Calendar inicio, Calendar fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public Calendar getInicio() {
        return inicio;
    }

    public void setInicio(Calendar inicio) {
        this.inicio = inicio;
    }

    public Calendar getFim() {
        return fim;
    }

    public void setFim(Calendar fim) {
        this.fim = fim;
    }
}
