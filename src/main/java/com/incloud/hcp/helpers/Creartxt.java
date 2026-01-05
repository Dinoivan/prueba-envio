package com.incloud.hcp.helpers;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Creartxt {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public String creacionTXT(String contenidoTxt) throws FileNotFoundException {
        //String localPath = "src/main/resources";
        File localPath = ResourceUtils.getFile("classpath:reportes/");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_hh_mm_ss");
        String fileName = "database"+formatter.format(LocalDateTime.now())+".txt";
        String fullNamePath = localPath+"/"+fileName;
        //String filenamePrueba= "pruebaa.txt";
        String sftpPath = "/";
        String sftpHost = "200.41.106.103";
        String sftpPort = "2222";
        String sftpUser = "sftpcope";
        String sftpPassword = "S3t%$&p30\"/";
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

            try {

                //BufferedWriter parametrosEntrada = new BufferedWriter(new FileWriter(localPath+"/"+filenamePrueba, true));
                File file = new File(localPath+fileName);
                logger.error("mostrar file: {}",file);
                logger.error("mostrar localPath: {}",localPath);
                logger.error("localPath: {}",localPath);

                BufferedWriter parametrosEntrada = new BufferedWriter(new FileWriter(file, true));

                parametrosEntrada.write(contenidoTxt);
                logger.error(contenidoTxt);

                parametrosEntrada.close();
                sftpChannel.put(localPath+fileName,sftpPath);
                Thread.sleep(7000);
                file.delete();
            } catch (IOException e) {
                return "No se genero txt en el SFTP: "+e;
            }

            sftpChannel.disconnect();
            session.disconnect();

            logger.error("Disconnected from sftp");

        } catch (Exception e) {
            return "No se genero txt en el SFTP: "+e;
        }

        return "se creo correctamen";
    }

}

