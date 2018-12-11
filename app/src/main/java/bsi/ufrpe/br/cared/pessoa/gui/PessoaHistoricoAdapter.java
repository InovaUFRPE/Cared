package bsi.ufrpe.br.cared.pessoa.gui;

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
import bsi.ufrpe.br.cared.avaliacao.gui.FormularioActivity;
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.horario.dominio.Agendamento;
import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.infra.MyApplication;

public class PessoaHistoricoAdapter extends ArrayAdapter<Agendamento> {
    private List<Agendamento> agendamentos;
    private List<Cuidador> cuidadores;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public PessoaHistoricoAdapter(@NonNull List<Agendamento> agendamentos, @NonNull List<Cuidador> cuidadores) {
        super(MyApplication.getContext(), R.layout.adapter_historico, agendamentos);
        this.agendamentos = agendamentos;
        this.cuidadores = cuidadores;
    }
    public View getView(int position, final View convertView, final ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) MyApplication.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_historico, parent, false);
        final Agendamento agendamento = agendamentos.get(position);
        Cuidador cuidador = escolherCuidador(agendamento.getCuidadorId());
        if (cuidador != null) {
            TextView nome = rowView.findViewById(R.id.nomeCuidadorId);
            TextView horarioInicio = rowView.findViewById(R.id.deId);
            TextView horarioFim = rowView.findViewById(R.id.ateId);
            TextView valor = rowView.findViewById(R.id.precoId);
            ImageView foto = rowView.findViewById(R.id.imageView2);
            Button avaliar = rowView.findViewById(R.id.avaliarId);
            Picasso.get()
                    .load(cuidador.getPessoa().getFoto())
                    .resize(300, 300)
                    .centerCrop()
                    .into(foto);

            nome.setText(cuidador.getPessoa().getNome());
            horarioInicio.setText(textoHorarioInicio(agendamento.getHorario()));
            horarioFim.setText(textoHorarioFim(agendamento.getHorario()));
            String aux = String.valueOf(cuidador.getValor());
            valor.setText(aux);
            avaliar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(parent.getContext(), FormularioActivity.class);
                    intent.putExtra("id", agendamento.getId());
                    intent.putExtra("idC", agendamento.getCuidadorId());
                    parent.getContext().startActivity(intent);
                }
            });
        }
        return rowView;
    }

    private Cuidador escolherCuidador(String id){
        for (Cuidador cuidador: cuidadores){
            if (cuidador.getUserId().equals(id)){
                return cuidador;
            }
        }
        return null;
    }

    private String textoHorarioInicio(Horario horario){
        Date data1 = new Date(horario.getInicio());
        return sdf.format(data1);
    }
    private String textoHorarioFim(Horario horario){
        Date data2 = new Date(horario.getFim());
        return sdf.format(data2);
    }


}
