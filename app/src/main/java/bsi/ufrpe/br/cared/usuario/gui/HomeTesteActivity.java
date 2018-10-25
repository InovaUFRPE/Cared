package bsi.ufrpe.br.cared.usuario.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.infra.Sessao;

public class HomeTesteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_teste);
        TextView nome = findViewById(R.id.nomePessoaId);
        if (Sessao.getTipo() == 0){
            nome.setText(Sessao.getPessoa().getNome());
        }else{
            nome.setText(Sessao.getCuidador().getPessoa().getNome());
        }
    }

    @Override
    public void onBackPressed() {
        Sessao.logout();
        startActivity(new Intent(HomeTesteActivity.this, LoginActivity.class));
        finish();
    }
}
