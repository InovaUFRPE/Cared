package bsi.ufrpe.br.cared.horario.dominio;

import java.util.Calendar;

public class Horario {
    private long inicio;
    private long fim;

    public Horario() {
    }

    public Horario(long inicio, long fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public long getInicio() {
        return inicio;
    }

    public void setInicio(long inicio) {
        this.inicio = inicio;
    }

    public long getFim() {
        return fim;
    }

    public void setFim(long fim) {
        this.fim = fim;
    }
}
