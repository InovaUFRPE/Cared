package bsi.ufrpe.br.cared.cuidador.gui;

import android.app.ProgressDialog;
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

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.avaliacao.dominio.AvaliacaoForm;
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.horario.dominio.Agendamento;
import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.horario.dominio.Situacao;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.infra.servico.ConflitoHorarios;

public class PerfilCuidadorActivity extends AppCompatActivity {
    private ImageView foto;
    private TextView nome, servicos, nota, valor;
    private Button botaoContratar, botaoComentarios;
    private Cuidador cuidador;
    private Horario horario;
    private LinearLayout linearLayout;
    private ProgressDialog progressDialog;
    private AvaliacaoForm avaliacaoForm;

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
        servicos = findViewById(R.id.descricao);
        valor = findViewById(R.id.valor);
        botaoContratar = findViewById(R.id.btContratarId);
        botaoContratar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                contratar();
            }
        });
        getExtras();
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
        servicos.setText(cuidador.getServico());
        valor.setText("R$" + getPreco());
        double notinha = mediaNota();
        nota.setText(String.valueOf(notinha));
        linearLayout.setVisibility(View.VISIBLE);
        progressDialog.dismiss();
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
        finish();
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

//    public double mediaNota(){
//        double media;
//        int notas;
//        media = 0;
//        notas = 0;
//        for (AvaliacaoForm avaliacaoForm: avaliacaoForm){
//            media = media + avaliacaoForm.getNota();
//            notas +=1;
//        }
//        return media/notas;
//    }
}
