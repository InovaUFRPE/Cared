package bsi.ufrpe.br.cared.pessoa.gui;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.List;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.horario.dominio.Agendamento;
import bsi.ufrpe.br.cared.infra.EventDecorator;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.infra.servico.CalendarTypeConverter;

public class PessoaCalendarActivity extends AppCompatActivity {
    private MaterialCalendarView mcv;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa_calendar);
        configurarTela();
    }

    private void configurarTela(){
        mcv = findViewById(R.id.calendarView);
        mcv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
                Intent intent = new Intent(PessoaCalendarActivity.this, PessoaMeusServicosActivity.class);
                intent.putExtra("day", calendarDay);
                startActivity(intent);
            }
        });
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PessoaCalendarActivity.this, PessoaMeusServicosActivity.class));
            }
        });
        getAgendamentos();
    }

    private void getAgendamentos(){
        Sessao.getDatabaseAgendamento().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Agendamento> agendamentos = new ArrayList<>();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    for (DataSnapshot dataSnapshot2: dataSnapshot1.getChildren()){
                        Agendamento agendamento = dataSnapshot2.getValue(Agendamento.class);
                        if (agendamento.getPacienteId().equals(Sessao.getUserId())){
                            agendamentos.add(agendamento);
                        }
                    }
                }
                configurarDecorator(agendamentos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void configurarDecorator(List<Agendamento> agendamentos){
        EventDecorator decorator = new EventDecorator(Color.BLACK, getDays(agendamentos)) {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return super.shouldDecorate(day);
            }

            @Override
            public void decorate(DayViewFacade view) {
                super.decorate(view);
            }
        };
        mcv.removeDecorators();
        mcv.addDecorator(decorator);
    }

    private List<CalendarDay> getDays(List<Agendamento> agendamentos){
        List<CalendarDay> days = new ArrayList<>();
        for (Agendamento agendamento: agendamentos){
            days.addAll(CalendarTypeConverter.toCalendarDayList(agendamento.getHorario()));
        }
        return days;
    }
}
