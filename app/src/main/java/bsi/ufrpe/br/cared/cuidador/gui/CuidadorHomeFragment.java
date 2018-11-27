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

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.infra.Sessao;

public class CuidadorHomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cuidador_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button meuCalendario = getView().findViewById(R.id.btMeuCalendario);
        meuCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CalendarioPerfilCuidadorActivity.class));
            }
        });
//        Button meuPerfil = getView().findViewById(R.id.btMeuPerfilid);
//        meuPerfil.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), PerfilCuidadorFragment.class));
//            }
//        });  ta dando bug pq não consigo passar o fragment
//        Button meusRendimentos = getView().findViewById(R.id.btMeusRendimentos);
//        meusRendimentos.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getActivity(), xxx.class));
//            }
//        });

    }
}
