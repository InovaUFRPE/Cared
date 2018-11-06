package bsi.ufrpe.br.cared.pessoa.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.cuidador.gui.CuidadorAdapter;
import bsi.ufrpe.br.cared.cuidador.gui.PerfilCuidadorActivity;
import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.horario.dominio.HorarioDisponivel;
import bsi.ufrpe.br.cared.infra.Sessao;

public class ListaCuidadorFragment extends Fragment {
    private List<Cuidador> cuidadorList = new ArrayList<>();
    private ListView listView;
    private CuidadorAdapter adapter;
    private Horario horario;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lista_cuidador, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        horario = Sessao.getHorario();
        listView = getView().findViewById(R.id.listView);
        adapter = new CuidadorAdapter(cuidadorList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Sessao.setCuidadorPerfil(cuidadorList.get(i));
                startActivity(new Intent(getActivity(), PerfilCuidadorActivity.class));
            }
        });
        Sessao.getDatabaseHorarioDisponivel().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    for (DataSnapshot dataSnapshot2: dataSnapshot1.getChildren()){
                        HorarioDisponivel disponivel = dataSnapshot2.getValue(HorarioDisponivel.class);
                        if (horario.getInicio() >= disponivel.getHorario().getInicio() && horario.getInicio() <= disponivel.getHorario().getFim()
                                && horario.getFim() >= disponivel.getHorario().getInicio() && horario.getFim() <= disponivel.getHorario().getFim()){
                            getCuidador(disponivel.getUserId());
                        }
                    }
                }
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
                cuidadorList.add(cuidador);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
