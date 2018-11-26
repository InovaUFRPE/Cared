package bsi.ufrpe.br.cared.pessoa.gui;

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
import bsi.ufrpe.br.cared.usuario.gui.ComentariosActivity;

public class PerfilPessoaFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pessoa_perfil, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ImageView fotoIdoso = getView().findViewById(R.id.fotoPerfilIdoso);
        TextView nomeIdoso = getView().findViewById(R.id.nomeiIdosoPerfil);
        TextView notaIdoso = getView().findViewById(R.id.notaIdoso);
        TextView necessidades = getView().findViewById(R.id.necessidadesUser);
        Button botaoComentarios = getView().findViewById(R.id.btComentariosid);
        Button btEditar = getView().findViewById(R.id.btEditarPerfilPessoa);

        Picasso.get()
                .load(Sessao.getPessoa().getFoto())
                .resize(300, 300)
                .centerCrop()
                .into(fotoIdoso);
        nomeIdoso.setText(Sessao.getPessoa().getNome());
        necessidades.setText(Sessao.getPessoa().getNecessidades());
//        notaIdoso;

        botaoComentarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ComentariosActivity.class));
            }
        });
        btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), EditarPerfilPessoaActivity.class));
            }
        });
    }
}
