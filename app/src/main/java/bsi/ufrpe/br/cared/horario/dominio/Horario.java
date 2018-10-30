package bsi.ufrpe.br.cared.horario.dominio;

import java.util.Date;

public class Horario {
    private Date inicio;
    private Date fim;

    public Horario(){}

    public Horario(Date inicio, Date fim) {
        this.inicio = inicio;
        this.fim = fim;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }
}
