package bsi.ufrpe.br.cared.cuidador.gui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.horario.dominio.Agendamento;
import bsi.ufrpe.br.cared.infra.EventDecorator;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.infra.servico.CalendarTypeConverter;

public class MeusHorariosAgendadosFragment extends Fragment {
    private MaterialCalendarView mcv;
    private EventDecorator eventDecorator;
    private List<CalendarDay> calendarDayList = new ArrayList<>();
    private List<Agendamento> agendamentoList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendario, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().findViewById(R.id.button).setVisibility(View.GONE);
        mcv = getView().findViewById(R.id.calendarView);
        eventDecorator = new EventDecorator(Color.BLACK, calendarDayList) {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return super.shouldDecorate(day);
            }

            @Override
            public void decorate(DayViewFacade view) {
                super.decorate(view);
            }
        };
        mcv.addDecorator(eventDecorator);
        mcv.setCurrentDate(CalendarTypeConverter.calendarToCalendarDay(Calendar.getInstance()));
        getHorarios();
    }

    private void getHorarios(){
        Sessao.getDatabaseAgendamento().child(Sessao.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Agendamento agendamento = dataSnapshot1.getValue(Agendamento.class);
                    if(agendamento.getCuidadorId().equals(Sessao.getUserId())){
                        agendamentoList.add(agendamento);
                        calendarDayList.addAll(CalendarTypeConverter.toCalendarDayList(agendamento.getHorario()));
                        restartDecorator();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void restartDecorator(){
        eventDecorator = new EventDecorator(Color.BLACK, calendarDayList) {
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
        mcv.addDecorator(eventDecorator);
    }
}
