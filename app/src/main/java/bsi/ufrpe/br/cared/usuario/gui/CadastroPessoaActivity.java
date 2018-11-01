package bsi.ufrpe.br.cared.usuario.gui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.infra.servico.ServicoValidacao;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;
import bsi.ufrpe.br.cared.usuario.dominio.Usuario;

public class CadastroPessoaActivity extends AppCompatActivity {
    private EditText campoNome, campoCPF, campoTelefone, campoEmail, campoSenha;
    private Button btConfirmar;
    private ServicoValidacao servicoValidacao = new ServicoValidacao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_pessoa);
        setView();
        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickConfirmar();
            }
        });
    }

    private void setView(){
        campoNome = findViewById(R.id.nomeId);
        campoCPF = findViewById(R.id.cpfId);
        campoTelefone = findViewById(R.id.telefoneId);
        campoEmail = findViewById(R.id.emailId);
        campoSenha = findViewById(R.id.senhaId);
        btConfirmar = findViewById(R.id.confirmarId);
    }

    private void cirarConta(){
        if (!vericarCampos()){
            return;
        }
        Sessao.getFirebaseAuth().createUserWithEmailAndPassword(campoEmail.getText().toString().trim().toLowerCase(),
                campoSenha.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignInSuccess", "createUserWithEmail:success");
                    criarUsuario();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SignInFails", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(CadastroPessoaActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void criarUsuario(){
        Usuario usuario = new Usuario(0, Sessao.getUserId());
        Sessao.getDatabaseUser().child(Sessao.getUserId()).setValue(usuario);
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(campoNome.getText().toString().trim());
        pessoa.setCpf(campoCPF.getText().toString().trim());
        pessoa.setTelefone(campoTelefone.getText().toString().trim());
        pessoa.setUserId(Sessao.getUserId());
        Sessao.setPessoa(0, pessoa);
    }

    private void clickConfirmar(){
        cirarConta();
    }

    private boolean vericarCampos(){
        String nome = campoNome.getText().toString().trim();
        String cpf = campoCPF.getText().toString().trim();
        String telefone = campoTelefone.getText().toString().trim();
        String email = campoEmail.getText().toString().trim();
        String senha = campoSenha.getText().toString().trim();
        if (servicoValidacao.verificarCampoVazio(nome)) {
            campoNome.setError("Campo vazio");
            return false;
        } else if(servicoValidacao.verificarCampoVazio(cpf)){
            campoCPF.setError("Campo vazio");
            return false;
        } else if(servicoValidacao.verificarCampoVazio(telefone)){
            campoTelefone.setError("Campo vazio");
            return false;
        } else if(servicoValidacao.verificarCampoVazio(email)){
            campoEmail.setError("Campo vazio");
            return false;
        } else if(servicoValidacao.verificarCampoVazio(senha)){
            campoSenha.setError("Campo vazio");
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
