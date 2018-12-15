package bsi.ufrpe.br.cared.pessoa.gui;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.horario.dominio.Agendamento;
import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.horario.dominio.Situacao;
import bsi.ufrpe.br.cared.infra.Sessao;

public class PessoaAgendamentoActivity extends AppCompatActivity {
    private TextView nome, horario, situacao;
    private Button cancelar;
    private Agendamento agendamento;
    private Cuidador cuidador;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa_agendamento);
        setTela();
    }

    private void setTela(){
        nome = findViewById(R.id.nomeId);
        horario = findViewById(R.id.horarioId);
        situacao = findViewById(R.id.situacaoId);
        cancelar = findViewById(R.id.cancelarId);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCancelar();
            }
        });
        getAgendamento();
    }

    private String getId(){
        Bundle extra = getIntent().getExtras();
        return extra.getString("id");
    }

    private String getCuidadorId(){
        Bundle extra = getIntent().getExtras();
        return extra.getString("idC");
    }

    private void clickCancelar(){
        agendamento.setSituacao(Situacao.CANCELADO);
        Sessao.getDatabaseAgendamento().child(agendamento.getCuidadorId()).child(agendamento.getId()).setValue(agendamento);
    }

    private void getAgendamento(){
        Sessao.getDatabaseAgendamento().child(getCuidadorId()).child(getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                agendamento = dataSnapshot.getValue(Agendamento.class);
                getPessoa(agendamento.getCuidadorId());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getPessoa(String id){
        Sessao.getDatabaseCuidador().child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cuidador = dataSnapshot.getValue(Cuidador.class);
                setTexts();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



    private void setTexts(){
        nome.setText(cuidador.getPessoa().getNome());
        horario.setText(textoHorario(agendamento.getHorario()));
        situacao.setText(agendamento.getSituacao().getName());
    }

    private String textoHorario(Horario horario){
        Date data1 = new Date(horario.getInicio());
        Date data2 = new Date(horario.getFim());
        return sdf.format(data1) + " - " + sdf.format(data2);
    }
}
