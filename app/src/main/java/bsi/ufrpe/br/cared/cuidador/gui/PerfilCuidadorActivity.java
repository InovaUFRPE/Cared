package bsi.ufrpe.br.cared.cuidador.gui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.avaliacao.dominio.AvaliacaoForm;
import bsi.ufrpe.br.cared.avaliacao.gui.ComentariosActivity;
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.horario.dominio.Agendamento;
import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.horario.dominio.Situacao;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.infra.servico.ConflitoHorarios;

public class PerfilCuidadorActivity extends AppCompatActivity {
    private ImageView foto;
    private TextView nome, descricao, endereco, bairro, nota, valor, disponivelDormir, dormirSimNao, possuiCurso, cursoSimNao, listaCurso, possuiExperiencia, experienciaSimNao, listaExperiencia;
    private Button botaoContratar, botaoComentarios;
    private Cuidador cuidador;
    private Horario horario;
    private LinearLayout linearLayout;
    private ProgressDialog progressDialog;
    private List<AvaliacaoForm> avaliacaoFormList = new ArrayList<>();
    double notas;
    int quantidade;

    public PerfilCuidadorActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_cuidador);
        setTela();
    }

    private void setTela(){
        linearLayout = findViewById(R.id.linearLayout2);
        linearLayout.setVisibility(View.GONE);
        foto = findViewById(R.id.fotoPerfilCuidadorActivity);
        nome = findViewById(R.id.nomeCuidadorPerfilId);
        descricao = findViewById(R.id.descricao);
        endereco = findViewById(R.id.enderecoCuidador);
        bairro = findViewById(R.id.bairroCuidador);
        valor = findViewById(R.id.valor);
        nota = findViewById(R.id.notaCuidador);
        disponivelDormir = findViewById(R.id.disponibilidadeDormir);
        dormirSimNao = findViewById(R.id.dormirSimouNao);
        possuiCurso = findViewById(R.id.possuiCurso);
        cursoSimNao = findViewById(R.id.cursoSimouNao);
        listaCurso = findViewById(R.id.listaCurso);
        possuiExperiencia = findViewById(R.id.possuiExperiencia);
        experienciaSimNao = findViewById(R.id.experienciaSimouNao);
        listaExperiencia = findViewById(R.id.listaExperiencia);
        botaoContratar = findViewById(R.id.btContratarId);
        botaoContratar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contratar();
            }
        });
        botaoComentarios = findViewById(R.id.btComentariosid);
        getExtras();
        botaoComentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comentarios();
            }
        });
    }
    private void getExtras(){
        Bundle extra = getIntent().getExtras();
        horario = new Horario();
        horario.setInicio(extra.getLong("inicio"));
        horario.setFim(extra.getLong("fim"));
        getCuidador(extra.getString("id"));
    }

    private void setCuidador(){
        Picasso.get()
                .load(cuidador.getPessoa().getFoto())
                .resize(300, 300)
                .centerCrop()
                .into(foto);
        nome.setText(cuidador.getPessoa().getNome());
        descricao.setText(cuidador.getServico());
        endereco.setText(cuidador.getPessoa().getEndereco().getCidade());
        bairro.setText(cuidador.getPessoa().getEndereco().getBairro());
        valor.setText("R$" + getPreco());
//        dormirSimNao.setText(cuidador.getDisponivelDormir());
//        possuiCurso.setText(cuidador.getPossuiCurso());
//        possuiExperiencia.setText(cuidador.getExperiencia());
        if((String.valueOf(cuidador.getDisponivelDormir())) == "SIM"){
            dormirSimNao.setText("Sim");
        } else {
            dormirSimNao.setText("Não");
        }
        if ((String.valueOf(cuidador.getPossuiCurso())) == "SIM") {
            cursoSimNao.setText("Sim");
            listaCurso.setText(cuidador.getCurso());
        } else {
            cursoSimNao.setText("Não");
            listaCurso.setText("Não possui cursos");
        }
        if ((String.valueOf(cuidador.getExperiencia())) == "SIM"){
            experienciaSimNao.setText("Sim");
            listaExperiencia.setText((cuidador.getResumoExperiencia()));
        } else{
            experienciaSimNao.setText("Não");
            listaExperiencia.setText("Não possui experiência");
        }
        getAvaliacoes();
        linearLayout.setVisibility(View.VISIBLE);
//        progressDialog.dismiss();
    }

    private void contratar(){
        Agendamento agendamento = new Agendamento();
        agendamento.setHorario(horario);
        agendamento.setCuidadorId(cuidador.getUserId());
        agendamento.setPacienteId(Sessao.getUserId());
        agendamento.setValor(getPreco());
        agendamento.setSituacao(Situacao.PENDENTE);
        ConflitoHorarios.newAgendamento(agendamento);
        Toast.makeText(this, "Cuidador contratado com sucesso", Toast.LENGTH_SHORT).show();
        //finish();
    }

    private void getCuidador(String id){
        Sessao.getDatabaseCuidador().child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cuidador = dataSnapshot.getValue(Cuidador.class);
                setCuidador();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private double getPreco(){
        BigDecimal pHora = new BigDecimal(cuidador.getValor());
        BigDecimal valor = pHora.multiply(new BigDecimal(ConflitoHorarios.getTempo(horario))).add(new BigDecimal(5));
        return valor.doubleValue();
    }

    public void mediaNota(){
        notas = 0;
        for (AvaliacaoForm avaliacaoForm: avaliacaoFormList){
            notas = notas + avaliacaoForm.getNota();
        }
        quantidade = avaliacaoFormList.size();
        notas = notas/quantidade;
        nota = findViewById(R.id.notaCuidador);
        nota.setText(String.valueOf(notas));
    }

    private void comentarios(){
        Intent intent = new Intent(this, ComentariosActivity.class);
        intent.putExtra("id", cuidador.getUserId());
        startActivity(intent);
    }

    private void getAvaliacoes(){
        Sessao.getDatabaseAvaliacao().child(cuidador.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    AvaliacaoForm avaliacaoForm = dataSnapshot1.getValue(AvaliacaoForm.class);
                    avaliacaoFormList.add(avaliacaoForm);
                }
                mediaNota();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
