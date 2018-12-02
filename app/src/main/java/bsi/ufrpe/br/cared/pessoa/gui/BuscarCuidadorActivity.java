package bsi.ufrpe.br.cared.pessoa.gui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.infra.Sessao;

public class BuscarCuidadorActivity extends AppCompatActivity implements EscolherHorarioFragment.PassarHorario {
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_cuidador);
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frameLayout, new EscolherHorarioFragment());
        ft.commit();
    }

    @Override
    public void passarHora(Horario horario) {
        ListaCuidadorFragment fragment = new ListaCuidadorFragment();
        fragment.setHorario(horario);
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frameLayout, fragment);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        if (fm.findFragmentById(R.id.frameLayout) instanceof ListaCuidadorFragment){
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frameLayout, new EscolherHorarioFragment());
            ft.commit();
        } else {
            super.onBackPressed();
        }
    }
}
