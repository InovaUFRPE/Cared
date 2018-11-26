package bsi.ufrpe.br.cared.pessoa.gui;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.infra.Sessao;

public class PessoaPerfilActivity extends AppCompatActivity {
    private ImageView fotoIdoso;
    private TextView nomeIdoso, notaIdoso, emailIdoso, botaoNecessidades, botaoComentarios;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa_perfil);
        setTela();
    }

    private void setTela() {
        fotoIdoso = findViewById(R.id.fotoPerfilIdoso);
        nomeIdoso = findViewById(R.id.nomeiIdosoPerfil);
        notaIdoso = findViewById(R.id.notaIdoso);
        emailIdoso = findViewById(R.id.emailIdoso);
        botaoNecessidades = findViewById(R.id.btRelatorioId);
        botaoComentarios = findViewById(R.id.btComentariosid);
        setIdoso();
    }

    private void setIdoso() {
        Picasso.get()
                .load(Sessao.getPessoa().getFoto())
                .resize(300, 300)
                .centerCrop()
                .into(fotoIdoso);
        nomeIdoso.setText(Sessao.getPessoa().getNome());
    }
}
