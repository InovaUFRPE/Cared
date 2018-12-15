package bsi.ufrpe.br.cared.cuidador.gui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment;
import com.codetroopers.betterpickers.radialtimepicker.RadialTimePickerDialogFragment;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;
import java.util.Date;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.horario.dominio.HorarioDisponivel;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.infra.servico.CalendarTypeConverter;
import bsi.ufrpe.br.cared.infra.servico.ConflitoHorarios;

public class CadastrarHorarioDisponivelActivity extends AppCompatActivity
        implements CalendarDatePickerDialogFragment.OnDateSetListener, RadialTimePickerDialogFragment.OnTimeSetListener{
    private Button dInicio, dFim, btCriar;
    private boolean dInicioClicked = false;
    private Calendar cInicio = CalendarTypeConverter.getEmptyCalendar(), cFim = CalendarTypeConverter.getEmptyCalendar();
    private Date date1 = new Date(), date2 = new Date();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_horario_disponivel);
        dInicio = findViewById(R.id.dInicio);
        dFim = findViewById(R.id.dFim);
        btCriar = findViewById(R.id.buscar);

        btCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCriar();
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

    private void clickDInicio(){
        dInicioClicked = true;
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(CadastrarHorarioDisponivelActivity.this)
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setPreselectedDate(cInicio.get(Calendar.YEAR), cInicio.get(Calendar.MONTH), cInicio.get(Calendar.DAY_OF_MONTH))
                .setDateRange(CalendarTypeConverter.calendarToCalendarDay2(calendar), null);
        cdp.show(getSupportFragmentManager(), null);
    }

    private void clickDFim(){
        dInicioClicked = false;
        Calendar calendar = CalendarTypeConverter.getEmptyCalendar();
        calendar.setTimeInMillis(cInicio.getTimeInMillis());
        calendar.add(Calendar.HOUR_OF_DAY, 1);
        CalendarDatePickerDialogFragment cdp = new CalendarDatePickerDialogFragment()
                .setOnDateSetListener(CadastrarHorarioDisponivelActivity.this)
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setPreselectedDate(cFim.get(Calendar.YEAR), cFim.get(Calendar.MONTH), cFim.get(Calendar.DAY_OF_MONTH))
                .setDateRange(CalendarTypeConverter.calendarToCalendarDay2(calendar), null);
        cdp.show(getSupportFragmentManager(), null);
    }

    private void clickHInicio(){
        RadialTimePickerDialogFragment rtp = new RadialTimePickerDialogFragment()
                .setOnTimeSetListener(CadastrarHorarioDisponivelActivity.this);
        rtp.show(getSupportFragmentManager(), null);
    }

    private void clickHFim(){
        RadialTimePickerDialogFragment rtp = new RadialTimePickerDialogFragment()
                .setOnTimeSetListener(CadastrarHorarioDisponivelActivity.this);
        rtp.show(getSupportFragmentManager(), null);
    }

    private void clickCriar(){
        HorarioDisponivel horarioDisponivel = new HorarioDisponivel();
        horarioDisponivel.setUserId(Sessao.getUserId());
        horarioDisponivel.setHorario(new Horario(cInicio.getTimeInMillis(), cFim.getTimeInMillis()));
        String id = Sessao.getDatabaseHorarioDisponivel().child(Sessao.getUserId()).push().getKey();
        horarioDisponivel.setId(id);
        ConflitoHorarios.newHorarioDisponivel(horarioDisponivel);
        finish();
    }
}