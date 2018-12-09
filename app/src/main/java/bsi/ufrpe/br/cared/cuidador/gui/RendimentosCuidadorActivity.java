package bsi.ufrpe.br.cared.cuidador.gui;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import bsi.ufrpe.br.cared.R;

public class RendimentosCuidadorActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rendimentos_cuidador);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPagerid);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        //adapter.AddFragment(new RendimentosGeral(),"GERAL");
        adapter.AddFragment(new RendimentosMesAtual(),"MÊS ATUAL");
        adapter.AddFragment(new RendimentosFuturo(),"FUTUROS");


        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);

    }
}