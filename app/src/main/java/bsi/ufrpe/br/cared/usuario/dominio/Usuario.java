package bsi.ufrpe.br.cared.usuario.dominio;

import com.google.firebase.database.IgnoreExtraProperties;

import bsi.ufrpe.br.cared.endereco.dominio.Endereco;

@IgnoreExtraProperties
public class Usuario {
    private int tipoConta;
    private String userId;

    public Usuario(){}

    public Usuario(int tipoConta, String userId){
        this.tipoConta = tipoConta;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getTipoConta() {
        return tipoConta;
    }

    public void setTipoConta(int tipoConta) {
        this.tipoConta = tipoConta;
    }
}
