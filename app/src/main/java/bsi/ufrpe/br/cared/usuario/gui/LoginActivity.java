package bsi.ufrpe.br.cared.usuario.gui;

import android.app.ProgressDialog;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.infra.servico.ServicoValidacao;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;
import bsi.ufrpe.br.cared.usuario.dominio.Usuario;

public class LoginActivity extends AppCompatActivity {
    private EditText emailLogin, senhaLogin;
    private Button botaoLogar;
    private ProgressDialog dialog;
    private ServicoValidacao servicoValidacao = new ServicoValidacao();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setView();

        botaoLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logIn();
            }
        });

    }

    private void setView() {
        botaoLogar = findViewById(R.id.loginId);
        emailLogin = findViewById(R.id.emailId);
        senhaLogin = findViewById(R.id.senhaId);

    }

    private void logIn() {
        if (!vericarCampos()) {
            return;
        }
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Sessao.getFirebaseAuth().signInWithEmailAndPassword(emailLogin.getText().toString().toLowerCase().trim(),
                senhaLogin.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("SignInSucces", "signInWithEmail:success");
                            FirebaseUser user = Sessao.getFirebaseAuth().getCurrentUser();
                            getUserData();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SignInFails", "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }
                });
    }

    private void getUserData(){
        Sessao.getDatabaseUser().child(Sessao.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                if (usuario.getTipoConta() == 0) {
                    getPessoa(usuario.getUserId());
                } else {
                    getCuidador(usuario.getUserId());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getPessoa(String id){
        Sessao.getDatabasePessoa().child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pessoa pessoa = dataSnapshot.getValue(Pessoa.class);
                Sessao.setPessoa(0, pessoa);
                dialog.dismiss();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getCuidador(String id){
        Sessao.getDatabaseCuidador().child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Cuidador cuidador = dataSnapshot.getValue(Cuidador.class);
                Sessao.setPessoa(1, cuidador);
                dialog.dismiss();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private boolean vericarCampos(){
        String email = emailLogin.getText().toString().trim();
        String senha = senhaLogin.getText().toString().trim();
        if (servicoValidacao.verificarCampoVazio(email)) {
            emailLogin.setError("Campo vazio");
            return false;
        } else if(servicoValidacao.verificarCampoVazio(senha)){
            senhaLogin.setError("Campo vazio");
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