package bsi.ufrpe.br.cared.pessoa.gui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.cuidador.gui.EditarPerfilCuidadorActivity;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.infra.servico.ServicoValidacao;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;

public class EditarPerfilPessoaActivity extends AppCompatActivity {
    private EditText nomePessoaEditar, telefonePessoaEditar, necessidadesPessoaEditar;
    private Button botaoAlterarDadosPessoa, botaoAlterarFotoPessoa;
    private ImageView fotoPessoaAlterar;
    private ServicoValidacao servicoValidacao = new ServicoValidacao();
    private String urlFoto;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_pessoa);
        setTela();
        botaoAlterarFotoPessoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pegarImagem();
            }
        });
        botaoAlterarDadosPessoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!vericarCampos()) {
                    return;
                } else {
                    inserirImagemBanco();
                }
            }
        });
    }

    private void pegarImagem() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent, "Selecione Foto"), PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null)
        {
            filePath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                fotoPessoaAlterar.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void inserirImagemBanco(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Carregando...");
        progressDialog.show();
        final StorageReference ref = storageReference.child("images/"+ UUID.randomUUID().toString());
        ref.putFile(filePath).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()){
                    throw task.getException();
                }
                return ref.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    Uri downUri = task.getResult();
                    urlFoto = downUri.toString();
                    setAlterar();
                    progressDialog.dismiss();
                    Toast.makeText(EditarPerfilPessoaActivity.this, "Carregado", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(EditarPerfilPessoaActivity.this, "Falhou" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTela(){
        fotoPessoaAlterar = findViewById(R.id.fotoPessoaEditar);
        botaoAlterarFotoPessoa = findViewById(R.id.botaoEditarFotoPessoa);
        nomePessoaEditar = findViewById(R.id.nomeId);
        telefonePessoaEditar = findViewById(R.id.telefoneId);
        botaoAlterarDadosPessoa = findViewById(R.id.atualizarId);
        necessidadesPessoaEditar = findViewById(R.id.necessidadesId);
        setPessoa();
    }

    private void setPessoa(){
        Pessoa pessoa = Sessao.getPessoa();
        nomePessoaEditar.setText(pessoa.getNome());
        telefonePessoaEditar.setText(pessoa.getTelefone());
        necessidadesPessoaEditar.setText(pessoa.getNecessidades());
    }

    private void setAlterar(){
        Pessoa pessoa = Sessao.getPessoa();
        String nome = nomePessoaEditar.getText().toString().trim();
        String telefone = telefonePessoaEditar.getText().toString().trim();
        String necessidades = necessidadesPessoaEditar.getText().toString().trim();
        pessoa.setNome(nome);
        pessoa.setTelefone(telefone);
        pessoa.setNecessidades(necessidades);
        pessoa.setFoto(urlFoto);
        Sessao.setPessoa(0, pessoa);
        Sessao.getDatabasePessoa().child(Sessao.getUserId()).setValue(Sessao.getPessoa());
        finish();
    }

    private boolean vericarCampos() {
        String nome = nomePessoaEditar.getText().toString().trim();
        String telefone = telefonePessoaEditar.getText().toString().trim();
        String necessidades = necessidadesPessoaEditar.getText().toString().trim();
        if (servicoValidacao.verificarCampoVazio(nome)) {
            nomePessoaEditar.setError("Campo vazio");
            return false;
        }else if (fotoPessoaAlterar.getDrawable() == null){
            Toast.makeText(EditarPerfilPessoaActivity.this, "Adicione uma foto", Toast.LENGTH_LONG).show();
            return false;
        } else if (servicoValidacao.verificarCampoVazio(telefone)) {
            telefonePessoaEditar.setError("Campo vazio");
            return false;
        } else if (servicoValidacao.verificaTamanhoTelefone(telefone)) {
            telefonePessoaEditar.setError("Informa um número válido");
            return false;
        } else if (servicoValidacao.verificarCampoVazio(necessidades)) {
            necessidadesPessoaEditar.setError("Campo vazio");
            return false;
        } else {
            return true;
        }
    }
}
