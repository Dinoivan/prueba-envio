package com.incloud.hcp.util;

import com.incloud.hcp.exception.ServiceException;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Optional;

/**
 * Created by USER on 22/11/2017.
 */
public class DateUtils {

    private static Logger log = LoggerFactory.getLogger(DateUtils.class);
    private static String datePattern = "dd/MM/yyyy";
    private static String timePattern = "HH:mm";
    private static String ZONA_LIMA = "America/Lima";
    private static Integer DELTA_HORA_DESFACE_LIMA = 5;


    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_ZONE_ID = "America/Lima";
    public static final String DATE_TIME_FORMAT_RESOURCES = "yyyy-MM-dd HH:mm";

    public final static long SECOND_MILLIS = 1000;
    public final static long MINUTE_MILLIS = SECOND_MILLIS*60;
    public final static long HOUR_MILLIS = MINUTE_MILLIS*60;
    public final static long DAY_MILLIS = HOUR_MILLIS*24;
    public final static long YEAR_MILLIS = DAY_MILLIS*365;

    public static DateFormat OUT_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    public static DateFormat OUT_TIME_FORMAT = new SimpleDateFormat("H:mm:ss");
    public static DateFormat OUT_DATETIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy H:mm:ss");
    public static DateFormat OUT_TIMESTAMP_FORMAT = new SimpleDateFormat("dd/MM/yyyy H:mm:ss.SSS");

    public static DateFormat IN_DATE_FORMAT = new SimpleDateFormat("dd/MM/yyyy");
    public static DateFormat IN_TIME_FORMAT = new SimpleDateFormat("H:mm:ss");
    public static DateFormat IN_DATETIME_FORMAT = new SimpleDateFormat("dd/MM/yyyy H:mm:ss");
    public static DateFormat IN_TIMESTAMP_FORMAT = new SimpleDateFormat("dd/MM/yyyy H:mm:ss.SSS");

    public static DateFormat DATE_TIME_FORMAT= new SimpleDateFormat( "yyyyMMddkkmmss" );
    public static final String DEFAULT_TIMEZONE = "America/Lima";
    public static final String SAP_DATE_FORMAT = "yyyyMMdd";
    public static final String STANDARD_DATE_FORMAT = "dd/MM/yyyy";

    public static Calendar calendar = new GregorianCalendar();

    static
    {
        IN_DATE_FORMAT.setLenient(false);
        IN_TIME_FORMAT.setLenient(false);
        IN_DATETIME_FORMAT.setLenient(false);
    }

    public static Date obtenerFechaActual() {
        ZoneId zona = ZoneId.systemDefault();
        //ZoneId zona = ZoneId.of(ZONA_LIMA);
        Date fechaActual =  Date.from(LocalDate.now().atStartOfDay(zona).toInstant());
        log.error("CBAZALAR DateUtil obtenerFechaActual fechaActual: "+fechaActual);
//        int aux = fechaActual.getHours() - DELTA_HORA_DESFACE_LIMA;
//        fechaActual.setHours(aux);
//        log.error("CBAZALAR DateUtil obtenerFechaActual fechaActual 01 : "+fechaActual);
//        fechaActual.setHours(0);
//        log.error("CBAZALAR DateUtil obtenerFechaActual fechaActual 02 : "+fechaActual);
        //log.error("obtenerFechaActual: " + fechaActual);
        return fechaActual;
    }

    public static Date obtenerFechaHoraActual() {
        ZoneId zona = ZoneId.systemDefault();
        Date fechaActual =  Date.from(LocalDateTime.now().atZone(zona).toInstant());
        int aux = fechaActual.getHours() - DELTA_HORA_DESFACE_LIMA;
        fechaActual.setHours(aux);
        //log.error("obtenerFechaHoraActual: " + fechaActual);
        return fechaActual;
    }

        /**
     * This method generates a string representation of a date based
     * on the System Property 'dateFormat'
     * in the format you specify on input
     *
     * @param aDate A date to convert
     * @return a string representation of the date
     */
    public static final String convertDateToString(Date aDate) {
        return getDateTime(datePattern, aDate);
    }

