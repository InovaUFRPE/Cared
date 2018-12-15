package bsi.ufrpe.br.cared.pessoa.gui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;

public class PessoaPerfilActivity extends AppCompatActivity {
    private ImageView fotoIdoso;
    private TextView nomeIdoso, notaIdoso, emailIdoso, necessidades, botaoComentarios;
    private Pessoa pessoa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa_perfil);
        setTela();
        getPessoa(getId());
    }

    private void setTela() {
        fotoIdoso = findViewById(R.id.fotoPerfilIdoso);
        nomeIdoso = findViewById(R.id.nomeiIdosoPerfil);
        notaIdoso = findViewById(R.id.notaIdoso);
        emailIdoso = findViewById(R.id.descricao);
        necessidades = findViewById(R.id.necessidadesUser);
        botaoComentarios = findViewById(R.id.btComentariosid);
    }

    private String getId(){
        Bundle extra = getIntent().getExtras();
        return extra.getString("id");
    }

    private void setIdoso() {
        Picasso.get()
                .load(Sessao.getPessoa().getFoto())
                .resize(300, 300)
                .centerCrop()
                .into(fotoIdoso);
        nomeIdoso.setText(Sessao.getPessoa().getNome());
    }

    private void getPessoa(String id){
        Sessao.getDatabasePessoa().child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pessoa = dataSnapshot.getValue(Pessoa.class);
                setTexts();
//                setButtons();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void setTexts(){
        nomeIdoso.setText(pessoa.getNome());
        necessidades.setText(pessoa.getNecessidades());
        setIdoso();
    }

    private void setButtons(){
    }
}
