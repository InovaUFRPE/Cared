package bsi.ufrpe.br.cared.avaliacao.gui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.avaliacao.dominio.AvaliacaoForm;
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;

public class FormularioActivity extends AppCompatActivity {
    private RatingBar notaServico;
    private TextView comentarioServico;
    private Button enviar;
    private Cuidador cuidador;
    private AvaliacaoForm avaliacaoForm;

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


    }


}
