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
    private List<Pessoa> pessoas;

    public ComentariosAdapter(@NonNull List<AvaliacaoForm> avaliacaoForms, @NonNull List<Pessoa> pessoas) {
        super(MyApplication.getContext(), R.layout.adapter_comentarios, avaliacaoForms);
        this.avaliacaoForms = avaliacaoForms;
        this.pessoas = pessoas;
    }
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) MyApplication.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_comentarios, parent, false);

        AvaliacaoForm avaliacaoForm =  avaliacaoForms.get(position);
        Pessoa pessoa = escolherPessoa(avaliacaoForm.getIdAvaliador());
        if (pessoa != null) {
            TextView nome = rowView.findViewById(R.id.nomePessoaId);
            TextView nota = rowView.findViewById(R.id.notaDadaId);
            ImageView foto = rowView.findViewById(R.id.fotoCuidadorLinha);
            TextView comentario = rowView.findViewById(R.id.comentarioId);

            comentario.setText(avaliacaoForm.getComentario());
            String aux = String.valueOf(avaliacaoForm.getNota());
            nota.setText(aux);
            nome.setText(pessoa.getNome());



            Picasso.get()
                    .load(pessoa.getFoto())
                    .resize(300, 300)
                    .centerCrop()
                    .into(foto);
        }

    return rowView;
    }

    private Pessoa escolherPessoa(String id){
        for (Pessoa pessoa: pessoas){
            if (pessoa.getUserId().equals(id)){
                return pessoa;
            }
        }
        return null;
    }


}