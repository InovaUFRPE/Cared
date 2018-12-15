package bsi.ufrpe.br.cared.pessoa.gui;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;

import java.util.Calendar;
import java.util.Date;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.infra.servico.CalendarTypeConverter;

public class EscolherHorarioFragment extends Fragment
        implements CalendarDatePickerDialogFragment.OnDateSetListener, RadialTimePickerDialogFragment.OnTimeSetListener {
    private Button dInicio, dFim, btBuscar;
    private boolean dInicioClicked = false;
    private Calendar cInicio = CalendarTypeConverter.getEmptyCalendar(), cFim = CalendarTypeConverter.getEmptyCalendar();
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

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        cInicio.setTimeInMillis(calendar.getTimeInMillis());
        calendar.add(Calendar.HOUR_OF_DAY,1);
        cFim.setTimeInMillis(calendar.getTimeInMillis());

        date1.setTime(cInicio.getTimeInMillis());
        date2.setTime(cFim.getTimeInMillis());
        dInicio.setText(CalendarTypeConverter.getSdfdh().format(date1));
        dFim.setText(CalendarTypeConverter.getSdfdh().format(date2));
    }

    public interface PassarHorario{
        void passarHora(Horario horario);
    }

    @Override
    public void onDateSet(CalendarDatePickerDialogFragment dialog, int year, int monthOfYear, int dayOfMonth) {
        if (dInicioClicked) {
            cInicio.set(year, monthOfYear, dayOfMonth);
            date1.setTime(cInicio.getTimeInMillis());
            dInicio.setText(CalendarTypeConverter.getSdfdh().format(date1));
            Calendar calendar = CalendarTypeConverter.getEmptyCalendar();
            calendar.setTimeInMillis(cInicio.getTimeInMillis());
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            if (calendar.getTimeInMillis() > cFim.getTimeInMillis()){
                cFim.setTimeInMillis(cInicio.getTimeInMillis() + 3600000L);
                date2.setTime(cFim.getTimeInMillis());
                dFim.setText(CalendarTypeConverter.getSdfdh().format(date2));
            }
            clickHInicio();
        }else {
            cFim.set(year, monthOfYear, dayOfMonth);
            date2.setTime(cFim.getTimeInMillis());
            dFim.setText(CalendarTypeConverter.getSdfdh().format(date2));
            clickHFim();
        }
    }

    @Override
    public void onTimeSet(RadialTimePickerDialogFragment dialog, int hourOfDay, int minute) {
        if (dInicioClicked){
            cInicio.set(Calendar.HOUR_OF_DAY, hourOfDay);
            cInicio.set(Calendar.MINUTE, minute);
            date1.setTime(cInicio.getTimeInMillis());
            dInicio.setText(CalendarTypeConverter.getSdfdh().format(date1));
            Calendar calendar = CalendarTypeConverter.getEmptyCalendar();
            calendar.setTimeInMillis(cInicio.getTimeInMillis());
            calendar.add(Calendar.HOUR_OF_DAY, 1);
            if (calendar.getTimeInMillis() > cFim.getTimeInMillis()){
                cFim.setTimeInMillis(calendar.getTimeInMillis());
                date2.setTime(cFim.getTimeInMillis());
                dFim.setText(CalendarTypeConverter.getSdfdh().format(date2));
            }
        }else {
            Calendar calendar = CalendarTypeConverter.getEmptyCalendar();
            calendar.setTimeInMillis(cFim.getTimeInMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calendar.set(Calendar.MINUTE, minute);
            Calendar calendar1 = CalendarTypeConverter.getEmptyCalendar();
            calendar1.setTimeInMillis(cInicio.getTimeInMillis());
            calendar1.add(Calendar.HOUR_OF_DAY, 1);
            if (!(calendar1.getTimeInMillis() > calendar.getTimeInMillis())){
                cFim.setTimeInMillis(calendar.getTimeInMillis());
                date2.setTime(cFim.getTimeInMillis());
                dFim.setText(CalendarTypeConverter.getSdfdh().format(date2));
            }else {
                cFim.setTimeInMillis(calendar1.getTimeInMillis());
                date2.setTime(cFim.getTimeInMillis());
                dFim.setText(CalendarTypeConverter.getSdfdh().format(date2));
            }

        }
    }

    private void clickBuscar(){
        Horario horario = new Horario(CalendarTypeConverter.calendarToLong(cInicio), CalendarTypeConverter.calendarToLong(cFim));
        PassarHorario passarHorario = (PassarHorario) getActivity();
        passarHorario.passarHora(horario);
    }

    private void clickDInicio(){
        dInicioClicked = true;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(EscolherHorarioFragment.this)
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setPreselectedDate(cInicio.get(Calendar.YEAR), cInicio.get(Calendar.MONTH), cInicio.get(Calendar.DAY_OF_MONTH))
                .setDateRange(CalendarTypeConverter.calendarToCalendarDay2(calendar), null);
        cdp.show(getActivity().getSupportFragmentManager(), null);
    }

    private void clickDFim(){
        dInicioClicked = false;
        Calendar calendar = CalendarTypeConverter.getEmptyCalendar();
        calendar.setTimeInMillis(cInicio.getTimeInMillis());
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(EscolherHorarioFragment.this)
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setPreselectedDate(cFim.get(Calendar.YEAR), cFim.get(Calendar.MONTH), cFim.get(Calendar.DAY_OF_MONTH))
                .setDateRange(CalendarTypeConverter.calendarToCalendarDay2(calendar), null);
        cdp.show(getActivity().getSupportFragmentManager(), null);
    }

    private void clickHInicio(){
        RadialTimePickerDialogFragment rtp = new RadialTimePickerDialogFragment()
                .setOnTimeSetListener(EscolherHorarioFragment.this);
        rtp.show(getActivity().getSupportFragmentManager(), null);
    }

    private void clickHFim(){
        RadialTimePickerDialogFragment rtp = new RadialTimePickerDialogFragment()
                .setOnTimeSetListener(EscolherHorarioFragment.this);
        rtp.show(getActivity().getSupportFragmentManager(), null);
    }
}
