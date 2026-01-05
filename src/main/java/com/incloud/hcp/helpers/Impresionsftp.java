package com.incloud.hcp.helpers;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.print.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class Impresionsftp {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public String LecuraSFTP() {

        //String localPath = "C:\\Users\\Usuario\\Desktop\\sftp-testing";
        String fileName = "prueba.txt";
        String sftpPath = "/";
        String sftpHost = "200.41.106.103";
        String sftpPort = "2222";
        String sftpUser = "sftpcope";
        String sftpPassword = "S3t%$&p30\"/";

        //Usuario: sftpcope
        //Clave:   S3t%$&p30"/

        String contenido = null;
        try {
            JSch jsch = new JSch();
            Session session = jsch.getSession(sftpUser, sftpHost, Integer.valueOf(sftpPort));
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(sftpPassword);
            logger.error("Connecting------");
            session.connect();
            logger.error("Established Session");

            Channel channel = session.openChannel("sftp");
            ChannelSftp sftpChannel = (ChannelSftp) channel;
            sftpChannel.connect();

            InputStream stream = sftpChannel.get(sftpPath + "/" + fileName);
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(stream));
                // SE RECORRE EL ARCHIVO PARA LEER CADA PARRAFO
                String line;
                while ((line = br.readLine()) != null) {
                    //contenido = line;
                    contenido= line;

                }
                imprimirSFTP(contenido,0);
            } catch (Exception e) {
                System.out.println("error " + e.getMessage());
                e.getMessage();
            }

            sftpChannel.disconnect();
            session.disconnect();

            System.out.println("Disconnected from sftp");

        } catch (Exception e) {
            e.printStackTrace();
        }

        return contenido;
    }

    public  void imprimirSFTP(String contenido, int i) {

        try {
            //el archivo debe estar en la dirección del campo url
            byte[] bytes = new String(contenido).getBytes(Charset.forName("Cp858"));

            DocFlavor formato = DocFlavor.BYTE_ARRAY.AUTOSENSE;;
            Doc documento = new SimpleDoc(bytes, formato, null);

            //PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
            logger.error("Imprimiendo...");
            //Configuración de la impresora
            //aset.add(MediaSizeName.NA_LETTER);
            //Elección tamaño de la hoja (carta o A4)
            //aset.add(MediaSizeName.ISO_A4);
            //aset.add(new Copies(1));
            //aset.add(Chromaticity.MONOCHROME);
            // aset.add(OrientationRequested.PORTRAIT);
            // Busqueda de impresoras




            PrintService[] services =
                    PrintServiceLookup.lookupPrintServices(formato,null);
            String nameImpresora = services[i].getName();
            // creación de orden de impresión
            if(services.length > 0) {
                DocPrintJob pj = services[i].createPrintJob();

                logger.error("Nombre impresora: {}"+nameImpresora);
                try {
                    //logger.error("Ingreso");
                    pj.print(documento,null);
                } catch (PrintException pe) {}
            }

            logger.error("Nombre impresora: {}"+nameImpresora);
        } catch (Exception e) {

        }


    }
}
