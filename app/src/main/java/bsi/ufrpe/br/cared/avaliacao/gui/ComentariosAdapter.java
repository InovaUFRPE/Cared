package bsi.ufrpe.br.cared.avaliacao.gui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.avaliacao.dominio.AvaliacaoForm;
import bsi.ufrpe.br.cared.avaliacao.gui.FormularioActivity;
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.horario.dominio.Agendamento;
import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.infra.MyApplication;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;

public class ComentariosAdapter extends ArrayAdapter<AvaliacaoForm> {
    private List<AvaliacaoForm> avaliacaoForms;
    private List<Pessoa> pessoa;

    public ComentariosAdapter(@NonNull List<AvaliacaoForm> agendamentos, @NonNull List<Pessoa> pessoa) {
        super(MyApplication.getContext(), R.layout.adapter_comentarios, agendamentos);
        this.avaliacaoForms = avaliacaoForms;
        this.pessoa = pessoa;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) MyApplication.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_comentarios, parent, false);
        TextView nome = rowView.findViewById(R.id.nomePessoaId);
        TextView nota = rowView.findViewById(R.id.notaDadaId);
        ImageView foto = rowView.findViewById(R.id.fotoCuidadorLinha);
        TextView comentario = rowView.findViewById(R.id.comentarioId);
//        Picasso.get()
//                .load(pessoa.get(position).getFoto())
//                .resize(300, 300)
//                .centerCrop()
//                .into(foto);
//        falta setar as coisas
    return rowView;
    }
}