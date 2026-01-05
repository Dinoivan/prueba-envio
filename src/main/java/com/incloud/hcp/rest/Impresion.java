package com.incloud.hcp.rest;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import java.io.FileInputStream;

public class Impresion {

    public static void main(String[] args) {
        //System.out.println("hola mundo");
        imprimirDirecto("C:\\Users\\Usuario\\Downloads\\1.png",0 );
    }

    public static void imprimirDirecto(String url, int i) {
        FileInputStream archivo;
        try {
            //el archivo debe estar en la dirección del campo url
            archivo =new FileInputStream(url);
            DocFlavor formato = DocFlavor.INPUT_STREAM.PNG;
            Doc documento = new SimpleDoc(archivo, formato, null);
            PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();

            //Configuración de la impresora
            aset.add(MediaSizeName.NA_LETTER);
            //Elección tamaño de la hoja (carta o A4)
            //aset.add(MediaSizeName.ISO_A4);
            aset.add(new Copies(1));
            //aset.add(Chromaticity.MONOCHROME);
            // aset.add(OrientationRequested.PORTRAIT);
            // Busqueda de impresoras
            PrintService[] services =
                    PrintServiceLookup.lookupPrintServices(formato, aset);
            // creación de orden de impresión
            if (services.length > 0) {
                DocPrintJob job = services[i].createPrintJob();
                try {
                    job.print(documento, aset);
                } catch (PrintException pe) {}
            }
        }catch(Exception e){

        }
    }

}
