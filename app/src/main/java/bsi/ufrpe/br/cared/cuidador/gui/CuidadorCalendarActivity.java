package bsi.ufrpe.br.cared.cuidador.gui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calendario_cuidador_header, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.btAdd){
            startActivity(new Intent(CuidadorCalendarActivity.this, CadastrarHorarioDisponivelActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}
