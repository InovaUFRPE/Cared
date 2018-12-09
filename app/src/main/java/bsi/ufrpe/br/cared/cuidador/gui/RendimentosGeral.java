package bsi.ufrpe.br.cared.cuidador.gui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.horario.dominio.Agendamento;
import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;

public class RendimentosGeral extends Fragment {

    View v;
    private RecyclerView recyclerViewGeral;
    private Agendamento agendamento;
    private Pessoa pessoa;
    private ArrayList<Agendamento> listGeral;
    private ArrayList<Pessoa> pessoaArrayListGeral;
    RendimentosAdapter rendimentosAdapterGeral;
    Date dataAtualGeral = new Date();

    public RendimentosGeral() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_rendimentos_geral,container,false);
        recyclerViewGeral = (RecyclerView)v.findViewById(R.id.recycler_rendimentos_geral);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewGeral.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewGeral.getContext(),layoutManager.getOrientation());
        recyclerViewGeral.addItemDecoration(dividerItemDecoration);
        return v;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        listGeral = new ArrayList<Agendamento>();
        pessoaArrayListGeral = new ArrayList<Pessoa>();

        Sessao.getDatabaseAgendamento().child(Sessao.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Agendamento agendamento = dataSnapshot1.getValue(Agendamento.class);
                    Horario horario = dataSnapshot1.getValue(Horario.class);

                    SimpleDateFormat mesAnoFormatacao = new SimpleDateFormat("MM/yyyy");
                    Date mesAnoAtual = new Date();
                    Date mesAnoAgendamento = new Date(agendamento.getHorario().getInicio());
                    if((String.valueOf(agendamento.getSituacao()).equals("ACEITO")) && (mesAnoAgendamento).before(dataAtualGeral)){
                        listGeral.add(agendamento);
                    }
                }
                //rendimentosAdapter = new RendimentosAdapter(RendimentosMesAtual.this,list,pessoaArrayList);
                //recyclerView.setAdapter(rendimentosAdapter);
                rendimentosAdapterGeral = new RendimentosAdapter(getContext(),listGeral,pessoaArrayListGeral);
                recyclerViewGeral.setAdapter(rendimentosAdapterGeral);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.makeText(RendimentosMesAtual.this,"Falha", Toast.LENGTH_SHORT).show();
            }
        });
    }
}