package bsi.ufrpe.br.cared.avaliacao.gui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.avaliacao.dominio.AvaliacaoForm;
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.horario.dominio.Agendamento;
import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;
import bsi.ufrpe.br.cared.usuario.gui.HomeActivity;

public class FormularioActivity extends AppCompatActivity {
    private RatingBar notaServico;
    private TextView comentarioServico;
    private Button enviar;
    private Cuidador cuidador;
    private AvaliacaoForm avaliacaoForm;
    private Agendamento agendamento;
    private Pessoa pessoa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
        setTela();
        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFormulario();
            }
        });
    }

    private void setTela() {
        notaServico = findViewById(R.id.notaServicoId);
        comentarioServico = findViewById(R.id.comentariosServico);
        enviar = findViewById(R.id.enviarAvaliacao);
        setFormulario();
    }

    private void setFormulario(){
        avaliacaoForm = new AvaliacaoForm();
        getExtras();
        avaliacaoForm.setComentario(comentarioServico.getText().toString().trim());
        avaliacaoForm.setIdAgendamento(agendamento.getId());
        avaliacaoForm.setNota(notaServico.getRating());
        avaliacaoForm.setIdAvaliador(Sessao.getUserId());
        avaliacaoForm.setIdAvaliado(agendamento.getCuidadorId());
        Sessao.getDatabaseAvaliacao().child(avaliacaoForm.getIdAvaliacao()).child(agendamento.getCuidadorId()).setValue(avaliacaoForm);
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }


    private void getAgendamento(){
        Sessao.getDatabaseAgendamento().child(Sessao.getUserId()).child(getId()).addListenerForSingleValueEvent(new ValueEventListener() {
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
        Sessao.getDatabasePessoa().child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pessoa = dataSnapshot.getValue(Pessoa.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private String getId(){
        Bundle extra = getIntent().getExtras();
        return extra.getString("id");
    }

    private void getExtras(){
        Bundle extra = getIntent().getExtras();
        agendamento = new Agendamento();
        agendamento.setId(extra.getString("id"));
        agendamento.setCuidadorId(extra.getString("idC"));
    }
}
