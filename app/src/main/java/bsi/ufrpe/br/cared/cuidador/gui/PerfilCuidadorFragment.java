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
import android.widget.TextView;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.infra.Sessao;

public class PerfilCuidadorFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_perfil_cuidador, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView nome = getView().findViewById(R.id.nomeCuidadorPerfilId);
        TextView cidade = getView().findViewById(R.id.enderecoCuidadoraId);
        TextView descicao = getView().findViewById(R.id.descricaoId);
        nome.setText(Sessao.getCuidador().getPessoa().getNome());
        cidade.setText(Sessao.getCuidador().getEndereco().getCidade());
        descicao.setText(Sessao.getCuidador().getServico());
        Button btCalendario = getView().findViewById(R.id.btCalendarioId);
        btCalendario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), CuidadorCalendarActivity.class));
            }
        });
    }
}
