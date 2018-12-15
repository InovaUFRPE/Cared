package bsi.ufrpe.br.cared.cuidador.gui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.math.BigDecimal;
import java.util.List;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.infra.MyApplication;

public class CuidadorAdapter extends ArrayAdapter<Cuidador> {
    private List<Cuidador> elementos;
    private BigDecimal tempo;
    public CuidadorAdapter(@NonNull List<Cuidador> elementos, @NonNull double tempo) {
        super(MyApplication.getContext(), R.layout.adapter_cuidador, elementos);
        this.elementos = elementos;
        this.tempo = new BigDecimal(tempo);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) MyApplication.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_cuidador, parent, false);
        ImageView foto = rowView.findViewById(R.id.fotoCuidadorLinha);
        TextView nomePessoa = rowView.findViewById(R.id.nomeCuidadorAdapter);
        TextView cidade = rowView.findViewById(R.id.cidadeCuidadorAdapter);
        TextView descricao = rowView.findViewById(R.id.descricaoCuidadorAdapter);
        TextView valor = rowView.findViewById(R.id.valor);
        Picasso.get()
                .load(elementos.get(position).getPessoa().getFoto())
                .resize(300, 300)
                .centerCrop()
                .into(foto);
        nomePessoa.setText(elementos.get(position).getPessoa().getNome());
        cidade.setText(elementos.get(position).getPessoa().getEndereco().getCidade());
        descricao.setText(elementos.get(position).getServico());
        valor.setText("R$" + getPreco(elementos.get(position)));
        return rowView;
    }

    private double getPreco(Cuidador cuidador){
        BigDecimal pHora = new BigDecimal(cuidador.getValor());
        BigDecimal valor = pHora.multiply(tempo);
        return valor.doubleValue();
    }
}
