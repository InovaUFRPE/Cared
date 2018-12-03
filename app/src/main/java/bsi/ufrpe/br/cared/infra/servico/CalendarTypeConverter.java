package bsi.ufrpe.br.cared.infra.servico;

import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import bsi.ufrpe.br.cared.horario.dominio.Horario;

public class CalendarTypeConverter {
    private static final SimpleDateFormat sdfdh = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private static final SimpleDateFormat sdfd = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat sdfh = new SimpleDateFormat("HH:mm");

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
        Calendar calendar = getEmptyCalendar();
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
        Calendar calendar = getEmptyCalendar();
        calendar.setTimeInMillis(time);
        return calendar;
    }

    public static long calendarDayToLong(CalendarDay day){
        return calendarToLong(calendarDayToCalendar(day));
    }

    public static CalendarDay longToCalendarDay(long time){
        return calendarToCalendarDay(longToCalendar(time));
    }

    public static Calendar getEmptyCalendar(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(1970, 0, 1, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static Calendar setDayBegin(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    public static Calendar setDayEnd(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar;
    }

    public static Calendar getTodayStart(){
        return setDayBegin(Calendar.getInstance());
    }

    public static Calendar getTodayEnd(){
        return setDayEnd(Calendar.getInstance());
    }

    public static MonthAdapter.CalendarDay calendarToCalendarDay2(Calendar calendar){
        MonthAdapter.CalendarDay calendarDay = new MonthAdapter.CalendarDay();
        calendarDay.setDay(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) - 1, calendar.get(Calendar.DAY_OF_MONTH));
        return calendarDay;
    }

    public static Calendar CalendarDay2toCalendar(MonthAdapter.CalendarDay calendarDay){
        Calendar calendar = getEmptyCalendar();
        calendar.setTimeInMillis(calendarDay.getDateInMillis());
        return calendar;
    }

    public static SimpleDateFormat getSdfdh() {
        return sdfdh;
    }

    public static SimpleDateFormat getSdfd() {
        return sdfd;
    }

    public static SimpleDateFormat getSdfh() {
        return sdfh;
    }
}
