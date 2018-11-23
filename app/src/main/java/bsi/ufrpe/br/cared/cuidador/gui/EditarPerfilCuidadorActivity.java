package bsi.ufrpe.br.cared.cuidador.gui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.endereco.dominio.Endereco;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;
import bsi.ufrpe.br.cared.pessoa.gui.EditarPerfilPessoaActivity;
import bsi.ufrpe.br.cared.pessoa.gui.PerfilPessoaFragment;

public class EditarPerfilCuidadorActivity extends AppCompatActivity {
    private EditText nomeEditar;
    private EditText telefoneEditar;
    private EditText cpfEditar;
    private EditText ruaEditar;
    private EditText numeroEditar;
    private EditText cidadeEditar;
    private EditText bairroEditar;
    private EditText valorEditar;
    private EditText descricaoEditar;
    private Button alterar;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_cuidador);
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
        alterar = findViewById(R.id.confirmarId);
        ruaEditar = findViewById(R.id.ruaId);
        numeroEditar = findViewById(R.id.numeroId);
        bairroEditar = findViewById(R.id.bairroId);
        cidadeEditar = findViewById(R.id.cidadeId);
        valorEditar = findViewById(R.id.precoId);
        descricaoEditar = findViewById(R.id.servicoId);
        setCuidador();
    }

    private void setCuidador(){
        Cuidador cuidador = Sessao.getCuidador();
        nomeEditar.setText(cuidador.getPessoa().getNome());
        telefoneEditar.setText(cuidador.getPessoa().getTelefone());
        cpfEditar.setText(cuidador.getPessoa().getCpf());
        ruaEditar.setText(cuidador.getEndereco().getRua());
        numeroEditar.setText(cuidador.getEndereco().getNumero());
        bairroEditar.setText(cuidador.getEndereco().getBairro());
        cidadeEditar.setText(cuidador.getEndereco().getCidade());
        valorEditar.setText(String.valueOf(cuidador.getValor()));
        descricaoEditar.setText(cuidador.getServico());
    }

    private void setAlterar(){
        Cuidador cuidador = Sessao.getCuidador();
        String nome = nomeEditar.getText().toString().trim();
        String telefone = telefoneEditar.getText().toString().trim();
        String cpf = cpfEditar.getText().toString().trim();
        String rua = ruaEditar.getText().toString().trim();
        String numero = numeroEditar.getText().toString().trim();
        String bairro = bairroEditar.getText().toString().trim();
        String cidade = cidadeEditar.getText().toString().trim();
        String valor = valorEditar.getText().toString().trim();
        String descricao = descricaoEditar.getText().toString().trim();
        Pessoa pessoa = cuidador.getPessoa();
        Endereco endereco = cuidador.getEndereco();
        cuidador.setServico(descricao);
        cuidador.setValor(Double.parseDouble(valor));
        pessoa.setNome(nome);
        pessoa.setCpf(cpf);
        pessoa.setTelefone(telefone);
        endereco.setBairro(bairro);
        endereco.setCidade(cidade);
        endereco.setNumero(numero);
        endereco.setRua(rua);
        cuidador.setPessoa(pessoa);
        cuidador.setEndereco(endereco);
        Sessao.setPessoa(1, cuidador);
        Sessao.getDatabaseCuidador().child(Sessao.getUserId()).setValue(Sessao.getCuidador());
        finish();
    }
}
