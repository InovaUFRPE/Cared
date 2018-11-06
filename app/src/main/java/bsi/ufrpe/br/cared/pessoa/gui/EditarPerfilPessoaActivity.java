package bsi.ufrpe.br.cared.pessoa.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_pessoa);
        setTela();
    }

    private void setTela(){
        nomeEditar = findViewById(R.id.nomeId);
        telefoneEditar = findViewById(R.id.telefoneId);
        cpfEditar = findViewById(R.id.cpfId);
        alterar = findViewById(R.id.confirmarId);
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
        startActivity(new Intent(EditarPerfilPessoaActivity.this, PessoaPerfilActivity.class));
        EditarPerfilPessoaActivity.this.finish();
    }
}
