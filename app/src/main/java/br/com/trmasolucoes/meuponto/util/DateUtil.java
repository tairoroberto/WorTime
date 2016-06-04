package br.com.trmasolucoes.meuponto.util;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import br.com.trmasolucoes.meuponto.R;

/**
 * Created by tairo on 28/03/15.
 */
public class DateUtil {
    private static NumberFormat numberFormat = new DecimalFormat("#,##0.00", new DecimalFormatSymbols());
    private static String symbol = numberFormat.getCurrency().getSymbol();
    public static final Calendar FIRST_DAY_OF_TIME;
    public static final Calendar LAST_DAY_OF_TIME;
    public static final int DAYS_OF_TIME;

    static {
        FIRST_DAY_OF_TIME = Calendar.getInstance();
        FIRST_DAY_OF_TIME.set(1900, Calendar.JANUARY, 1);
        LAST_DAY_OF_TIME = Calendar.getInstance();
        LAST_DAY_OF_TIME.set(2100, Calendar.DECEMBER, 31);
        DAYS_OF_TIME = 73413; //(int) ((LAST_DAY_OF_TIME.getTimeInMillis() - FIRST_DAY_OF_TIME.getTimeInMillis()) / (24 * 60 * 60 * 1000));
    }

    /*Metodo para pegar o inicio do mes*/
    public static Date getFirstDayMonth(final Date date) {
        Calendar dateIni = new GregorianCalendar();
        dateIni.setTime(date);
        dateIni.set(Calendar.HOUR_OF_DAY, 0);
        dateIni.set(Calendar.MINUTE, 0);
        dateIni.set(Calendar.SECOND, 0);
        dateIni.set(Calendar.MILLISECOND, 0);
        dateIni.set(Calendar.DAY_OF_MONTH, 1);
        return dateIni.getTime();
    }


    /*Metodo para pegar o inicio do mes*/
    public static Date getLastDayMonth(final Date date) {
        Calendar dateFim = new GregorianCalendar();
        dateFim.setTime(date);
        dateFim.set(Calendar.HOUR_OF_DAY, 23);
        dateFim.set(Calendar.MINUTE, 59);
        dateFim.set(Calendar.SECOND, 59);
        dateFim.set(Calendar.MILLISECOND, 999);
        dateFim.set(Calendar.DAY_OF_MONTH, dateFim.getActualMaximum(Calendar.DAY_OF_MONTH));
        return dateFim.getTime();
    }

    public static int getLastDayMonth() {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime( new Date() );
        return cal.getActualMaximum( Calendar.DAY_OF_MONTH );
    }

    public static int[] getDayMonthYear(){
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime( new Date() );
        return new int[]{cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH), cal.get(Calendar.YEAR)};//cal.get(Calendar.DAY_OF_MONTH);
    }

    /*Metodo para pegar a data do dia de hoje do mes*/
    public static Date getDataHoje(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    public static String getDateToString(Date date){
        if (date == null || date.equals(""))
            return null;

        DateFormat formatador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//new SimpleDateFormat("dd/MM/yyyy");
        return formatador.format(date);
    }

    public static Date getStringToDate(String date){
        Date data = null;

        DateFormat formatador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//new SimpleDateFormat("dd/MM/yyyy");
        try {
            data = (Date) formatador.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }



    public static String getDateToStringShort(Date date){
        if (date == null || date.equals(""))
            return null;

        DateFormat formatador = new SimpleDateFormat("dd-MM-yyyy");//new SimpleDateFormat("dd/MM/yyyy");
        return formatador.format(date);
    }

    public static String formataValorToString(BigDecimal value){
        return symbol + numberFormat.format(value);
    }

    public static double formataStringToValor(String value){
        Number number = null;
        try {
            number = numberFormat.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return number.doubleValue();
    }

    public static String getSymbol(){
        return symbol;
    }




    public static String getMes(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            String[] meses = {"Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};

            int retorno = 0;
            GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
            cal.setTime(getDataHoje());

            retorno =  cal.get(GregorianCalendar.MONTH);
            Log.i("Script","Mes:"+retorno);
            return meses[retorno];
        }else{

            DateFormat dateFormat = new SimpleDateFormat("MMMMM");
            return dateFormat.format(getDataHoje());
        }
    }


    public static String getMes(Date data){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            String[] meses = {"Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"};

            int retorno = 0;
            GregorianCalendar cal = (GregorianCalendar) GregorianCalendar.getInstance();
            cal.setTime(data);

            retorno =  cal.get(GregorianCalendar.MONTH);
            Log.i("Script","Mes:"+retorno);
            return meses[retorno];
        }else{

            DateFormat dateFormat = new SimpleDateFormat("MMMMM");
            return dateFormat.format(data);
        }
    }

    public static String getAno(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        return dateFormat.format(getDataHoje());
    }

    public static Date getIntToDate(int dia, int mes, int ano) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("ddMMyyyy");
        try {
            return originalFormat.parse(String.valueOf(dia) + String.valueOf(mes) + String.valueOf(ano));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * Get the position in the ViewPager for a given day
     *
     * @param day
     * @return the position or 0 if day is null
     */
    public static int getPositionForDay(Calendar day) {
        if (day != null) {
            return (int) ((day.getTimeInMillis() - FIRST_DAY_OF_TIME.getTimeInMillis())
                    / 86400000  //(24 * 60 * 60 * 1000)
            );
        }
        return 0;
    }

    /**
     * Get the day for a given position in the ViewPager
     *
     * @param position
     * @return the day
     * @throws IllegalArgumentException if position is negative
     */
    public static Calendar getDayForPosition(int position) throws IllegalArgumentException {
        if (position < 0) {
            throw new IllegalArgumentException("posição não pode ser negativa");
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(FIRST_DAY_OF_TIME.getTimeInMillis());
        cal.add(Calendar.DAY_OF_YEAR, position);
        return cal;
    }


    public static String getFormattedDate(Context context, long date, String pattern) {
        final String defaultPattern = "yyyy-MM-dd";

        if (pattern == null) {
            pattern = defaultPattern;
        }
        SimpleDateFormat simpleDateFormat = null;
        try {
            simpleDateFormat = new SimpleDateFormat(pattern);
        } catch (IllegalArgumentException e) {
            simpleDateFormat = new SimpleDateFormat(defaultPattern);
        }

        return simpleDateFormat.format(new Date(date));
    }
}
