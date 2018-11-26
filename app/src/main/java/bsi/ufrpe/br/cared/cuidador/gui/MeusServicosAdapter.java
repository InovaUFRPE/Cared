package bsi.ufrpe.br.cared.cuidador.gui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.horario.dominio.Agendamento;
import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.infra.MyApplication;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;

public class MeusServicosAdapter extends ArrayAdapter<Agendamento> {
    private List<Agendamento> elementos;
    private List<Pessoa> pessoas;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

    public MeusServicosAdapter(@NonNull List<Agendamento> elementos, @NonNull List<Pessoa> pessoas) {
        super(MyApplication.getContext(), R.layout.linha_servico_cuidador, elementos);
        this.elementos = elementos;
        this.pessoas = pessoas;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) MyApplication.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.linha_servico_cuidador, parent, false);
        TextView nome = rowView.findViewById(R.id.nomeId);
        TextView horario = rowView.findViewById(R.id.horarioId);
        TextView situacao = rowView.findViewById(R.id.situacaoId);

        nome.setText(escolherPessoa(elementos.get(position).getPacienteId()));
        horario.setText(textoHorario(elementos.get(position).getHorario()));
        situacao.setText(elementos.get(position).getSituacao().getName());
        return rowView;
    }

    private String escolherPessoa(String id){
        for (Pessoa pessoa: pessoas){
            if (pessoa.getUserId().equals(id)){
                return pessoa.getNome();
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
