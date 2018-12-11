package bsi.ufrpe.br.cared.avaliacao.gui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.avaliacao.dominio.AvaliacaoForm;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;

public class ComentariosActivity extends AppCompatActivity {
    private ListView listView;
    private List<AvaliacaoForm> avaliacaoForms = new ArrayList<>();
    private List <Pessoa> pessoas = new ArrayList<>();
    private ComentariosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);
        setTela();
    }

    private void setTela(){
        listView = findViewById(R.id.listViewAvaliacoes);
        adapter = new ComentariosAdapter(avaliacaoForms, pessoas);
        listView.setAdapter(adapter);
    }

    private void getAvaliacoes(){
        Sessao.getDatabaseAvaliacao().child(getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    AvaliacaoForm avaliacaoForm = dataSnapshot1.getValue(AvaliacaoForm.class);
                    avaliacaoForms.add(avaliacaoForm);
                    getPessoa(avaliacaoForm.getIdAvaliador());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getPessoa(String id){
        Sessao.getDatabasePessoa().child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pessoas.add(dataSnapshot.getValue(Pessoa.class));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String getId(){
        try{
            Bundle b = getIntent().getExtras();
            return b.getString("id");
        }catch (NullPointerException n){
            return Sessao.getUserId();
        }
    }
}