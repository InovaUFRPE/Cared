package bsi.ufrpe.br.cared.cuidador.gui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import bsi.ufrpe.br.cared.R;
import bsi.ufrpe.br.cared.horario.dominio.Agendamento;
import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;

public class RendimentosAdapter extends RecyclerView.Adapter<RendimentosAdapter.MyViewHolder> {
    private List<Agendamento> agendamentos;
    Context context;
    private List<Pessoa> pessoas;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public RendimentosAdapter(Context c, List<Agendamento> a, List<Pessoa> pessoa){
        context = c;
        this.agendamentos = a;
        this.pessoas = pessoa;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_rendimentos,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
        myViewHolder.nomeIdoso.setText(nomePessoa(agendamentos.get(position).getPacienteId()));
        myViewHolder.dataTrabalho.setText(textoData(agendamentos.get(position).getHorario()));
        myViewHolder.valorRendimento.setText(String.valueOf(agendamentos.get(position).getValor())+"0");
    }

    private String nomePessoa(String id){
        for (Pessoa pessoa : pessoas){
            if (pessoa.getUserId().equals(id)){
                return pessoa.getNome();
            }
        }
        return "";
    }

    public double valorTotalRendimentos(){
        double valorTotal;
        valorTotal = 0;
        for (Agendamento agendamento: agendamentos){
            valorTotal = valorTotal + agendamento.getValor();
        }
        return valorTotal;
    }

    private String textoData(Horario horario){
        Date data1 = new Date(horario.getInicio());
        return sdf.format(data1);
    }

    @Override
    public int getItemCount() {
        return agendamentos.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView nomeIdoso, dataTrabalho, valorRendimento, valorTotal;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeIdoso = (TextView)itemView.findViewById(R.id.nomeIdosoRendimentos);
            dataTrabalho = (TextView)itemView.findViewById(R.id.dataTrabalhoRendimentos);
            valorRendimento = (TextView)itemView.findViewById(R.id.valorRendimentos);
        }
    }
}
