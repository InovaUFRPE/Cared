package bsi.ufrpe.br.cared.infra.servico;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import bsi.ufrpe.br.cared.horario.dominio.Horario;

public class CalendarTypeConverter {

    public static List<CalendarDay> toCalendarDayList(Horario horario){
        List<CalendarDay> list = new ArrayList<>();
        Calendar calendar = longToCalendar(horario.getInicio());
        Calendar fim = longToCalendar(horario.getFim());
        list.add(calendarToCalendarDay(calendar));
        while(!(calendar.get(Calendar.YEAR) == fim.get(Calendar.YEAR)
        && calendar.get(Calendar.MONTH) == fim.get(Calendar.MONTH)
        && calendar.get(Calendar.DAY_OF_MONTH) == fim.get(Calendar.DAY_OF_MONTH))){
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            list.add(calendarToCalendarDay(calendar));
        }
        return list;
    }

    public static Calendar calendarDayToCalendar(CalendarDay day){
        Calendar calendar = Calendar.getInstance();
        calendar.set(day.getYear(), day.getMonth()-1, day.getDay());
        return calendar;
    }

    public static CalendarDay calendarToCalendarDay(Calendar calendar){
        CalendarDay day = CalendarDay.from(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));
        return day;
    }

    public static long calendarToLong(Calendar calendar){
        return calendar.getTimeInMillis();
    }

    public static Calendar longToCalendar(long time){
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        return calendar;
    }

    public static long calendarDayToLong(CalendarDay day){
        return calendarToLong(calendarDayToCalendar(day));
    }

    public static CalendarDay longToCalendarDay(long time){
        return calendarToCalendarDay(longToCalendar(time));
    }
}
