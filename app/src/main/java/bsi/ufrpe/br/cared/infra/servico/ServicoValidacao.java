package bsi.ufrpe.br.cared.infra.servico;

public class ServicoValidacao {
    public boolean verificarCampoVazio(String campo){
        if (campo.isEmpty()){
            return true;
        } else{
            return false;
        }
    }
    public boolean verificaTamanhoSenha(String campo){
        if (campo.length() < 6){
            return true;
        } else{
            return false;
        }
    }
    public boolean verificaTamanhoTelefone(String campo){
        if (campo.length() < 8 || campo.length() > 10){
            return true;
        } else{
            return false;
        }
    }
}