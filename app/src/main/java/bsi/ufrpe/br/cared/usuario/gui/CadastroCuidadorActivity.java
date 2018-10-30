package bsi.ufrpe.br.cared.usuario.gui;

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
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.endereco.dominio.Endereco;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;
import bsi.ufrpe.br.cared.usuario.dominio.Usuario;

public class CadastroCuidadorActivity extends AppCompatActivity {
    private EditText nome;
    private EditText cpf;
    private EditText telefone;
    private EditText email;
    private EditText senha;
    private EditText rua;
    private EditText numero;
    private EditText cidade;
    private EditText servico;
    private Button btConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cuidador);
        setView();
        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickConfirmar();
            }
        });
    }

    private void setView(){
        nome = findViewById(R.id.nomeId);
        cpf = findViewById(R.id.cpfId);
        telefone = findViewById(R.id.telefoneId);
        email = findViewById(R.id.emailId);
        senha = findViewById(R.id.senhaId);
        btConfirmar = findViewById(R.id.confirmarId);
        rua = findViewById(R.id.ruaId);
        numero = findViewById(R.id.numeroId);
        cidade = findViewById(R.id.cidadeId);
        servico = findViewById(R.id.servicoId);
    }

    private void cirarConta(){
        Sessao.getFirebaseAuth().createUserWithEmailAndPassword(email.getText().toString().trim().toLowerCase(),
                senha.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignInSuccess", "createUserWithEmail:success");
                    criarUsuario();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SignInFails", "createUserWithEmail:failure", task.getException());
                    Toast.makeText(CadastroCuidadorActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void criarUsuario(){
        Usuario usuario = new Usuario(1, Sessao.getUserId());
        Sessao.getDatabaseUser().child(Sessao.getUserId()).setValue(usuario);
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(nome.getText().toString().trim());
        pessoa.setCpf(cpf.getText().toString().trim());
        pessoa.setTelefone(telefone.getText().toString().trim());
        pessoa.setUserId(Sessao.getUserId());
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

    private void clickConfirmar(){
        cirarConta();
    }
}
