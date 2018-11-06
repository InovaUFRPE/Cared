package bsi.ufrpe.br.cared.pessoa.gui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.infra.servico.CalendarTypeConverter;

public class EscolherHorarioFragment extends Fragment {
    private TextView dataInicio, dataFim;
    private Button eInicio, eFim, btBuscar;
    private LinearLayout linearLayout;
    private MaterialCalendarView mcv;
    private CalendarDay day1, day2;
    private Calendar calendar = CalendarTypeConverter.getTodayStart();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_escolher_horario, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        dataInicio = getView().findViewById(R.id.dataInicioId);
        dataFim = getView().findViewById(R.id.dataFimId);
        eInicio = getView().findViewById(R.id.eInicio);
        eFim = getView().findViewById(R.id.eFim);
        btBuscar = getView().findViewById(R.id.criar);
        linearLayout = getView().findViewById(R.id.layoutId);
        telaPadrao();
        mcv = getView().findViewById(R.id.calerdarViewId);
        btBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar temp2 = CalendarTypeConverter.setDayEnd(CalendarTypeConverter.calendarDayToCalendar(day2));
                Calendar temp = CalendarTypeConverter.setDayBegin(CalendarTypeConverter.calendarDayToCalendar(day1));
                Horario horario = new Horario(CalendarTypeConverter.calendarToLong(temp), CalendarTypeConverter.calendarToLong(temp2));
                Sessao.setHorario(horario);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.frameLayout, new ListaCuidadorFragment());
                ft.commit();
            }
        });
        eInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.setVisibility(View.GONE);
                mcv.setVisibility(View.VISIBLE);
                mcv.state().edit().setMinimumDate(CalendarTypeConverter.calendarToCalendarDay(calendar)).commit();
                escolherInicio();
            }
        });
        eFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayout.setVisibility(View.GONE);
                mcv.setVisibility(View.VISIBLE);
                mcv.state().edit().setMinimumDate(day1).commit();
                escolherFim();
            }
        });
    }

    private void escolherInicio(){
        mcv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
                day1 = calendarDay;
                dataInicio.setText(day1.getDay()+"/"+day1.getMonth()+"/"+day1.getYear());
                day2 = day1;
                dataFim.setText(day2.getDay()+"/"+day2.getMonth()+"/"+day2.getYear());
                mcv.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void escolherFim(){
        mcv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {
                day2 = calendarDay;
                dataFim.setText(day2.getDay()+"/"+day2.getMonth()+"/"+day2.getYear());
                mcv.setVisibility(View.GONE);
                linearLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void telaPadrao(){
        day1 = CalendarTypeConverter.calendarToCalendarDay(CalendarTypeConverter.getTodayStart());
        day2 = day1;
        dataInicio.setText(day1.getDay()+"/"+day1.getMonth()+"/"+day1.getYear());
        dataFim.setText(day2.getDay()+"/"+day2.getMonth()+"/"+day2.getYear());
    }


}
