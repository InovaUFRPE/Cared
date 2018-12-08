package bsi.ufrpe.br.cared.pessoa.gui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.horario.dominio.Agendamento;
import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.infra.Sessao;
import java.text.SimpleDateFormat;

public class PessoaHistoricoActivity extends AppCompatActivity {
    private ListView listView;
    private List<Agendamento> agendamentos = new ArrayList<>();
    private List<Cuidador> cuidadores = new ArrayList<>();
    private PessoaHistoricoAdapter adapter;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa_historico);
        setTela();
    }
    private void setTela(){
        listView = findViewById(R.id.listViewHistorico);
        adapter = new PessoaHistoricoAdapter(agendamentos, cuidadores);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clickAgendamento(i);
            }
        });
        getAgedamentos();
    }
    private void clickAgendamento(int position){
        Intent intent = new Intent(this, PessoaAgendamentoActivity.class);
        intent.putExtra("id", agendamentos.get(position).getId());
        intent.putExtra("idC", agendamentos.get(position).getCuidadorId());
        startActivity(intent);
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
//                        Agendamento agendamentoF = agendamento.getHorario().getFim();
//                        java.util.Date dataUtil = new java.util.Date();
//                        Date dataSql = new java.sql.Date(dataUtil.getTime());
//                        dataSql.after(agendamento.getHorario());
                        if (agendamento.getPacienteId().equals(Sessao.getUserId())){
//                            if(agendamento.getHorario() <= dataSql ){
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
                boolean ok = true;
                Cuidador cuidador = dataSnapshot.getValue(Cuidador.class);
                for (Cuidador cuidador1: cuidadores) {
                    if (cuidador.getUserId().equals(cuidador1.getUserId()))
                    ok = false;
                }
                if (ok){
                    cuidadores.add(cuidador);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String textoHorarioFim(Horario horario){
        Date data2 = new Date(horario.getFim());
        return sdf.format(data2);
    }
}
