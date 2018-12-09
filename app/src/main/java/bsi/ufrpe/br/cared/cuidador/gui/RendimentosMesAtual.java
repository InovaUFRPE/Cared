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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.horario.dominio.Agendamento;
import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.infra.Sessao;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;

public class RendimentosMesAtual extends Fragment {

    View v;
    private RecyclerView recyclerViewMesAtual;
    private List<Agendamento> listMesAtual = new ArrayList<>();
    private List<Pessoa> pessoaArrayListMesAtual = new ArrayList<>();
    RendimentosAdapter rendimentosAdapterMesAtual;
    Date dataAtual = new Date();

    public RendimentosMesAtual() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_rendimentos_mesatual,container,false);
        recyclerViewMesAtual = (RecyclerView)v.findViewById(R.id.recycler_mes_atual);
        rendimentosAdapterMesAtual = new RendimentosAdapter(getContext(), listMesAtual, pessoaArrayListMesAtual);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewMesAtual.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerViewMesAtual.getContext(),layoutManager.getOrientation());
        recyclerViewMesAtual.addItemDecoration(dividerItemDecoration);
        return v;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //listFuturo = new ArrayList<Agendamento>();
        //pessoaArrayListFuturo = new ArrayList<Pessoa>();

        Sessao.getDatabaseAgendamento().child(Sessao.getUserId()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren()){
                    Agendamento agendamento = dataSnapshot1.getValue(Agendamento.class);
                    Pessoa pessoa = dataSnapshot.getValue(Pessoa.class);
                    Horario horario = dataSnapshot1.getValue(Horario.class);

                    SimpleDateFormat mesAnoFormatacao = new SimpleDateFormat("MM/yyyy");
                    Date mesAnoAtual = new Date();
                    Date mesAnoAgendamento = new Date(agendamento.getHorario().getInicio());
                    if(!(mesAnoAgendamento).after(dataAtual) && (String.valueOf(agendamento.getSituacao()).equals("ACEITO")) && (mesAnoFormatacao.format(mesAnoAgendamento)).equals((mesAnoFormatacao.format(mesAnoAtual)))){
                        listMesAtual.add(agendamento);
                        getPessoaAgendamento(agendamento.getPacienteId());
                    }
                }
                //rendimentosAdapter = new RendimentosAdapter(RendimentosMesAtual.this,list,pessoaArrayList);
                //recyclerView.setAdapter(rendimentosAdapter);
                recyclerViewMesAtual.setAdapter(rendimentosAdapterMesAtual);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Toast.makeText(RendimentosMesAtual.this,"Falha", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getPessoaAgendamento(String id){
        Sessao.getDatabasePessoa().child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Pessoa pessoa = dataSnapshot.getValue(Pessoa.class);
                pessoaArrayListMesAtual.add(pessoa);
                rendimentosAdapterMesAtual.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}