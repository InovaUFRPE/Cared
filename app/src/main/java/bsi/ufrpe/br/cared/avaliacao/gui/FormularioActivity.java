package bsi.ufrpe.br.cared.avaliacao.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.avaliacao.dominio.AvaliacaoForm;
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.horario.dominio.Agendamento;
import bsi.ufrpe.br.cared.infra.Sessao;

public class FormularioActivity extends AppCompatActivity {
    private RatingBar notaServico;
    private TextView comentarioServico;
    private Button enviar;
    private Cuidador cuidador;
    private AvaliacaoForm avaliacaoForm;
    private Agendamento agendamento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario);
    }

    private void setTela() {
        notaServico = findViewById(R.id.notaServicoId);
        comentarioServico = findViewById(R.id.comentariosServico);
        enviar = findViewById(R.id.enviarAvaliacao);
    }

    private void setFormulario(){
        avaliacaoForm.setComentario(comentarioServico.getText().toString().trim());
//        avaliacaoForm.setIdAgendamento();
        avaliacaoForm.setNota(notaServico.getRating());
        avaliacaoForm.setIdAvaliador(Sessao.getUserId());
        //falta pegar o id do avaliado e pegar o id do agendamento
        //setar o id avaliado como chave

    }


}
