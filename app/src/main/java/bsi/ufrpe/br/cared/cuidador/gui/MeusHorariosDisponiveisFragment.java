package bsi.ufrpe.br.cared.cuidador.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
import bsi.ufrpe.br.cared.horario.dominio.HorarioDisponivel;
import bsi.ufrpe.br.cared.infra.EventDecorator;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.infra.servico.CalendarTypeConverter;

public class MeusHorariosDisponiveisFragment extends Fragment {
    private MaterialCalendarView mcv;
    private EventDecorator eventDecorator;
    private List<CalendarDay> calendarDayList = new ArrayList<>();
    private List<HorarioDisponivel> horarioDisponivelList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendario, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button button = getView().findViewById(R.id.button);
        button.setText("adicionar horário disponível");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CadastrarHorarioDisponivelActivity.class));
            }
        });
        mcv = getView().findViewById(R.id.calendarView);
        eventDecorator = new EventDecorator(0, calendarDayList) {
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
        mcv.state().edit().setMinimumDate(CalendarTypeConverter.calendarToCalendarDay(Calendar.getInstance())).commit();
        getHorarios();
    }

    private void getHorarios(){
        Sessao.getDatabaseHorarioDisponivel().child(Sessao.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    HorarioDisponivel horarioDisponivel = dataSnapshot1.getValue(HorarioDisponivel.class);
                    if(horarioDisponivel.getUserId().equals(Sessao.getUserId())){
                        horarioDisponivelList.add(horarioDisponivel);
                        calendarDayList.addAll(CalendarTypeConverter.toCalendarDayList(horarioDisponivel.getHorario()));
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
        eventDecorator = new EventDecorator(0, calendarDayList) {
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
