package bsi.ufrpe.br.cared.cuidador.gui;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import bsi.ufrpe.br.cared.R;

public class CalendarioPerfilCuidadorActivity extends AppCompatActivity {
    private FragmentManager fm;
    private BottomNavigationView navigationView;
    private MeusHorariosAgendadosFragment mhaf = new MeusHorariosAgendadosFragment();
    private MeusHorariosDisponiveisFragment mhdf = new MeusHorariosDisponiveisFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario_perfil_cuidador);
        fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frameLayout, mhaf);
        ft.commit();
        navigationView = findViewById(R.id.navigationView);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();

                if(id == R.id.agendadosBottomNav){
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frameLayout, mhaf);
                    ft.commit();
                }else if(id == R.id.disponivelBottomNav){
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.frameLayout, mhdf);
                    ft.commit();
                }
                return true;
            }
        });
    }
}
