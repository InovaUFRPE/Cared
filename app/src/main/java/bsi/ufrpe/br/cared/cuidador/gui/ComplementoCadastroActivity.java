package bsi.ufrpe.br.cared.cuidador.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.usuario.gui.HomeActivity;

public class ComplementoCadastroActivity extends AppCompatActivity {
    private CheckBox dormirDisponivel, dormirIndisponivel, possuiCurso, naoPossuiCurso, possuiExperiencia, naoPossuiExperiencia;
    private EditText listaCursos, experienciaCuidador;
    private Button infoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complemento_cadastro);
        setTelaComplementos();
        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInfoCuidador();
            }
        });
    }

    private void setTelaComplementos(){
        dormirDisponivel = findViewById(R.id.dormirSim);
        dormirIndisponivel = findViewById(R.id.dormirNão);
        possuiCurso = findViewById(R.id.cursoSim);
        naoPossuiCurso = findViewById(R.id.cursoNão);
        listaCursos = findViewById(R.id.cursoCuidador);
        possuiExperiencia = findViewById(R.id.experienciaSim);
        naoPossuiExperiencia = findViewById(R.id.experienciaNão);
        experienciaCuidador = findViewById(R.id.resumoExperiencia);
        infoButton = findViewById(R.id.complementoButton);
    }

    private void setInfoCuidador(){
        Cuidador cuidador = Sessao.getCuidador();
        String SIM = "SIM";
        String NAO = "NÃO";
        if (dormirDisponivel.isChecked() && !dormirIndisponivel.isChecked()){
            cuidador.setDisponivelDormir(SIM);
        } else if(dormirIndisponivel.isChecked() && !dormirDisponivel.isChecked()){
            cuidador.setDisponivelDormir(NAO);
        }
        if (possuiCurso.isChecked() && !naoPossuiCurso.isChecked()){
            cuidador.setPossuiCurso(SIM);
            String cursos = listaCursos.getText().toString().trim();
            cuidador.setCursosInformado(cursos);
        } else if(naoPossuiCurso.isChecked() && !possuiCurso.isChecked()) {
            cuidador.setPossuiCurso(NAO);
        }
        if (possuiExperiencia.isChecked() && !naoPossuiExperiencia.isChecked()){
            cuidador.setPossuiExperiencia(SIM);
            String resumoExperiencia = experienciaCuidador.getText().toString().trim();
            cuidador.setResumoExperiencia(resumoExperiencia);
        } else if (naoPossuiExperiencia.isChecked() && !possuiExperiencia.isChecked()) {
            cuidador.setPossuiExperiencia(NAO);
        }
        Sessao.getDatabaseCuidador().child(Sessao.getUserId()).setValue(Sessao.getCuidador());
        Sessao.setPessoa(1, cuidador);
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    private void trocarTela(){
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
