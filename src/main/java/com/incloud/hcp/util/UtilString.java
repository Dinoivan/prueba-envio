package com.incloud.hcp.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UtilString {

    public static List<String> fragmentaTexto(String text, Integer size) {
        List<String> result = new ArrayList<String>((text.length() + size - 1) / size);
        for (int i = 0; i < text.length(); i += size) {
            result.add(text.substring(i, Math.min(text.length(), i + size)));
        }
        return result;
    }

    public static String concatenarTexto(List<String> listaCadenas) {
        if(listaCadenas == null){
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        listaCadenas.forEach(o -> stringBuffer.append(o));
        return stringBuffer.toString();
    }

    public static String coalesce(String text){
        return Optional.ofNullable( text ).orElse( "" );
    }

    public static String coalesce( String text, String textIsNull ){
        return Optional.ofNullable( text ).orElse( textIsNull );
    }

    public static String coalesceTrim( String text ){
        return coalesce( text ).trim();
    }

    public static Boolean nonEmpty( String text ){

        return !isEmpty( text );

    }

    public static  Boolean isEmpty( String text ){

        return  coalesceTrim( text ).isEmpty();

    }

    public static String subStringOf( String s, Integer offset ){

        String text = coalesce( s );

        if( text.length() < 255 ){
            return text;
        }else{
            return text.substring( 255 );
        }

    }

    public static String replace( String text, String target, String replacement  ){

        return coalesceTrim( text ).replace( target, replacement );

    }


    public static String getIndicator( Boolean condition ){

        return condition ? "X" : "";

    }

    public static Boolean getIndicator( String condition ){

        return UtilString.coalesceTrim( condition ).equals( "X" );

    }

    public static void validaCampoObligatorio( Object campo, String nombreCampo ){

        String mensaje = "Es necesario enviar información en el campo: ";

        if( campo == null ){

            throw new RuntimeException( mensaje + nombreCampo );

        }else if( campo instanceof String ){

            if( coalesceTrim( (String) campo ).isEmpty() ){

                throw new RuntimeException( mensaje + nombreCampo );

            }

        }

    }

}