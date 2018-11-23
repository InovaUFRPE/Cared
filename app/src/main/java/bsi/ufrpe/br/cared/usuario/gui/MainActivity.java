package bsi.ufrpe.br.cared.usuario.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.cuidador.gui.CadastroCuidadorActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    protected  void onResume(){
        super.onResume();
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
    }

    public void trocaTela (Class tela){
        Intent intent = new Intent(this,tela);
        startActivity(intent);
        finish();
    }

    public void cliqueSouContratante (View view){
        this.trocaTela(CadastroPessoaActivity.class);
    }

    public void cliqueSouCuidador (View view){
        this.trocaTela(CadastroCuidadorActivity.class);
    }

    public void cliqueFazerLogin (View view){
        this.trocaTela(LoginActivity.class);
    }
}
