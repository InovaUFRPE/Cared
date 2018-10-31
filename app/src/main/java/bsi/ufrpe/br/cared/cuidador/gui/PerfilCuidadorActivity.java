package bsi.ufrpe.br.cared.cuidador.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.infra.Sessao;

public class PerfilCuidadorActivity extends AppCompatActivity {
    private TextView nome;
    private TextView servicos;
    private TextView email;
    private TextView cidade;
    private Button botaoCalendario;
    private Button botaoComentarios;
    private TextView nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cuidador);
        setTela();
    }

    private void setTela(){
        nome = findViewById(R.id.nomeCuidadorPerfilId);
        cidade = findViewById(R.id.enderecoCuidadoraId);
        servicos = findViewById(R.id.descricaoId);
        setCuidador();
    }

    private void setCuidador(){
        nome.setText(Sessao.getCuidadorPerfil().getPessoa().getNome());
        servicos.setText(Sessao.getCuidadorPerfil().getServico());
        cidade.setText(Sessao.getCuidadorPerfil().getEndereco().getCidade());
    }
}
