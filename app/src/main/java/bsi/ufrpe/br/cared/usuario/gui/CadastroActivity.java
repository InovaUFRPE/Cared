package bsi.ufrpe.br.cared.usuario.gui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import java.util.Arrays;
import java.util.List;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.cuidador.gui.CuidadorListActivity;
import bsi.ufrpe.br.cared.endereco.dominio.Endereco;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;
import bsi.ufrpe.br.cared.usuario.dominio.Usuario;

public class CadastroActivity extends AppCompatActivity {
    private EditText nome;
    private EditText cpf;
    private EditText telefone;
    private EditText email;
    private EditText senha;
    private Spinner contaSpinner;
    private EditText rua;
    private EditText numero;
    private EditText cidade;
    private EditText servico;
    private Button btConfirmar;
    private List<String> tiposConta = Arrays.asList("Cliente", "Cuidador");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);
        setView();

        contaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tipoConta(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount();
            }
        });
    }

    private void createAccount(){
        Sessao.getFirebaseAuth().createUserWithEmailAndPassword(email.getText().toString().trim().toLowerCase(),
                senha.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignInSuccess", "createUserWithEmail:success");
                    criarConta();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SignInFails", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(CadastroActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void criarConta(){
        if(contaSpinner.getSelectedItemPosition() == 0){
            Pessoa pessoa = criarPessoa();
            Sessao.getDatabasePessoa().child(Sessao.getUserId()).setValue(pessoa);
            Sessao.setPessoa(0, pessoa);
        }else{
            criarCuidador();
        }
        startActivity(new Intent(CadastroActivity.this, CuidadorListActivity.class));
        finish();
    }

    private Pessoa criarPessoa(){
        Usuario usuario = new Usuario(contaSpinner.getSelectedItemPosition(), Sessao.getUserId());
        Sessao.getDatabaseUser().child(Sessao.getUserId()).setValue(usuario);
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome.getText().toString().trim());
        pessoa.setCpf(cpf.getText().toString().trim());
        pessoa.setTelefone(telefone.getText().toString().trim());
        pessoa.setUserId(Sessao.getUserId());
        return pessoa;
    }

    private void criarCuidador(){
        Pessoa pessoa = criarPessoa();
        Endereco endereco = new Endereco();
        endereco.setCidade(cidade.getText().toString().trim());
        endereco.setNumero(numero.getText().toString().trim());
        endereco.setRua(rua.getText().toString().trim());
        Cuidador cuidador = new Cuidador();
        cuidador.setEndereco(endereco);
        cuidador.setPessoa(pessoa);
        cuidador.setServico(servico.getText().toString().trim());
        cuidador.setUserId(Sessao.getUserId());
        Sessao.getDatabaseCuidador().child(Sessao.getUserId()).setValue(cuidador);
        Sessao.setPessoa(1, cuidador);
    }

    private void setView(){
        nome = findViewById(R.id.nomeId);
        cpf = findViewById(R.id.cpfId);
        telefone = findViewById(R.id.telefoneId);
        email = findViewById(R.id.emailId);
        senha = findViewById(R.id.senhaId);
        contaSpinner = findViewById(R.id.tipoContaId);
        btConfirmar = findViewById(R.id.confirmarId);
        rua = findViewById(R.id.ruaId);
        numero = findViewById(R.id.numeroId);
        cidade = findViewById(R.id.cidadeId);
        servico = findViewById(R.id.servicoId);
        contaSpinner.setAdapter(new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item , tiposConta));
    }

    private void tipoConta(int i){
        if(i == 0){
            contaCliente();
        }else {
            contaCuidador();
        }
    }

    private void contaCuidador(){
        findViewById(R.id.enderecoTextoId).setVisibility(View.VISIBLE);
        findViewById(R.id.servicoTextoId).setVisibility(View.VISIBLE);
        rua.setVisibility(View.VISIBLE);
        numero.setVisibility(View.VISIBLE);
        cidade.setVisibility(View.VISIBLE);
        servico.setVisibility(View.VISIBLE);
    }

    private void contaCliente(){
        findViewById(R.id.enderecoTextoId).setVisibility(View.GONE);
        findViewById(R.id.servicoTextoId).setVisibility(View.GONE);
        rua.setVisibility(View.GONE);
        numero.setVisibility(View.GONE);
        cidade.setVisibility(View.GONE);
        servico.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CadastroActivity.this, LoginActivity.class));
        finish();
    }
}
