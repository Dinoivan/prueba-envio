package com.incloud.hcp.util;

import com.incloud.hcp.domain.TasaCambio;
import com.incloud.hcp.exception.ServiceException;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {


    public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_ZONE_ID = "America/Lima";
    public static final String DATE_TIME_FORMAT_RESOURCES = "yyyy-MM-dd HH:mm";

    public static boolean isStringEmpty(String message) {
        return message == null || "".equals(message.trim());
    }


    public static ZonedDateTime getDefaultCurrentZonedDateTime() {
        return getCurrentZonedDateTime(ZoneId.of(DEFAULT_ZONE_ID));
    }

    public static ZonedDateTime getCurrentZonedDateTime(ZoneId zoneId) {
        Instant now = Instant.now();
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(now, zoneId);
        return zonedDateTime;
    }

    public static ZonedDateTime getDefaultZonedDateTimeFromDate(Date date) {
        return getZonedDateTimeFromDate(date, ZoneId.of(DEFAULT_ZONE_ID));
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
        ZonedDateTime currentZonedDateTime = Utils.getDefaultCurrentZonedDateTime();
        Optional<Date> currentDateOptional = Utils.zonedDateTimeToDateTime(currentZonedDateTime);
        Date fechaProceso = currentDateOptional.orElseThrow(() -> new ServiceException("no se pudo obtener fecha del proceso"));
        return fechaProceso;
    }

    public static String obtieneMensajeErrorException(Exception e) {
        String retorno = null;
        if (StringUtils.isNotBlank(e.getMessage())) {
            return e.getMessage();
        }
        if (StringUtils.isNotBlank(e.getLocalizedMessage())) {
            return e.getLocalizedMessage();
        }
        retorno = e.toString();
        return retorno;
    }

    public static Boolean validarEmail(String email){
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher mather = pattern.matcher(email);

        if (!email.isEmpty() && !mather.find()) { // si email no esta vacio debe cumplir el patron
            return false;
        }

        return true;
    }

    public static TasaCambio obtenerTasaCambioInvertida (TasaCambio tasaCambio){
        TasaCambio newTasaCambio = new TasaCambio();

        newTasaCambio.setValor(new BigDecimal("1.00").divide(tasaCambio.getValor(),4, BigDecimal.ROUND_HALF_UP));
        newTasaCambio.setIdMonedaOrigen(tasaCambio.getIdMonedaDestino());
        newTasaCambio.setIdMonedaDestino(tasaCambio.getIdMonedaOrigen());
        newTasaCambio.setFechaTasa(tasaCambio.getFechaTasa());

        return newTasaCambio;
    }
}