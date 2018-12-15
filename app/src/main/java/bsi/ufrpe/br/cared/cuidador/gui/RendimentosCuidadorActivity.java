package bsi.ufrpe.br.cared.cuidador.gui;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

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
        adapter.AddFragment(new RendimentosMesAtual(),"MÊS ATUAL");
        adapter.AddFragment(new RendimentosFuturo(),"FUTUROS");
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(adapter);
    }
}