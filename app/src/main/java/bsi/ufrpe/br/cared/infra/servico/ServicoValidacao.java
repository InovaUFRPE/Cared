package bsi.ufrpe.br.cared.infra.servico;

public class ServicoValidacao {
    public boolean verificarCampoVazio(String campo){
        if (campo.isEmpty()){
            return true;
        } else{
            return false;
        }
    }
}