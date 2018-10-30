package bsi.ufrpe.br.cared.cuidador.gui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        TextView cidade = getView().findViewById(R.id.cidadeCuidadorPerfilId);
        TextView telefone = getView().findViewById(R.id.telefoneCuidadorPerfilId);
        TextView descicao = getView().findViewById(R.id.descricaoCuidadorPerfilId);
        nome.setText(Sessao.getCuidador().getPessoa().getNome());
        cidade.setText(Sessao.getCuidador().getEndereco().getCidade());
        telefone.setText(Sessao.getCuidador().getPessoa().getTelefone());
        descicao.setText(Sessao.getCuidador().getServico());
    }
}
