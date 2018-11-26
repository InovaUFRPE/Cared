package bsi.ufrpe.br.cared.pessoa.gui;

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
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.horario.dominio.Agendamento;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;

public class PessoaMeusServicosActivity extends AppCompatActivity {
    private ListView listView;
    private List<Agendamento> agendamentos = new ArrayList<>();
    private List<Cuidador> cuidadores = new ArrayList<>();
    private PessoaMeusServicosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa_meus_servicos);
        getView();
    }

    private void getView(){
        listView = findViewById(R.id.listView);
        adapter = new PessoaMeusServicosAdapter(agendamentos, cuidadores);
        listView.setAdapter(adapter);
        getAgedamentos();
    }

    private void getAgedamentos(){
        Sessao.getDatabaseAgendamento().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Agendamento> l1 = new ArrayList<>();
                cuidadores.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    for (DataSnapshot dataSnapshot2: dataSnapshot1.getChildren()){
                        Agendamento agendamento = dataSnapshot2.getValue(Agendamento.class);
                        if (agendamento.getPacienteId().equals(Sessao.getUserId())){
                            l1.add(agendamento);
                            getCuidadorAgendamento(agendamento.getCuidadorId());
                        }
                    }
                }
                agendamentos.clear();
                agendamentos.addAll(l1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getCuidadorAgendamento(String id){
        Sessao.getDatabaseCuidador().child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Cuidador cuidador = dataSnapshot.getValue(Cuidador.class);
                cuidadores.add(cuidador);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
