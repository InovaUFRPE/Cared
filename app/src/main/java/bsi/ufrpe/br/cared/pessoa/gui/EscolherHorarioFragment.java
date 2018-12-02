package bsi.ufrpe.br.cared.pessoa.gui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;

import java.util.Calendar;
import java.util.Date;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.infra.servico.CalendarTypeConverter;

public class EscolherHorarioFragment extends Fragment implements CalendarDatePickerDialogFragment.OnDateSetListener {
    private Button dInicio, dFim, btBuscar, hInicio, hFim;
    private Calendar calendar = CalendarTypeConverter.getTodayStart();
    private boolean inicioClicked = false;
    private Calendar cInicio = CalendarTypeConverter.getTodayStart(), cFim = CalendarTypeConverter.getTodayStart();
    private Date date1 = new Date(), date2 = new Date();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_escolher_horario, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configurar();
    }

    private void configurar(){
        dInicio = getView().findViewById(R.id.dInicio);
        dFim = getView().findViewById(R.id.dFim);
        btBuscar = getView().findViewById(R.id.buscar);

        btBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickBuscar();
            }
        });
        dInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickDInicio();
            }
        });
        dFim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickDFim();
            }
        });

        date1.setTime(calendar.getTimeInMillis());
        date2.setTime(calendar.getTimeInMillis());
        dInicio.setText(CalendarTypeConverter.getSdfd().format(date1));
        dFim.setText(CalendarTypeConverter.getSdfd().format(date2));
    }

    public interface PassarHorario{
        void passarHora(Horario horario);
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        if (inicioClicked) {
            cInicio.set(year, monthOfYear, dayOfMonth);
            date1.setTime(cInicio.getTimeInMillis());
            dInicio.setText(CalendarTypeConverter.getSdfd().format(date1));
            if (cInicio.getTimeInMillis() > cFim.getTimeInMillis()){
                cFim.set(year, monthOfYear, dayOfMonth);
                date2.setTime(cFim.getTimeInMillis());
                dFim.setText(CalendarTypeConverter.getSdfd().format(date2));
            }
        } else{
            cFim.set(year, monthOfYear, dayOfMonth);
            date2.setTime(cFim.getTimeInMillis());
            dFim.setText(CalendarTypeConverter.getSdfd().format(date2));
        }
    }

    private void clickBuscar(){
        Horario horario = new Horario(CalendarTypeConverter.calendarToLong(cInicio), CalendarTypeConverter.calendarToLong(cFim));
        PassarHorario passarHorario = (PassarHorario) getActivity();
        passarHorario.passarHora(horario);
    }

    private void clickDInicio(){
        inicioClicked = true;
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(EscolherHorarioFragment.this)
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setPreselectedDate(cInicio.get(Calendar.YEAR), cInicio.get(Calendar.MONTH), cInicio.get(Calendar.DAY_OF_MONTH))
                .setDateRange(CalendarTypeConverter.calendarToCalendarDay2(calendar), null);
        cdp.show(getActivity().getSupportFragmentManager(), null);
    }

    private void clickDFim(){
        inicioClicked = false;
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(EscolherHorarioFragment.this)
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setPreselectedDate(cFim.get(Calendar.YEAR), cFim.get(Calendar.MONTH), cFim.get(Calendar.DAY_OF_MONTH))
                .setDateRange(CalendarTypeConverter.calendarToCalendarDay2(cInicio), null);
        cdp.show(getActivity().getSupportFragmentManager(), null);
    }
}
