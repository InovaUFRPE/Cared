package bsi.ufrpe.br.cared.cuidador.gui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.endereco.dominio.Endereco;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.infra.servico.ServicoValidacao;
import bsi.ufrpe.br.cared.infra.servico.ValidaCPF;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;
import bsi.ufrpe.br.cared.usuario.dominio.Usuario;
import bsi.ufrpe.br.cared.usuario.gui.MainActivity;

public class CadastroCuidadorActivity extends AppCompatActivity {
    private EditText campoNome, campoCPF, campoTelefone, campoEmail, campoSenha, campoRua, campoNumero, campoBairro, campoCidade, campoServico, campoValor;
    private ImageView imageView;
    private Button btConfirmar, selecionarFoto;
    private ServicoValidacao servicoValidacao = new ServicoValidacao();
    private ValidaCPF validaCPF = new ValidaCPF();
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_cuidador);
        setView();
        selecionarFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pegarImagem();
            }
        });
        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickConfirmar();
            }
        });
    }

    private void setView(){
        imageView = findViewById(R.id.fotoView);
        selecionarFoto = findViewById(R.id.escolherFoto);
        campoNome = findViewById(R.id.nomeId);
        campoCPF = findViewById(R.id.cpfId);
        campoTelefone = findViewById(R.id.telefoneId);
        campoEmail = findViewById(R.id.emailId);
        campoSenha = findViewById(R.id.senhaId);
        btConfirmar = findViewById(R.id.confirmarId);
        campoRua = findViewById(R.id.ruaId);
        campoNumero = findViewById(R.id.numeroId);
        campoBairro = findViewById(R.id.bairroId);
        campoCidade = findViewById(R.id.cidadeId);
        campoServico = findViewById(R.id.servicoId);
        campoValor = findViewById(R.id.precoId);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    private void pegarImagem() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent, "Selecione Foto"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null)
        {
            filePath = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void criarConta(){
        if (!vericarCampos()) {
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
                    Toast.makeText(CadastroCuidadorActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void criarUsuario(){
        Usuario usuario = new Usuario(1, Sessao.getUserId());
        Sessao.getDatabaseUser().child(Sessao.getUserId()).setValue(usuario);
        final Pessoa pessoa = new Pessoa();
        if (filePath != null){
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
                        String urlFoto = downUri.toString();
                        pessoa.setFoto(urlFoto);
                    }
                }
            });

        }
        String informacao = "Sem Informação";
        pessoa.setNome(campoNome.getText().toString().trim());
        pessoa.setCpf(campoCPF.getText().toString().trim());
        pessoa.setTelefone(campoTelefone.getText().toString().trim());
        pessoa.setUserId(Sessao.getUserId());
        Endereco endereco = new Endereco();
        endereco.setRua(campoRua.getText().toString().trim());
        endereco.setNumero(campoNumero.getText().toString().trim());
        endereco.setBairro(campoBairro.getText().toString().trim());
        endereco.setCidade(campoCidade.getText().toString().trim());
        pessoa.setEndereco(endereco);
        Cuidador cuidador = new Cuidador();
        cuidador.setPessoa(pessoa);
        cuidador.setValor(Long.parseLong(campoValor.getText().toString().trim()));
        cuidador.setServico(campoServico.getText().toString().trim());
        cuidador.setDisponivelDormir(informacao);
        cuidador.setPossuiCurso(informacao);
        cuidador.setCursosInformado(informacao);
        cuidador.setPossuiExperiencia(informacao);
        cuidador.setResumoExperiencia(informacao);
        cuidador.setUserId(Sessao.getUserId());
        Sessao.getDatabaseCuidador().child(Sessao.getUserId()).setValue(cuidador);
        Sessao.setPessoa(1, cuidador);
        startActivity(new Intent(this, ComplementoCadastroActivity.class));
        finish();
    }

    private void clickConfirmar(){
        criarConta();
    }

    private boolean vericarCampos(){
        String nome = campoNome.getText().toString().trim();
        String cpf = campoCPF.getText().toString().trim();
        String telefone = campoTelefone.getText().toString().trim();
        String email = campoEmail.getText().toString().trim();
        String senha = campoSenha.getText().toString().trim();
        String rua = campoRua.getText().toString().trim();
        String numero = campoNumero.getText().toString().trim();
        String bairro = campoBairro.getText().toString().trim();
        String cidade = campoCidade.getText().toString().trim();
        String servico = campoServico.getText().toString().trim();
        if (servicoValidacao.verificarCampoVazio(nome)) {
            campoNome.setError("Campo vazio");
            return false;
        } else if(servicoValidacao.verificarCampoVazio(cpf)) {
            campoCPF.setError("Campo vazio");
            return false;
        } else if(validaCPF.isCPF(cpf)){
            campoCPF.setError("CPF inválido");
            return false;
        } else if(servicoValidacao.verificarCampoVazio(telefone)) {
            campoTelefone.setError("Campo vazio");
            return false;
        } else if(servicoValidacao.verificaTamanhoTelefone(telefone)){
            campoTelefone.setError("Informa um número válido");
            return false;
        } else if(servicoValidacao.verificarCampoVazio(email)){
            campoEmail.setError("Campo vazio");
            return false;
        } else if(servicoValidacao.verificarCampoVazio(senha)){
            campoSenha.setError("Campo vazio");
            return false;
        } else if(servicoValidacao.verificaTamanhoSenha(senha)){
            campoSenha.setError("Inserir no mínimo 6 dígitos");
            return false;
        } else if(servicoValidacao.verificarCampoVazio(rua)){
            campoRua.setError("Campo vazio");
            return false;
        } else if(servicoValidacao.verificarCampoVazio(numero)){
            campoNumero.setError("Campo vazio");
            return false;
        } else if(servicoValidacao.verificarCampoVazio(bairro)){
            campoBairro.setError("Campo vazio");
            return false;
        } else if(servicoValidacao.verificarCampoVazio(cidade)){
            campoCidade.setError("Campo vazio");
            return false;
        } else if(servicoValidacao.verificarCampoVazio(servico)) {
            campoServico.setError("Campo vazio");
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
