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
    private List <Pessoa> pessoa = new ArrayList<>();
    private ComentariosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comentarios);
        setTela();
    }

    private void setTela(){
        listView = findViewById(R.id.listViewAvaliacoes);
        adapter = new ComentariosAdapter(avaliacaoForms, pessoa);
        listView.setAdapter(adapter);
    }

//    private void getAvaliacoes(){
//        Sessao.getDatabaseAvaliacao().addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                List<AvaliacaoForm> ll = new ArrayList<>();
//                pessoa.clear();
//                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
//                    for (DataSnapshot dataSnapshot2: dataSnapshot1.getChildren()){
//                        AvaliacaoForm avaliacaoForm = dataSnapshot2.getValue(AvaliacaoForm.class);
//                        if (avaliacaoForm.getIdAvaliado().equals(avaliacaoForm.)
//                    }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        })
//
//    }
//
//    private void getPessoa(){
//
//    }
//        }
}