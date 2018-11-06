package bsi.ufrpe.br.cared.usuario.gui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;
import bsi.ufrpe.br.cared.usuario.dominio.Usuario;

public class Main2Activity extends AppCompatActivity {
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        if (Sessao.getFirebaseAuth().getCurrentUser() != null){
            dialog = new ProgressDialog(Main2Activity.this);
            dialog.setMessage("Please wait...");
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
            getUser();
        }else {
            startActivity(new Intent(Main2Activity.this, MainActivity.class));
        }
    }

    private void getUser(){
        Sessao.getDatabaseUser().child(Sessao.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
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
        Sessao.getDatabasePessoa().child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pessoa pessoa = dataSnapshot.getValue(Pessoa.class);
                Sessao.setPessoa(0, pessoa);
                dialog.dismiss();
                startActivity(new Intent(Main2Activity.this, HomeActivity.class));
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getCuidador(String id){
        Sessao.getDatabaseCuidador().child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Cuidador cuidador = dataSnapshot.getValue(Cuidador.class);
                Sessao.setPessoa(1, cuidador);
                dialog.dismiss();
                startActivity(new Intent(Main2Activity.this, HomeActivity.class));
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
