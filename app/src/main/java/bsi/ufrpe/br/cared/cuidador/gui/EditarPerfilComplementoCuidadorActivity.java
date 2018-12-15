package bsi.ufrpe.br.cared.cuidador.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.squareup.picasso.Picasso;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.infra.TipoUsuario;
import bsi.ufrpe.br.cared.usuario.gui.HomeActivity;

public class EditarPerfilComplementoCuidadorActivity extends AppCompatActivity {
    private RadioGroup radioGroupDormir, radioGroupCurso, radioGroupExperiencia;
    private RadioButton editarDormirDisponivel, editarDormirIndisponivel, editarPossuiCurso, editarNaoPossuiCurso, editarPossuiExperiencia, editarNaoPossuiExperiencia;
    private EditText editarListaCursos, editarExperienciaCuidador;
    private Button editarInfoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_complemento_cuidador);
        setEditarTelaComplementos();
        setCuidadorComplemento();
        editarInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setEditarInfoCuidador();
            }
        });
    }

    private void setEditarTelaComplementos(){
        radioGroupDormir = findViewById(R.id.radioGroupDormir);
        radioGroupCurso = findViewById(R.id.radioGroupCurso);
        radioGroupExperiencia = findViewById(R.id.radioGroupExperiencia);
        editarDormirDisponivel = findViewById(R.id.editarDormirSim);
        editarDormirIndisponivel = findViewById(R.id.editarDormirNão);
        editarPossuiCurso = findViewById(R.id.editarCursoSim);
        editarNaoPossuiCurso = findViewById(R.id.editarCursoNão);
        editarListaCursos = findViewById(R.id.editarCursoCuidador);
        editarPossuiExperiencia = findViewById(R.id.editarExperienciaSim);
        editarNaoPossuiExperiencia = findViewById(R.id.editarExperienciaNão);
        editarExperienciaCuidador = findViewById(R.id.editarResumoExperiencia);
        editarInfoButton = findViewById(R.id.editarComplementoButton);
    }

    private void setCuidadorComplemento() {
        Cuidador cuidador = Sessao.getCuidador();
        if (cuidador.getDisponivelDormir() == "SIM"){
            editarDormirDisponivel.setChecked(true);
        } else {
            editarDormirIndisponivel.setChecked(true);
        }
        if (cuidador.getPossuiCurso() == "SIM"){
            editarPossuiCurso.setChecked(true);
        } else {
            editarNaoPossuiCurso.setChecked(true);
        }
        if (cuidador.getExperiencia() == "SIM"){
            editarPossuiExperiencia.setChecked(true);
        } else {
            editarNaoPossuiExperiencia.setChecked(true);
        }
        editarListaCursos.setText(cuidador.getCurso());
        editarExperienciaCuidador.setText(cuidador.getResumoExperiencia());

    }

    private void setEditarInfoCuidador() {
        Cuidador cuidador = Sessao.getCuidador();
        String SIM = "SIM";
        String NAO = "NÃO";
        if (editarDormirDisponivel.isChecked()) {
            cuidador.setDisponivelDormir(SIM);
        } else if (editarDormirIndisponivel.isChecked()) {
            cuidador.setDisponivelDormir(NAO);
        }
        if (editarPossuiCurso.isChecked()) {
            cuidador.setPossuiCurso(SIM);
            String cursos = editarListaCursos.getText().toString().trim();
            cuidador.setCursosInformado(cursos);
        } else if (editarNaoPossuiCurso.isChecked()) {
            cuidador.setPossuiCurso(NAO);
        }
        if (editarPossuiExperiencia.isChecked()) {
            cuidador.setPossuiExperiencia(SIM);
            String resumoExperiencia = editarExperienciaCuidador.getText().toString().trim();
            cuidador.setResumoExperiencia(resumoExperiencia);
        } else if (editarNaoPossuiExperiencia.isChecked()) {
            cuidador.setPossuiExperiencia(NAO);
        }
        Sessao.getDatabaseCuidador().child(Sessao.getUserId()).setValue(Sessao.getCuidador());
        Sessao.setPessoa(TipoUsuario.CUIDADOR, cuidador);
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }
}
