package bsi.ufrpe.br.cared.cuidador.gui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.infra.Sessao;

public class CuidadorListActivity extends AppCompatActivity {
    private List<Cuidador> cuidadorList = new ArrayList<>();
    private ListView listView;
    private CuidadorAdapter cuidadorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuidador_list);
        cuidadorAdapter = new CuidadorAdapter(cuidadorList);
        listView = findViewById(R.id.testeId);
        listView.setAdapter(cuidadorAdapter);
        Sessao.getDatabaseCuidador().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    Cuidador cuidador = dataSnapshot1.getValue(Cuidador.class);
                    cuidadorList.add(cuidador);
                }
                cuidadorAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Sessao.setCuidadorPerfil(cuidadorList.get(i));
                startActivity(new Intent(CuidadorListActivity.this, PerfilCuidadorActivity.class));
            }
        });
    }
}
