package bsi.ufrpe.br.cared.infra;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import bsi.ufrpe.br.cared.cuidador.dominio.Cuidador;
import bsi.ufrpe.br.cared.horario.dominio.Horario;
import bsi.ufrpe.br.cared.pessoa.dominio.Pessoa;

public class Sessao {
    private static FirebaseAuth firebaseAuth;
    private static DatabaseReference databasePessoa;
    private static DatabaseReference databaseUser;
    private static DatabaseReference databaseCuidador;
    private static DatabaseReference databaseHorarioDisponivel;
    private static DatabaseReference databaseAgendamento;
    private static DatabaseReference databaseAvaliacao;
    private static final Map<String, Object> values = new HashMap<>();

    private Sessao(){}

    public static DatabaseReference getDatabasePessoa(){
        if(databasePessoa == null){
            databasePessoa = FirebaseDatabase.getInstance().getReference("pessoa");
        }
        return databasePessoa;
    }

    public static DatabaseReference getDatabaseUser() {
        if(databaseUser == null){
            databaseUser = FirebaseDatabase.getInstance().getReference("user");
        }
        return databaseUser;
    }

    public static DatabaseReference getDatabaseCuidador() {
        if(databaseCuidador == null){
            databaseCuidador = FirebaseDatabase.getInstance().getReference("cuidador");
        }
        return databaseCuidador;
    }

    public static DatabaseReference getDatabaseHorarioDisponivel() {
        if(databaseHorarioDisponivel == null){
            databaseHorarioDisponivel = FirebaseDatabase.getInstance().getReference("horariodisponivel");
        }
        return databaseHorarioDisponivel;
    }

    public static DatabaseReference getDatabaseAgendamento() {
        if(databaseAgendamento == null){
            databaseAgendamento = FirebaseDatabase.getInstance().getReference("agendamento");
        }
        return databaseAgendamento;
    }

    public static DatabaseReference getDatabaseAvaliacao(){
        if(databaseAvaliacao==null){
            databaseAvaliacao = FirebaseDatabase.getInstance().getReference("avaliacao");
        }
        return databaseAvaliacao;
    }

    public static FirebaseAuth getFirebaseAuth(){
        if(firebaseAuth == null){
            firebaseAuth = FirebaseAuth.getInstance();
        }
        return firebaseAuth;
    }

    public static String getUserId(){
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public static void setValue(String key, Object value){
        values.put(key, value);
    }

    public static void setPessoa(TipoUsuario usuario, Object object){
        if(usuario.equals(TipoUsuario.PESSOA)){
            setValue("conta", usuario);
            setValue("pessoa", object);
        }else if (usuario.equals(TipoUsuario.CUIDADOR)){
            setValue("conta", usuario);
            setValue("cuidador", object);
        }else{
            setValue("conta", usuario);
            setValue("pessoa", object);
            setValue("cuidador", object);
        }
    }

    public static Pessoa getPessoa(){
        return (Pessoa) values.get("pessoa");
    }

    public static Cuidador getCuidador(){
        return (Cuidador) values.get("cuidador");
    }

    public static TipoUsuario getTipo(){
        return (TipoUsuario) values.get("conta");
    }

    public static void logout(){
        setPessoa(TipoUsuario.NONE, null);
        getFirebaseAuth().signOut();
    }
}
