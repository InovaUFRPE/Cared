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

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.usuario.gui.HomeActivity;

public class PessoaHomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pessoa_home, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button btBuscarBuidador = getView().findViewById(R.id.btVisualizarCuidadoresId);
        btBuscarBuidador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), BuscarCuidadorActivity.class));
            }
        });
        Button mp = getView().findViewById(R.id.btMeuPerfil);
        mp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeActivity)getActivity()).irParaPerfil();
            }
        });
    }
}
