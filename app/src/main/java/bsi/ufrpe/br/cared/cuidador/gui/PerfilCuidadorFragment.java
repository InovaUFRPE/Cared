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

import com.squareup.picasso.Picasso;

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
        ImageView foto = getView().findViewById(R.id.fotoPerfilCuidadorActivity);
        TextView nome = getView().findViewById(R.id.nomeCuidadorPerfilId);
        TextView cidade = getView().findViewById(R.id.enderecoCuidadoraId);
        TextView bairro = getView().findViewById(R.id.bairroCuidadorId);
        TextView descricao = getView().findViewById(R.id.descricao);
        Picasso.get()
                .load(Sessao.getCuidador().getPessoa().getFoto())
                .resize(300, 300)
                .centerCrop()
                .into(foto);
        nome.setText(Sessao.getCuidador().getPessoa().getNome());
        descricao.setText(Sessao.getCuidador().getServico());
        cidade.setText(Sessao.getCuidador().getPessoa().getEndereco().getCidade());
        bairro.setText(Sessao.getCuidador().getPessoa().getEndereco().getBairro());
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
    }
}
