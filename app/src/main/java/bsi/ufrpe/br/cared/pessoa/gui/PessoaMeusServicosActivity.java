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
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.horario.dominio.Agendamento;
import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.infra.servico.CalendarTypeConverter;

public class PessoaMeusServicosActivity extends AppCompatActivity {
    private ListView listView;
    private List<Agendamento> agendamentos = new ArrayList<>();
    private List<Cuidador> cuidadores = new ArrayList<>();
    private PessoaMeusServicosAdapter adapter;
    private long dayBegin, dayEnd;

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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clickAgendamento(i);
            }
        });
        try{
            getDay();
            getAgendamentoDay();
        } catch (NullPointerException e) {
            getAgedamentos();
        }
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

    private void clickAgendamento(int position){
        Intent intent = new Intent(this, PessoaAgendamentoActivity.class);
        intent.putExtra("id", agendamentos.get(position).getId());
        intent.putExtra("idC", agendamentos.get(position).getCuidadorId());
        startActivity(intent);
    }

    private void getDay(){
        Bundle bundle = getIntent().getExtras();
        CalendarDay day = bundle.getParcelable("day");
        Calendar calendar = CalendarTypeConverter.calendarDayToCalendar(day);
        calendar = CalendarTypeConverter.setDayBegin(calendar);
        dayBegin = CalendarTypeConverter.calendarToLong(calendar);
        calendar = CalendarTypeConverter.setDayEnd(calendar);
        dayEnd = CalendarTypeConverter.calendarToLong(calendar);
    }

    private void getAgendamentoDay(){
        Sessao.getDatabaseAgendamento().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Agendamento> l1 = new ArrayList<>();
                cuidadores.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    for (DataSnapshot dataSnapshot2: dataSnapshot1.getChildren()){
                        Agendamento agendamento = dataSnapshot2.getValue(Agendamento.class);
                        Horario horario = agendamento.getHorario();
                        if (agendamento.getPacienteId().equals(Sessao.getUserId()) && ((horario.getInicio() >= dayBegin && horario.getInicio() <= dayEnd) || (horario.getFim() >= dayBegin && horario.getFim() <= dayEnd))){
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
}
