package bsi.ufrpe.br.cared.cuidador.gui;

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
import bsi.ufrpe.br.cared.horario.dominio.Agendamento;
import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.infra.servico.CalendarTypeConverter;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;

public class CuidadorMeusServicosActivity extends AppCompatActivity {
    private CuidadorMeusServicosAdapter adapter;
    private ListView listView;
    private List<Agendamento> agendamentos = new ArrayList<>();
    private List<Pessoa> pessoas = new ArrayList<>();
    private long dayBegin;
    private long dayEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuidador_meus_servicos);
        getView();
    }

    private void getView(){
        listView = findViewById(R.id.listView);
        adapter = new CuidadorMeusServicosAdapter(agendamentos, pessoas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                clickItem(i);
            }
        });
        try {
            getDay();
            getAgendamentosDay();
        } catch (NullPointerException e){
            getAgendamentos();
        }
    }

    private void getAgendamentosDay(){
        Sessao.getDatabaseAgendamento().child(Sessao.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Agendamento> l1 = new ArrayList<>();
                pessoas.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Agendamento agendamento = dataSnapshot1.getValue(Agendamento.class);
                    Horario horario = agendamento.getHorario();
                    if ((horario.getInicio() >= dayBegin && horario.getInicio() <= dayEnd) || (horario.getFim() >= dayBegin && horario.getFim() <= dayEnd)) {
                        l1.add(agendamento);
                        getPessoaAgendamento(agendamento.getPacienteId());
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

    private void getAgendamentos(){
        Sessao.getDatabaseAgendamento().child(Sessao.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Agendamento> l1 = new ArrayList<>();
                pessoas.clear();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Agendamento agendamento = dataSnapshot1.getValue(Agendamento.class);
                    l1.add(agendamento);
                    getPessoaAgendamento(agendamento.getPacienteId());
                }
                agendamentos.clear();
                agendamentos.addAll(l1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getPessoaAgendamento(String id){
        Sessao.getDatabasePessoa().child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pessoa pessoa = dataSnapshot.getValue(Pessoa.class);
                pessoas.add(pessoa);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void clickItem(int position){
        Intent intent = new Intent(this, CuidadorAgendamentoActivity.class);
        intent.putExtra("id", agendamentos.get(position).getId());
        startActivity(intent);
    }

    private void getDay(){
        Bundle extras = getIntent().getExtras();
        CalendarDay day = extras.getParcelable("day");
        Calendar calendar = CalendarTypeConverter.calendarDayToCalendar(day);
        dayBegin = CalendarTypeConverter.calendarToLong(calendar);
        calendar = CalendarTypeConverter.setDayEnd(calendar);
        dayEnd = CalendarTypeConverter.calendarToLong(calendar);
    }
}
