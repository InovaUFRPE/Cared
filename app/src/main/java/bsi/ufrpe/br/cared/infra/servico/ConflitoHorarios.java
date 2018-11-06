package bsi.ufrpe.br.cared.infra.servico;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.horario.dominio.HorarioDisponivel;
import bsi.ufrpe.br.cared.infra.Sessao;

public class ConflitoHorarios {

    private ConflitoHorarios(){}

    public static void newHorarioDisponivel(final HorarioDisponivel disponivel){
        final Horario horario = disponivel.getHorario();
        Sessao.getDatabaseHorarioDisponivel().child(Sessao.getUserId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean ok = true;
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    HorarioDisponivel disponivel1 = dataSnapshot1.getValue(HorarioDisponivel.class);
                    if (horario.getInicio() >= disponivel1.getHorario().getInicio() && horario.getInicio() <= disponivel1.getHorario().getFim()
                            && horario.getFim() >= disponivel1.getHorario().getInicio() && horario.getFim() <= disponivel1.getHorario().getFim()){
                        ok = false;
                        break;
                    }else if (horario.getInicio() >= disponivel1.getHorario().getInicio() && horario.getInicio() <= disponivel1.getHorario().getFim()){
                        horario.setInicio(disponivel1.getHorario().getInicio());
                        Sessao.getDatabaseHorarioDisponivel().child(Sessao.getUserId()).child(disponivel1.getId()).setValue(null);
                    }else if (horario.getFim() >= disponivel1.getHorario().getInicio() && horario.getFim() <= disponivel1.getHorario().getFim()) {
                        horario.setFim(disponivel1.getHorario().getFim());
                        Sessao.getDatabaseHorarioDisponivel().child(Sessao.getUserId()).child(disponivel1.getId()).setValue(null);
                    }
                }
                if (ok == true) {
                    Sessao.getDatabaseHorarioDisponivel().child(Sessao.getUserId()).child(disponivel.getId()).setValue(disponivel);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
