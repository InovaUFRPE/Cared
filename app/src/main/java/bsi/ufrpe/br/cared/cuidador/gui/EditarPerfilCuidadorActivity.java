package bsi.ufrpe.br.cared.cuidador.gui;

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
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.endereco.dominio.Endereco;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.infra.TipoUsuario;
import bsi.ufrpe.br.cared.infra.servico.ServicoValidacao;

public class EditarPerfilCuidadorActivity extends AppCompatActivity {
    private EditText nomeEditar, telefoneEditar, ruaEditar, numeroEditar, cidadeEditar, bairroEditar, valorEditar, descricaoEditar;
    private ImageView fotoCuidadorEditar;
    private ServicoValidacao servicoValidacao = new ServicoValidacao();
    private Button alterarDados, fotoAlterarCuidador;
    private String urlFoto;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil_cuidador);
        setTela();
        fotoAlterarCuidador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pegarImagem();
            }
        });
        alterarDados.setOnClickListener(new View.OnClickListener() {
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
                fotoCuidadorEditar.setImageBitmap(bitmap);
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
                    Toast.makeText(EditarPerfilCuidadorActivity.this, "Carregado", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(EditarPerfilCuidadorActivity.this, "Falhou" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setTela(){
        fotoCuidadorEditar = findViewById(R.id.fotoViewCuidadorEditar);
        fotoAlterarCuidador = findViewById(R.id.escolherFotoCuidadorEditar);
        nomeEditar = findViewById(R.id.nomeId);
        telefoneEditar = findViewById(R.id.telefoneId);
        alterarDados = findViewById(R.id.confirmarId);
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
        ruaEditar.setText(cuidador.getPessoa().getEndereco().getRua());
        numeroEditar.setText(cuidador.getPessoa().getEndereco().getNumero());
        bairroEditar.setText(cuidador.getPessoa().getEndereco().getBairro());
        cidadeEditar.setText(cuidador.getPessoa().getEndereco().getCidade());
        valorEditar.setText(String.valueOf(cuidador.getValor()));
        descricaoEditar.setText(cuidador.getServico());
    }

    private void setAlterar(){
        Cuidador cuidador = Sessao.getCuidador();
        String nome = nomeEditar.getText().toString().trim();
        String telefone = telefoneEditar.getText().toString().trim();
        String rua = ruaEditar.getText().toString().trim();
        String numero = numeroEditar.getText().toString().trim();
        String bairro = bairroEditar.getText().toString().trim();
        String cidade = cidadeEditar.getText().toString().trim();
        String valor = valorEditar.getText().toString().trim();
        String descricao = descricaoEditar.getText().toString().trim();
        Endereco endereco = cuidador.getPessoa().getEndereco();
        cuidador.setServico(descricao);
        cuidador.setValor(Double.parseDouble(valor));
        cuidador.getPessoa().setNome(nome);
        cuidador.getPessoa().setTelefone(telefone);
        cuidador.getPessoa().setFoto(urlFoto);
        endereco.setBairro(bairro);
        endereco.setCidade(cidade);
        endereco.setNumero(numero);
        endereco.setRua(rua);
        cuidador.getPessoa().setEndereco(endereco);
        Sessao.setPessoa(TipoUsuario.CUIDADOR, cuidador);
        Sessao.getDatabaseCuidador().child(Sessao.getUserId()).setValue(Sessao.getCuidador());
        finish();
    }

    private boolean vericarCampos() {
        String nome = nomeEditar.getText().toString().trim();
        String telefone = telefoneEditar.getText().toString().trim();
        String rua = ruaEditar.getText().toString().trim();
        String numero = numeroEditar.getText().toString().trim();
        String bairro = bairroEditar.getText().toString().trim();
        String cidade = cidadeEditar.getText().toString().trim();
        String valor = valorEditar.getText().toString().trim();
        String descricao = descricaoEditar.getText().toString().trim();
        if (servicoValidacao.verificarCampoVazio(nome)) {
            nomeEditar.setError("Campo vazio");
            return false;
        }else if (fotoCuidadorEditar.getDrawable() == null){
            Toast.makeText(EditarPerfilCuidadorActivity.this, "Adicione uma foto", Toast.LENGTH_LONG).show();
            return false;
        } else if (servicoValidacao.verificarCampoVazio(telefone)) {
            telefoneEditar.setError("Campo vazio");
            return false;
        } else if (servicoValidacao.verificaTamanhoTelefone(telefone)) {
            telefoneEditar.setError("Informa um número válido");
            return false;
        } else if (servicoValidacao.verificarCampoVazio(rua)) {
            ruaEditar.setError("Campo vazio");
            return false;
        } else if (servicoValidacao.verificarCampoVazio(numero)) {
            numeroEditar.setError("Campo vazio");
            return false;
        } else if (servicoValidacao.verificarCampoVazio(bairro)) {
            bairroEditar.setError("Campo vazio");
            return false;
        } else if (servicoValidacao.verificarCampoVazio(cidade)) {
            cidadeEditar.setError("Campo vazio");
            return false;
        } else if (servicoValidacao.verificarCampoVazio(valor)) {
            valorEditar.setError("Campo vazio");
            return false;
        } else if (servicoValidacao.verificarCampoVazio(descricao)) {
            descricaoEditar.setError("Campo vazio");
            return false;
        } else {
            return true;
        }
    }

}
