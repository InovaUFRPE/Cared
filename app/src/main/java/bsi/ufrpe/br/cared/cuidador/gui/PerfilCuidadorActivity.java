package bsi.ufrpe.br.cared.cuidador.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.horario.dominio.Agendamento;
import bsi.ufrpe.br.cared.horario.dominio.Situacao;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.infra.servico.ConflitoHorarios;

public class PerfilCuidadorActivity extends AppCompatActivity {
    private ImageView foto;
    private TextView nome, servicos, email, cidade, nota;
    private Button botaoContratar, botaoComentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cuidador);
        setTela();
    }

    private void setTela(){
        foto = findViewById(R.id.fotoPerfilCuidadorActivity);
        nome = findViewById(R.id.nomeCuidadorPerfilId);
        cidade = findViewById(R.id.enderecoCuidadoraId);
        servicos = findViewById(R.id.emailIdoso);
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
        Picasso.get()
                .load(Sessao.getCuidadorPerfil().getPessoa().getFoto())
                .resize(300, 300)
                .centerCrop()
                .into(foto);
        nome.setText(Sessao.getCuidadorPerfil().getPessoa().getNome());
        servicos.setText(Sessao.getCuidadorPerfil().getServico());
    }

    private void contratar(){
        Agendamento agendamento = new Agendamento();
        agendamento.setHorario(Sessao.getHorario());
        agendamento.setCuidadorId(Sessao.getCuidadorPerfil().getUserId());
        agendamento.setPacienteId(Sessao.getUserId());
        agendamento.setSituacao(Situacao.PENDENTE);
        ConflitoHorarios.newAgendamento(agendamento);
        finish();
    }
}
