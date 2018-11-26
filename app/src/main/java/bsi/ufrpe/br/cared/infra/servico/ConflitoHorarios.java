package bsi.ufrpe.br.cared.infra.servico;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import bsi.ufrpe.br.cared.horario.dominio.Agendamento;
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

    public static void newAgendamento(final Agendamento agendamento){
        Sessao.getDatabaseHorarioDisponivel().child(agendamento.getCuidadorId()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    HorarioDisponivel disponivel = dataSnapshot1.getValue(HorarioDisponivel.class);
                    if (agendamento.getHorario().getInicio() >= disponivel.getHorario().getInicio() && agendamento.getHorario().getInicio() <= disponivel.getHorario().getFim()
                            && agendamento.getHorario().getFim() >= disponivel.getHorario().getInicio() && agendamento.getHorario().getFim() <= disponivel.getHorario().getFim()){
                        if (agendamento.getHorario().getInicio() == disponivel.getHorario().getInicio() && agendamento.getHorario().getFim() == disponivel.getHorario().getFim()){
                            Sessao.getDatabaseHorarioDisponivel().child(agendamento.getCuidadorId()).child(disponivel.getId()).setValue(null);
                            break;
                        }else if (agendamento.getHorario().getInicio() == disponivel.getHorario().getInicio()){
                            Horario horario = disponivel.getHorario();
                            horario.setInicio(agendamento.getHorario().getFim() + 1);
                            disponivel.setHorario(horario);
                            Sessao.getDatabaseHorarioDisponivel().child(agendamento.getCuidadorId()).child(disponivel.getId()).setValue(disponivel);
                            break;
                        }else if (agendamento.getHorario().getFim() == disponivel.getHorario().getFim()){
                            Horario horario = disponivel.getHorario();
                            horario.setFim(agendamento.getHorario().getInicio() - 1);
                            disponivel.setHorario(horario);
                            Sessao.getDatabaseHorarioDisponivel().child(agendamento.getCuidadorId()).child(disponivel.getId()).setValue(disponivel);
                            break;
                        }else{
                            Horario horario = new Horario();
                            horario.setInicio(disponivel.getHorario().getInicio());
                            horario.setFim(agendamento.getHorario().getInicio() - 1);
                            Horario horario1 = new Horario();
                            horario1.setFim(disponivel.getHorario().getFim());
                            horario1.setInicio(agendamento.getHorario().getFim() + 1);
                            disponivel.setHorario(horario);
                            HorarioDisponivel disponivel1 = new HorarioDisponivel();
                            disponivel1.setUserId(agendamento.getCuidadorId());
                            disponivel1.setHorario(horario1);
                            String id = Sessao.getDatabaseHorarioDisponivel().push().getKey();
                            disponivel1.setId(id);
                            Sessao.getDatabaseHorarioDisponivel().child(agendamento.getCuidadorId()).child(disponivel.getId()).setValue(disponivel);
                            Sessao.getDatabaseHorarioDisponivel().child(agendamento.getCuidadorId()).child(disponivel1.getId()).setValue(disponivel1);
                            break;
                        }
                    }
                }
                String aId = Sessao.getDatabaseAgendamento().child(agendamento.getCuidadorId()).push().getKey();
                agendamento.setId(aId);
                Sessao.getDatabaseAgendamento().child(agendamento.getCuidadorId()).child(agendamento.getId()).setValue(agendamento);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
