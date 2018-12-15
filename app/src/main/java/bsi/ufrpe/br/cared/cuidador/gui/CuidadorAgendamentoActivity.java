package bsi.ufrpe.br.cared.cuidador.gui;

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
import bsi.ufrpe.br.cared.horario.dominio.Agendamento;
import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.horario.dominio.Situacao;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;

public class CuidadorAgendamentoActivity extends AppCompatActivity {
    private TextView nome, horario, situacao;
    private Button aceitar, recusar;
    private Agendamento agendamento;
    private Pessoa pessoa;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuidador_agendamento);
        setTela();
    }

    private void setTela(){
        nome = findViewById(R.id.nomeId);
        horario = findViewById(R.id.horarioId);
        situacao = findViewById(R.id.situacaoId);
        aceitar = findViewById(R.id.aceitarId);
        recusar = findViewById(R.id.recusarId);
        getAgendamento();
    }

    private void setButtons(){
        if (agendamento.getSituacao().equals(Situacao.PENDENTE)){
            aceitar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickAceitar();
                }
            });
            recusar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickRecusar();
                }
            });
        }else if (agendamento.getSituacao().equals(Situacao.ACEITO)){
            aceitar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    clickCancelar();
                }
            });
            aceitar.setText("CANCELAR");
            recusar.setVisibility(View.GONE);
        }else{
            aceitar.setVisibility(View.GONE);
            recusar.setVisibility(View.GONE);
        }
    }

    private String getId(){
        Bundle extra = getIntent().getExtras();
        return extra.getString("id");
    }

    private void clickAceitar(){
        agendamento.setSituacao(Situacao.ACEITO);
        Sessao.getDatabaseAgendamento().child(agendamento.getCuidadorId()).child(agendamento.getId()).setValue(agendamento);
        finish();
    }

    private void clickRecusar(){
        agendamento.setSituacao(Situacao.RECUSADO);
        Sessao.getDatabaseAgendamento().child(agendamento.getCuidadorId()).child(agendamento.getId()).setValue(agendamento);
        finish();
    }

    private void clickCancelar(){
        agendamento.setSituacao(Situacao.CANCELADO);
        Sessao.getDatabaseAgendamento().child(agendamento.getCuidadorId()).child(agendamento.getId()).setValue(agendamento);
    }

    private void getAgendamento(){
        Sessao.getDatabaseAgendamento().child(Sessao.getUserId()).child(getId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                agendamento = dataSnapshot.getValue(Agendamento.class);
                getPessoa(agendamento.getPacienteId());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getPessoa(String id){
        Sessao.getDatabasePessoa().child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pessoa = dataSnapshot.getValue(Pessoa.class);
                setTexts();
                setButtons();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setTexts(){
        nome.setText(pessoa.getNome());
        horario.setText(textoHorario(agendamento.getHorario()));
        situacao.setText(agendamento.getSituacao().getName());
    }

    private String textoHorario(Horario horario){
        Date data1 = new Date(horario.getInicio());
        Date data2 = new Date(horario.getFim());
        return sdf.format(data1) + " - " + sdf.format(data2);
    }
}
