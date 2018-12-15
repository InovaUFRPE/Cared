package bsi.ufrpe.br.cared.infra;

public enum TipoUsuario {
    CUIDADOR("Cuidador"),
    PESSOA("Pessoa"),
    NONE("None");
    private final String nome;

    TipoUsuario(String nome){
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }
}
