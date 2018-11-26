package bsi.ufrpe.br.cared.pessoa.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;

public class EditarPerfilPessoaActivity extends AppCompatActivity {
    private EditText nomeEditar;
    private EditText telefoneEditar;
    private EditText cpfEditar;
    private Button alterar;
    private EditText necessidades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_pessoa);
        setTela();
        alterar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setAlterar();
            }
        });
    }

    private void setTela(){
        nomeEditar = findViewById(R.id.nomeId);
        telefoneEditar = findViewById(R.id.telefoneId);
        cpfEditar = findViewById(R.id.cpfId);
        alterar = findViewById(R.id.atualizarId);
        setPessoa();
    }

    private void setPessoa(){
        Pessoa pessoa = Sessao.getPessoa();
        nomeEditar.setText(pessoa.getNome());
        telefoneEditar.setText(pessoa.getTelefone());
        cpfEditar.setText(pessoa.getCpf());
    }

    private void setAlterar(){
        Pessoa pessoa = Sessao.getPessoa();
        String nome = nomeEditar.getText().toString().trim();
        String telefone = telefoneEditar.getText().toString().trim();
        String cpf = cpfEditar.getText().toString().trim();
        pessoa.setNome(nome);
        pessoa.setTelefone(telefone);
        pessoa.setCpf(cpf);
        Sessao.setPessoa(0, pessoa);
        Sessao.getDatabasePessoa().child(Sessao.getUserId()).setValue(Sessao.getPessoa());
        finish();
    }
}
