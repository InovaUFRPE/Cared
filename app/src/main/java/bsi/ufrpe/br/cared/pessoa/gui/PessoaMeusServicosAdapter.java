package bsi.ufrpe.br.cared.pessoa.gui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.horario.dominio.Agendamento;
import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.infra.MyApplication;

public class PessoaMeusServicosAdapter extends ArrayAdapter<Agendamento> {
    private List<Agendamento> agendamentos;
    private List<Cuidador> cuidadores;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public PessoaMeusServicosAdapter(@NonNull List<Agendamento> agendamentos, @NonNull List<Cuidador> cuidadores) {
        super(MyApplication.getContext(), R.layout.adapter_meus_servicos, agendamentos);
        this.agendamentos = agendamentos;
        this.cuidadores = cuidadores;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) MyApplication.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.adapter_meus_servicos, parent, false);
        TextView nome = rowView.findViewById(R.id.nomeId);
        TextView horario = rowView.findViewById(R.id.horarioId);
        TextView situacao = rowView.findViewById(R.id.situacaoId);

        nome.setText(escolherCuidador(agendamentos.get(position).getCuidadorId()));
        horario.setText(textoHorario(agendamentos.get(position).getHorario()));
        situacao.setText(agendamentos.get(position).getSituacao().getName());
        return rowView;
    }

    private String escolherCuidador(String id){
        for (Cuidador cuidador: cuidadores){
            if (cuidador.getUserId().equals(id)){
                return cuidador.getPessoa().getNome();
            }
        }
        return "";
    }

    private String textoHorario(Horario horario){
        Date data1 = new Date(horario.getInicio());
        Date data2 = new Date(horario.getFim());
        return sdf.format(data1) + " - " + sdf.format(data2);
    }
}
