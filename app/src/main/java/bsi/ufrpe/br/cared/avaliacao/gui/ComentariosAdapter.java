package bsi.ufrpe.br.cared.avaliacao.gui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.avaliacao.dominio.AvaliacaoForm;
import bsi.ufrpe.br.cared.infra.MyApplication;
import bsi.ufrpe.br.cared.infra.Sessao;
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

        AvaliacaoForm avaliacaoForm =  avaliacaoForms.get(position);
        TextView nome = rowView.findViewById(R.id.nomePessoaId);
        TextView nota = rowView.findViewById(R.id.notaDadaId);
        ImageView foto = rowView.findViewById(R.id.fotoCuidadorLinha);
        TextView comentario = rowView.findViewById(R.id.comentarioId);

        comentario.setText(avaliacaoForm.getComentario());
        String aux = String.valueOf(avaliacaoForm.getNota());
        nota.setText(aux);
        //falta setar o nome da pessoa



        Picasso.get()
                .load(pessoa.get(position).getFoto())
                .resize(300, 300)
                .centerCrop()
                .into(foto);

    return rowView;
    }


}