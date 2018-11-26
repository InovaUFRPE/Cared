package bsi.ufrpe.br.cared.horario.dominio;

public enum Situacao {
    PENDENTE("Pendente"),
    RECUSADO("Recusado"),
    ACEITO("Aceito");
    private final String name;

    Situacao(String name) {
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
