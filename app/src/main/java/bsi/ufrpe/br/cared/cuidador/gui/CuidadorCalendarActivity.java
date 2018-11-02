package bsi.ufrpe.br.cared.cuidador.gui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.infra.EventDecorator;

public class CuidadorCalendarActivity extends AppCompatActivity {
    private MaterialCalendarView mcv;
    private EventDecorator eventDecorator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuidador_calendar);
        mcv = findViewById(R.id.calerdarViewId);
        mcv.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView materialCalendarView, @NonNull CalendarDay calendarDay, boolean b) {

            }
        });
    }
}
