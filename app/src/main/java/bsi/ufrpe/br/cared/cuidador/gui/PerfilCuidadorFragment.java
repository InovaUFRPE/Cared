package bsi.ufrpe.br.cared.cuidador.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.avaliacao.dominio.AvaliacaoForm;
import bsi.ufrpe.br.cared.avaliacao.gui.ComentariosActivity;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;

public class PerfilCuidadorFragment extends Fragment {
    private AvaliacaoForm avaliacaoForm;
    private List<AvaliacaoForm> avaliacaoFormList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil_cuidador, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView foto = getView().findViewById(R.id.fotoPerfilCuidadorActivity);
        TextView nome = getView().findViewById(R.id.nomeCuidadorPerfilId);
        TextView cidade = getView().findViewById(R.id.enderecoCuidadoraId);
        TextView bairro = getView().findViewById(R.id.bairroCuidadorId);
        TextView descricao = getView().findViewById(R.id.descricao);
        TextView dormir = getView().findViewById(R.id.disponibilidadeDormir);
        TextView curso = getView().findViewById(R.id.cursoSimNao);
        TextView listaCurso = getView().findViewById(R.id.listaCursos);
        TextView experiencia = getView().findViewById(R.id.possuiExperiencia);
        TextView listaExperiencia =getView().findViewById(R.id.listaExperiencia);
        TextView nota = getView().findViewById(R.id.notaCuidador);
        Picasso.get()
                .load(Sessao.getCuidador().getPessoa().getFoto())
                .resize(300, 300)
                .centerCrop()
                .into(foto);
        nome.setText(Sessao.getCuidador().getPessoa().getNome());
        cidade.setText(Sessao.getCuidador().getPessoa().getEndereco().getCidade());
        bairro.setText(Sessao.getCuidador().getPessoa().getEndereco().getBairro());
        descricao.setText(Sessao.getCuidador().getServico());
//        double notinha = mediaNota();
//        nota.setText(String.valueOf(notinha));
        if((String.valueOf(Sessao.getCuidador().getDisponivelDormir())) == "SIM"){
            dormir.setText("Sim");
        } else {
            dormir.setText("Não");
        }
        if ((String.valueOf(Sessao.getCuidador().getPossuiCurso())) == "SIM") {
            curso.setText("Sim");
            listaCurso.setText(Sessao.getCuidador().getCurso());
        } else {
            curso.setText("Não");
            listaCurso.setText("Não possui cursos");
        }
        if ((String.valueOf(Sessao.getCuidador().getExperiencia())) == "SIM"){
            experiencia.setText("Sim");
            listaExperiencia.setText((Sessao.getCuidador().getResumoExperiencia()));
        } else{
            experiencia.setText("Não");
            listaExperiencia.setText("Não possui experiência");
        }

        Button btCalendario = getView().findViewById(R.id.btContratarId);
        btCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CalendarioPerfilCuidadorActivity.class));
            }
        });
        Button btEditar = getView().findViewById(R.id.btEditarPerfilid);
        btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditarPerfilCuidadorActivity.class));
            }
        });
        Button btComentarios = getView().findViewById(R.id.btComentariosid);
        btComentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ComentariosActivity.class));
            }
        });
    }


    public double mediaNota(){
        getAvaliacoes();
        double notas;
        int quantidade;
        notas = 0;
        quantidade = 0;
        for (AvaliacaoForm avaliacaoForm: avaliacaoFormList);{
            notas = notas + avaliacaoForm.getNota();
            quantidade +=1;
        }
        return notas/quantidade;
    }

    private void getAvaliacoes(){
        Sessao.getDatabaseAvaliacao().child(Sessao.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    AvaliacaoForm avaliacaoForm = dataSnapshot1.getValue(AvaliacaoForm.class);
                    avaliacaoFormList.add(avaliacaoForm);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


}