    /**
     * This method generates a string representation of a date based
     * on the System Property 'dateFormat'
     * in the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param aDate A date to convert
     * @return a string representation of the date
     */
    public static final String convertDateToString(String aMask, Date aDate) {
        return getDateTime(aMask, aDate);
    }

    /**
     * This method converts a String to a date using the datePattern
     *
     * @param strDate the date to convert (in format MM/dd/yyyy)
     * @return a date object
     *
     * @throws ParseException
     */
    public static Date convertStringToDate(String strDate)
            throws ParseException {
        Date aDate = null;

        try {
            if (log.isDebugEnabled()) {
                log.debug("converting date with pattern: " + datePattern);
            }

            aDate = convertStringToDate(datePattern, strDate);
        } catch (ParseException pe) {
            log.error("Could not convert '" + strDate
                    + "' to a date, throwing exception");
            pe.printStackTrace();
            throw new ParseException(pe.getMessage(),
                    pe.getErrorOffset());

        }

        return aDate;
    }

    /**
     * This method generates a string representation of a date/time
     * in the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param strDate a string representation of a date
     * @return a converted Date object
     * @see SimpleDateFormat
     * @throws ParseException
     */
    public static final Date convertStringToDate(String aMask, String strDate)
            throws ParseException {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);

        if (log.isDebugEnabled()) {
            log.debug("converting '" + strDate + "' to date with mask '"
                    + aMask + "'");
        }

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            //log.error("ParseException: " + pe);
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return (date);
    }

    public static final Date convertStringToDate02(String aMask, String strDate) {
        SimpleDateFormat df = null;
        Date date = null;
        df = new SimpleDateFormat(aMask);

        if (log.isDebugEnabled()) {
            log.debug("converting '" + strDate + "' to date with mask '"
                    + aMask + "'");
        }

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            //log.error("ParseException: " + pe);
            //throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return (date);
    }





    public static int getDiaSemana(String fecha, String datePattern) {
        String Valor_dia = null;
        SimpleDateFormat df = new SimpleDateFormat(datePattern);
        Date fechaActual = null;
        try {
            fechaActual = df.parse(fecha);
        } catch (ParseException e) {
            System.err.println("No se ha podido parsear la fecha.");
            e.printStackTrace();
        }
        GregorianCalendar fechaCalendario = new GregorianCalendar();
        fechaCalendario.setTime(fechaActual);
        int diaSemana = fechaCalendario.get(Calendar.DAY_OF_WEEK);
//        if (diaSemana == 1) {
//            Valor_dia = "Domingo";
//        } else if (diaSemana == 2) {
//            Valor_dia = "Lunes";
//        } else if (diaSemana == 3) {
//            Valor_dia = "Martes";
//        } else if (diaSemana == 4) {
//            Valor_dia = "Miercoles";
//        } else if (diaSemana == 5) {
//            Valor_dia = "Jueves";
//        } else if (diaSemana == 6) {
//            Valor_dia = "Viernes";
//        } else if (diaSemana == 7) {
//            Valor_dia = "Sabado";
//        }
        return diaSemana;
    }

    public static Date obtenerFechaActualv02() {
        ZoneId zona = ZoneId.systemDefault();
        Date fechaActual =  Date.from(LocalDate.now().atStartOfDay(zona).toInstant());
        return fechaActual;
    }

    public static Date obtenerFechaHoraActualv02() {
        ZoneId zona = ZoneId.systemDefault();
        Date fechaActual =  Date.from(LocalDateTime.now().atZone(zona).toInstant());
        return fechaActual;
    }

    public static Date obtenerFechaHoraActualMinusMin(long minutes) {
        ZoneId zona = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.minusMinutes(minutes);
        Date fechaActual =  Date.from(localDateTime.atZone(zona).toInstant());
        return fechaActual;
    }

    public static Date obtenerFechaHoraActualPlusMin(long minutes) {
        ZoneId zona = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.plusMinutes(minutes);
        Date fechaActual =  Date.from(localDateTime.atZone(zona).toInstant());
        return fechaActual;
    }

    public static Date obtenerFechaHoraActualPlusHora(long horas) {
        ZoneId zona = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.now();
        localDateTime = localDateTime.plusHours(horas);
        Date fechaActual =  Date.from(localDateTime.atZone(zona).toInstant());
        return fechaActual;
    }

    public static Date obtenerFechaActualMinusDay(long days) {
        ZoneId zona = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now();
        localDate = localDate.minusDays(days);
        Date fechaActual =  Date.from(localDate.atStartOfDay(zona).toInstant());
        return fechaActual;
    }

    public static Date obtenerFechaActualPlusDay(long days) {
        ZoneId zona = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now();
        localDate = localDate.plusDays(days);
        Date fechaActual =  Date.from(localDate.atStartOfDay(zona).toInstant());
        return fechaActual;
    }

    public static Date obtenerFechaActualMinusMonth(long month) {
        ZoneId zona = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now();
        localDate = localDate.minusMonths(month);
        Date fechaActual =  Date.from(localDate.atStartOfDay(zona).toInstant());
        return fechaActual;
    }

    public static Date obtenerFechaActualPlusMonth(long month) {
        ZoneId zona = ZoneId.systemDefault();
        LocalDate localDate = LocalDate.now();
        localDate = localDate.plusMonths(month);
        Date fechaActual =  Date.from(localDate.atStartOfDay(zona).toInstant());
        return fechaActual;
    }

    public static int obtenerHourDate(Date fecha) {
        Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
        calendar.setTime(fecha);   // assigns calendar to given date
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        return hora;
    }

    public static Date sumarRestarSegundos(Date date, int segundos) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, segundos);
        return calendar.getTime();
    }

    public static Date sumarRestarMinutos(Date date, int minutos) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutos);
        return calendar.getTime();
    }

    public static Date sumarRestarHoras(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }

    public static Date sumarRestarDias(Date fecha, int dias) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.DAY_OF_YEAR, dias);
        return calendar.getTime();
    }

    public static Date sumarRestarMeses(Date fecha, int meses) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.MONTH, meses);
        return calendar.getTime();
    }

    public static Date sumarRestarAnno(Date fecha, int anno) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fecha);
        calendar.add(Calendar.YEAR, anno);
        return calendar.getTime();
    }

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public static LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
    }



    public static XMLGregorianCalendar dateToXml(Date date){
        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        String strDate=dateFormat.format(date);
        try {
            XMLGregorianCalendar xmlDate= DatatypeFactory.newInstance().newXMLGregorianCalendar(strDate);
            return xmlDate;
        }
        catch (  DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Get an XMLGregorianCalendar for the specified date.
     * @param date
     * @return
     * @throws DatatypeConfigurationException
     */
    protected XMLGregorianCalendar getXmlDate(DateTime date) throws DatatypeConfigurationException {
        DatatypeFactory datatypeFactory=DatatypeFactory.newInstance();
        XMLGregorianCalendar start=datatypeFactory.newXMLGregorianCalendar();
        start.setYear(date.getYear());
        start.setMonth(date.getMonthOfYear());
        start.setTime(date.getHourOfDay(),date.getMinuteOfHour(),date.getSecondOfMinute(),date.getMillisOfSecond());
        start.setDay(date.getDayOfMonth());
        return start;
    }


    /**
     * This method generates a string representation of a date's date/time
     * in the format you specify on input
     *
     * @param aMask the date pattern the string is in
     * @param aDate a date object
     * @return a formatted string representation of the date
     *
     * @see SimpleDateFormat
     */
    public static final String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
            log.error("aDate is null!");
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }




    /**
     * Create a new DateTime. To the last second. This will not create any
     * extra-millis-seconds, which may cause bugs when writing to stores such as
     * databases that round milli-seconds up and down.
     */
    public static Date newDateTime()
    {
        return new Date( (System.currentTimeMillis()/SECOND_MILLIS)*SECOND_MILLIS);
    }

    /**
     * Create a new Date. To the last day.
     */
    public static java.sql.Date newDate()
    {
        return new java.sql.Date( (System.currentTimeMillis()/DAY_MILLIS)*DAY_MILLIS);
    }

    /**
     * Create a new Time, with no date component.
     */
    public static java.sql.Time newTime()
    {
        return new java.sql.Time( System.currentTimeMillis()%DAY_MILLIS);
    }

    /**
     * Create a new Timestamp.
     */
    public static java.sql.Timestamp newTimestamp()
    {
        return new java.sql.Timestamp( System.currentTimeMillis() );
    }

    /**
     * Get the seconds difference
     */
    public static int secondsDiff( Date earlierDate, Date laterDate )
    {
        if( earlierDate == null || laterDate == null ) return 0;

        return (int)((laterDate.getTime()/SECOND_MILLIS) - (earlierDate.getTime()/SECOND_MILLIS));
    }

    /**
     * Get the minutes difference
     */
    public static int minutesDiff( Date earlierDate, Date laterDate )
    {
        if( earlierDate == null || laterDate == null ) return 0;

        return (int)((laterDate.getTime()/MINUTE_MILLIS) - (earlierDate.getTime()/MINUTE_MILLIS));
    }

    /**
     * Get the hours difference
     */
    public static int hoursDiff( Date earlierDate, Date laterDate )
    {
        if( earlierDate == null || laterDate == null ) return 0;

        return (int)((laterDate.getTime()/HOUR_MILLIS) - (earlierDate.getTime()/HOUR_MILLIS));
    }

    /**
     * Get the days difference
     */
    public static int daysDiff( Date earlierDate, Date laterDate )
    {
        if( earlierDate == null || laterDate == null ) return 0;

        return (int)((laterDate.getTime()/DAY_MILLIS) - (earlierDate.getTime()/DAY_MILLIS));
    }


    /**
     * Roll the java.util.Time forward or backward.
     * @param startDate - The start date
     * @period Calendar.YEAR etc
     * @param amount - Negative to rollbackwards.
     */
    public static java.sql.Time rollTime( Date startDate, int period, int amount )
    {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(startDate);
        gc.add(period, amount);
        return new java.sql.Time(gc.getTime().getTime());
    }

    /**
     * Roll the java.util.Date forward or backward.
     * @param startDate - The start date
     * @period Calendar.YEAR etc
     * @param amount - Negative to rollbackwards.
     */
    public static Date rollDateTime( Date startDate, int period, int amount )
    {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(startDate);
        gc.add(period, amount);
        return new Date(gc.getTime().getTime());
    }

    /**
     * Roll the java.sql.Date forward or backward.
     * @param startDate - The start date
     * @period Calendar.YEAR etc
     * @param amount - Negative to rollbackwards.
     */
    public static java.sql.Date rollDate( Date startDate, int period, int amount )
    {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(startDate);
        gc.add(period, amount);
        return new java.sql.Date(gc.getTime().getTime());
    }

    /**
     * Roll the years forward or backward.
     * @param startDate - The start date
     * @param years - Negative to rollbackwards.
     */
    public static java.sql.Date rollYears( Date startDate, int years )
    {
        return rollDate( startDate, Calendar.YEAR, years );
    }

    /**
     * Roll the days forward or backward.
     * @param startDate - The start date
     * @param months - Negative to rollbackwards.
     */
    public static java.sql.Date rollMonths( Date startDate, int months )
    {
        return rollDate( startDate, Calendar.MONTH, months );
    }

    /**
     * Roll the days forward or backward.
     * @param startDate - The start date
     * @param days - Negative to rollbackwards.
     */
    public static java.sql.Date rollDays( Date startDate, int days )
    {
        return rollDate( startDate, Calendar.DATE, days );
    }

    /**
     * Checks the day, month and year are equal.
     */
    public static boolean dateEquals( Date d1, Date d2 )
    {
        if( d1 == null || d2 == null ) return false;

        return d1.getDate() == d2.getDate() &&
                d1.getMonth() == d2.getMonth() &&
                d1.getYear() == d2.getYear();
    }

    /**
     * Checks the hour, minute and second are equal.
     */
    public static boolean timeEquals( Date d1, Date d2 )
    {
        if( d1 == null || d2 == null ) return false;

        return d1.getHours() == d2.getHours() &&
                d1.getMinutes() == d2.getMinutes() &&
                d1.getSeconds() == d2.getSeconds();
    }


    /**
     * Checks the second, hour, month, day, month and year are equal.
     */
    public static boolean dateTimeEquals( Date d1, Date d2 )
    {
        if( d1 == null || d2 == null ) return false;

        return d1.getDate() == d2.getDate() &&
                d1.getMonth() == d2.getMonth() &&
                d1.getYear() == d2.getYear() &&
                d1.getHours() == d2.getHours() &&
                d1.getMinutes() == d2.getMinutes() &&
                d1.getSeconds() == d2.getSeconds();
    }

    /**
     * Convert an Object of type Classs to an Object.
     */
    public static Object toObject( Class clazz, Object value ) throws ParseException
    {
        if( value == null ) return null;
        if( clazz == null ) return value;

        if( java.sql.Date.class.isAssignableFrom( clazz ) ) return toDateSQL( value );
        if( java.sql.Time.class.isAssignableFrom( clazz ) ) return toTime( value );
        if( java.sql.Timestamp.class.isAssignableFrom( clazz ) ) return toTimestamp( value );
        if( Date.class.isAssignableFrom( clazz ) ) return toDateTime( value );

        return value;
    }

    /**
     * Convert an Object to a DateTime, without an Exception
     */
    public static Date getDateTime( Object value )
    {
        try
        {
            return toDateTime( value );
        }
        catch( ParseException pe )
        {
            pe.printStackTrace();
            return null;
        }
    }

    /**
     * Convert an Object to a DateTime.
     */
    public static Date toDateTime( Object value ) throws ParseException
    {
        if( value == null ) return null;
        if( value instanceof Date ) return (Date)value;
        if( value instanceof String )
        {
            if( "".equals(value) ) return null;
            return IN_DATETIME_FORMAT.parse( (String)value );
        }

        return IN_DATETIME_FORMAT.parse( value.toString() );
    }

    /**
     * Convert an Object to a DateTime, without an Exception
     */
    public static Date getDate( Object value )
    {
        try
        {
            return toDate( value );
        }
        catch( ParseException pe )
        {
            pe.printStackTrace();
            return null;
        }
    }

    /**
     * Convert an Object to a DateTime.
     */
    public static Date toDate( Object value ) throws ParseException
    {
        if( value == null ) return null;
        if( value instanceof Date ) return (Date)value;
        if( value instanceof String )
        {
            if( "".equals(value) ) return null;
            return IN_DATE_FORMAT.parse( (String)value );
        }

        return IN_DATE_FORMAT.parse( value.toString() );
    }


    /**
     * Convert an Object to a Date, without an Exception
     */
    public static java.sql.Date getDateSQL( Object value )
    {
        try
        {
            return toDateSQL( value );
        }
        catch( ParseException pe )
        {
            pe.printStackTrace();
            return null;
        }
    }

    /**
     * Convert an Object to a Date.
     */
    public static java.sql.Date toDateSQL( Object value ) throws ParseException
    {
        if( value == null ) return null;
        if( value instanceof java.sql.Date ) return (java.sql.Date)value;
        if( value instanceof String )
        {
            if( "".equals( (String)value ) ) return null;
            return new java.sql.Date( IN_DATE_FORMAT.parse( (String)value ).getTime() );
        }

        return new java.sql.Date( IN_DATE_FORMAT.parse( value.toString() ).getTime() );
    }

    /**
     * Convert an Object to a Time, without an Exception
     */
    public static java.sql.Time getTime( Object value )
    {
        try
        {
            return toTime( value );
        }
        catch( ParseException pe )
        {
            pe.printStackTrace();
            return null;
        }
    }

    /**
     * Convert an Object to a Time.
     */
    public static java.sql.Time toTime( Object value ) throws ParseException
    {
        if( value == null ) return null;
        if( value instanceof java.sql.Time ) return (java.sql.Time)value;
        if( value instanceof String )
        {
            if( "".equals( (String)value ) ) return null;
            return new java.sql.Time( IN_TIME_FORMAT.parse( (String)value ).getTime() );
        }

        return new java.sql.Time( IN_TIME_FORMAT.parse( value.toString() ).getTime() );
    }

    /**
     * Convert an Object to a Timestamp, without an Exception
     */
    public static java.sql.Timestamp getTimestamp( Object value )
    {
        try
        {
            return toTimestamp( value );
        }
        catch( ParseException pe )
        {
            pe.printStackTrace();
            return null;
        }
    }

    /**
     * Convert an Object to a Timestamp.
     */
    public static java.sql.Timestamp toTimestamp( Object value ) throws ParseException
    {
        if( value == null ) return null;
        if( value instanceof java.sql.Timestamp ) return (java.sql.Timestamp)value;
        if( value instanceof String )
        {
            if( "".equals( (String)value ) ) return null;
            return new java.sql.Timestamp( IN_TIMESTAMP_FORMAT.parse( (String)value ).getTime() );
        }

        return new java.sql.Timestamp( IN_TIMESTAMP_FORMAT.parse( value.toString() ).getTime() );
    }

    /**
     * Tells you if the date part of a datetime is in a certain time range.
     */
    public static boolean isTimeInRange( java.sql.Time start, java.sql.Time end, Date d )
    {
        d=new java.sql.Time(d.getHours(),d.getMinutes(),d.getSeconds());

        if (start==null || end==null)
        {
            return false;
        }

        if (start.before(end)&&(!(d.after(start)&&d.before(end))))
        {
            return false;
        }

        if (end.before(start)&&(!(d.after(end)||d.before(start))))
        {
            return false;
        }
        return true;
    }

    public static  int getYear( Date date )
    {
        calendar.setTime( date );
        return calendar.get( Calendar.YEAR );
    }

    public static int getMonth( Date date )
    {
        calendar.setTime( date );
        return calendar.get( Calendar.MONTH );
    }

    public static int getDate( Date date )
    {
        calendar.setTime( date );
        return calendar.get( Calendar.DATE );
    }

    public static int getHour( Date date )
    {
        calendar.setTime( date );
        return calendar.get( Calendar.HOUR );
    }

    public static int getMinute( Date date )
    {
        calendar.setTime( date );
        return calendar.get( Calendar.MINUTE );
    }

    public static int getSeconds( Date date )
    {
        calendar.setTime( date );
        return calendar.get( Calendar.SECOND );
    }

    public static int getMillisecond( Date date )
    {
        calendar.setTime( date );
        return calendar.get( Calendar.MILLISECOND );
    }

    /**
     * Convert an Object to a String using Dates
     */
    public static String toString( Object date )
    {
        if( date == null ) return null;

        if( java.sql.Timestamp.class.isAssignableFrom( date.getClass() ) )
        {
            return OUT_TIMESTAMP_FORMAT.format( date );
        }
        if( java.sql.Time.class.isAssignableFrom( date.getClass() ) )
        {
            return OUT_TIME_FORMAT.format( date );
        }
        if( java.sql.Date.class.isAssignableFrom( date.getClass() ) )
        {
            return OUT_DATE_FORMAT.format( date );
        }
        if( Date.class.isAssignableFrom( date.getClass() ) )
        {
            return OUT_DATETIME_FORMAT.format( date );
        }

        throw new IllegalArgumentException( "Unsupported type " + date.getClass() );
    }

    public static int calcularEdad(String fecha) {
        String datetext = fecha;

        try {
            Calendar birth = new GregorianCalendar();
            Calendar today = new GregorianCalendar();

            int age=0;
            int factor=0;

            Date birthDate=new SimpleDateFormat("dd/MM/yyyy").parse(datetext);
            Date currentDate=new Date(); //current date
            birth.setTime(birthDate);
            today.setTime(currentDate);

            if (today.get(Calendar.MONTH) <= birth.get(Calendar.MONTH)) {
                if (today.get(Calendar.MONTH) == birth.get(Calendar.MONTH)) {
                    if (today.get(Calendar.DATE) > birth.get(Calendar.DATE)) {
                        factor = -1; //Aun no celebra su cumpleaos
                    }
                } else {
                    factor = -1; //Aun no celebra su cumpleaos
                }
            }
            age=(today.get(Calendar.YEAR)-birth.get(Calendar.YEAR))+factor;
            return age;
        } catch (ParseException e) {
            return -1;
        }
    }



    public static ZonedDateTime getDefaultCurrentZonedDateTime() {
        return getCurrentZonedDateTime(ZoneId.of(DEFAULT_TIMEZONE));
    }

    public static ZonedDateTime getCurrentZonedDateTime(ZoneId zoneId) {
        Instant now = Instant.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(now, zoneId);
        return zonedDateTime;
    }

    public static ZonedDateTime getDefaultZonedDateTimeFromDate(Date date) {
        return getZonedDateTimeFromDate(date, ZoneId.of(DEFAULT_TIMEZONE));
    }

    public static ZonedDateTime getZonedDateTimeFromDate(Date date, ZoneId zoneId) {
        Instant instant = date.toInstant();
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, zoneId);
        return zonedDateTime;
    }

    public static Optional<Date> getDateFromString(String date) {
        return getDateFromString(date, DEFAULT_DATE_FORMAT);
    }

    public static Optional<Date> getDateTimeFromString(String date) {
        return getDateFromString(date, DEFAULT_DATETIME_FORMAT);
    }

    public static Date getDevuelveFechaHora(String pfecha, String phora) {
        if (StringUtils.isBlank(pfecha)) {
            return null;
        }
        Date dFecha = getDateFromString(pfecha).orElse(null);
        if (StringUtils.isNotBlank(phora)) {
            String fechaHora = pfecha + " " + phora;
            dFecha = getDateFromString(fechaHora, DATE_TIME_FORMAT_RESOURCES).orElse(null);
        }
        return dFecha;
    }

    public static Optional<Date> getDateFromString(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format);
            Date d = df.parse(date);
            return Optional.of(d);
        } catch (ParseException ex) {
            return Optional.empty();
        }
    }

    public static Optional<Date> zonedDateTimeToDate(ZonedDateTime zonedDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
        String date = dateTimeFormatter.format(zonedDateTime);
        return getDateFromString(date);
    }

    public static Optional<Date> zonedDateTimeToDateTime(ZonedDateTime zonedDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DEFAULT_DATETIME_FORMAT);
        String date = dateTimeFormatter.format(zonedDateTime);
        return getDateTimeFromString(date);
    }

    public static String formatDate(Date d){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String s = df.format(d);
        return s;
    }

    public static String formatDateTime(Date dt){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String s = df.format(dt);
        return s;
    }

    public static Date getCurrentDate() {
        ZonedDateTime currentZonedDateTime = getDefaultCurrentZonedDateTime();
        Optional<Date> currentDateOptional = zonedDateTimeToDateTime(currentZonedDateTime);
        Date fechaProceso = currentDateOptional.orElseThrow(() -> new ServiceException("no se pudo obtener fecha del proceso"));
        return fechaProceso;
    }

    public static Timestamp convertToTimestamp(String strFecha, Boolean indTime){
        if (StringUtils.isBlank(strFecha))
            return null;
        Timestamp timeStampDate=null;
        DateFormat formatter;

        if (indTime) {
            formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        }else{
            formatter = new SimpleDateFormat("dd/MM/yyyy");
        }

        Date date = null;
        try {
            date = (Date) formatter.parse(strFecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        timeStampDate = new Timestamp(date.getTime());

        return timeStampDate;
    }

    public static Time dateToTime(Date date){
        if (date == null)
            return null;
        return new Time(date.getTime());
    }



    public static Timestamp getCurrentTimestamp() {
        return Timestamp.valueOf(LocalDateTime.ofInstant(Instant.now(), ZoneId.of(DEFAULT_TIMEZONE)));
    }


    public static int getCurrentHourOfDay(){
        return LocalDateTime.ofInstant(Instant.now(), ZoneId.of(DEFAULT_TIMEZONE)).getHour();
    }

    public static int getCurrentDay() {
        return LocalDateTime.ofInstant(Instant.now(), ZoneId.of(DEFAULT_TIMEZONE)).getDayOfMonth();
    }


    public static LocalDate utilDateToLocalDate (Date utilDate) {
        if (utilDate == null)
            return null;

        return utilDate.toInstant().atZone(ZoneId.of("UTC")).toLocalDate();
    }

    public static Date localDateToUtilDate(LocalDate localDate){
        if (localDate == null)
            return null;

        return Date.from(localDate.atStartOfDay(ZoneId.of("UTC")).toInstant());
    }

    public static Date localDateTimeToUtilDate(LocalDateTime localDateTime){
        if (localDateTime == null)
            return null;

        return Date.from(localDateTime.atZone(ZoneId.of("UTC")).toInstant());
    }

    public static Date utilDateAtEndOfDay(Date utilDate){
        if (utilDate == null)
            return null;


        LocalDate localDate = utilDate.toInstant().atZone(ZoneId.of("UTC")).toLocalDate();
        LocalDateTime localDateTime = localDate.atTime(23,59,59);

        return localDateTimeToUtilDate(localDateTime);
    }

    public static Date timestampToUtilDateAtStartOfDay(Timestamp timestamp){
        if (timestamp == null)
            return null;


        LocalDate localDate = timestamp.toInstant().atZone(ZoneId.of("UTC")).toLocalDate();
        LocalDateTime localDateTime = localDate.atStartOfDay();

        return localDateTimeToUtilDate(localDateTime);
    }


    public static LocalTime sqlTimeToLocalTime (Time sqlTime) {
        if (sqlTime == null)
            return null;

        return sqlTime.toLocalTime();
    }

    public static LocalDateTime getLocalDateTimeFromDateAndTime (Date utilDate, Time sqlTime) {
        if (utilDate == null || sqlTime == null)
            return null;

        LocalDate localDate = utilDateToLocalDate(utilDate);
        LocalTime localTime = sqlTimeToLocalTime(sqlTime);

        return localTime.atDate(localDate);
    }


    public static String getFechaActualAsSapString(){
        LocalDateTime localDateTimeActual = LocalDateTime.ofInstant(Instant.now(), ZoneId.of(DEFAULT_TIMEZONE));
        return localDateTimeActual.format(DateTimeFormatter.ofPattern(SAP_DATE_FORMAT));
    }

    public static String getFechaActualAsStringPattern(String pattern){
        LocalDateTime localDateTimeActual = LocalDateTime.ofInstant(Instant.now(), ZoneId.of(DEFAULT_TIMEZONE));
        return localDateTimeActual.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String getFechaInicioAsSapStringByDiasAtras(int dias){
        LocalDateTime localDateTimeInicio = LocalDateTime.ofInstant(Instant.now(), ZoneId.of(DEFAULT_TIMEZONE)).minusDays(dias);
        return localDateTimeInicio.format(DateTimeFormatter.ofPattern(SAP_DATE_FORMAT));
    }

    public static Date stringToUtilDate(String stringDate){
        if (stringDate == null)
            return null;

        return new Date(java.sql.Date.valueOf(LocalDate.parse(stringDate, DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT))).getTime());
    }

    public static String utilDateToSapString(Date utilDate){
        if (utilDate == null)
            return null;

        return utilDateToStringPattern(utilDate, SAP_DATE_FORMAT);
    }

    public static String utilDateToString(Date utilDate){
        if (utilDate == null)
            return null;

        return utilDateToStringPattern(utilDate, DEFAULT_DATE_FORMAT);
    }

    public static String utilDateToStringPattern(Date utilDate, String pattern){
        if (utilDate == null)
            return null;

        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        return DateTimeFormatter.ofPattern(pattern).format(sqlDate.toLocalDate());
    }

    public static String localDateToString(LocalDate localDate){
        return localDateToStringPattern(localDate, DEFAULT_DATE_FORMAT);
    }

    public static String localDateToSapString(LocalDate localDate){
        return localDateToStringPattern(localDate, SAP_DATE_FORMAT);
    }

    private static String localDateToStringPattern(LocalDate localDate, String pattern){
        if (localDate == null)
            return null;

        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static boolean evaluarModificacionDeDocumento (Date fechaModAnterior, Time horaModAnterior, Date fechaModNueva, Time horaModNueva){
        if(fechaModAnterior == null || horaModAnterior == null){
            if (fechaModNueva == null || horaModNueva == null)
                return false;

            return true;
        }

        LocalDateTime localDateTimeModAnterior = DateUtils.getLocalDateTimeFromDateAndTime(fechaModAnterior, horaModAnterior);
        LocalDateTime localDateTimeModNueva = DateUtils.getLocalDateTimeFromDateAndTime(fechaModNueva, horaModNueva);
        Integer comparacionDateTime = localDateTimeModNueva.compareTo(localDateTimeModAnterior);

        if (comparacionDateTime > 0)
            return true;

        return false;
    }
}
