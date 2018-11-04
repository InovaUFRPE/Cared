package bsi.ufrpe.br.cared.cuidador.gui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.horario.dominio.HorarioDisponivel;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.infra.servico.CalendarTypeConverter;

public class CadastrarHorarioDisponivelActivity extends AppCompatActivity {
    private MaterialCalendarView mcv;
    private TextView dataInicio, dataFim, eInicio, eFim;
    private Button btCriar;
    private CalendarDay day1, day2;
    private LinearLayout linearLayout;
    private Calendar calendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_horario_disponivel);
        dataInicio = findViewById(R.id.dataInicioId);
        dataFim = findViewById(R.id.dataFimId);
        eInicio = findViewById(R.id.eInicio);
        eFim = findViewById(R.id.eFim);
        btCriar = findViewById(R.id.criar);
        linearLayout = findViewById(R.id.layoutId);
        mcv = findViewById(R.id.calerdarViewId);
        btCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HorarioDisponivel horarioDisponivel = new HorarioDisponivel();
                horarioDisponivel.setUserId(Sessao.getUserId());
                horarioDisponivel.setHorario(new Horario(CalendarTypeConverter.calendarDayToLong(day1), CalendarTypeConverter.calendarDayToLong(day2)));
                String id = Sessao.getDatabaseHorarioDisponivel().push().getKey();
                horarioDisponivel.setId(id);
                Sessao.getDatabaseHorarioDisponivel().child(id).setValue(horarioDisponivel);
                finish();
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
}
