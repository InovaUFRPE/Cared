package bsi.ufrpe.br.cared.cuidador.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.horario.dominio.Agendamento;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.infra.servico.ConflitoHorarios;

public class PerfilCuidadorActivity extends AppCompatActivity {
    private TextView nome;
    private TextView servicos;
    private TextView email;
    private TextView cidade;
    private Button botaoContratar;
    private Button botaoComentarios;
    private TextView nota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cuidador);
        setTela();
    }

    private void setTela(){
        nome = findViewById(R.id.nomeCuidadorPerfilId);
        cidade = findViewById(R.id.enderecoCuidadoraId);
        servicos = findViewById(R.id.descricaoId);
        botaoContratar = findViewById(R.id.btContratarId);
        botaoContratar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contratar();
            }
        });
        setCuidador();
    }

    private void setCuidador(){
        nome.setText(Sessao.getCuidadorPerfil().getPessoa().getNome());
        servicos.setText(Sessao.getCuidadorPerfil().getServico());
        cidade.setText(Sessao.getCuidadorPerfil().getEndereco().getCidade());
    }

    private void contratar(){
        Agendamento agendamento = new Agendamento();
        agendamento.setHorario(Sessao.getHorario());
        agendamento.setCuidadorId(Sessao.getCuidadorPerfil().getUserId());
        agendamento.setPacienteId(Sessao.getUserId());
        ConflitoHorarios.newAgendamento(agendamento);
        finish();
    }
}
