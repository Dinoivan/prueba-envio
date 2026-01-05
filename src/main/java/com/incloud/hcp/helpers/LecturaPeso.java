package com.incloud.hcp.helpers;

import com.jcraft.jsch.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Optional;
import java.util.Properties;
import java.util.stream.Stream;

public class LecturaPeso {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private static Session session = null;
    private static String sftpPath = "/";
    private static String sftpHost = "200.41.106.103";
    private static String sftpPort = "2222";
    private static String sftpUser = "sftpcope";
    private static String sftpPassword = "S3t%$&p30\"/";
    public String LecturaPesoSFTP(String txtName) throws JSchException, SftpException {
        logger.error("[LecuraPesoSFTP]:Inicio");
        String fileName = txtName + ".txt";
        ChannelSftp channel = connect();
        Stream<String> contents = getContents(channel, fileName);
        Optional<String> firstValue = contents.findFirst();
        String result = firstValue.orElse(null).trim();
        disconnect(channel);
        return result.trim();
    }
    private static Stream<String> getContents(ChannelSftp channel, String fileName) throws SftpException {
        InputStream inputStream = channel.get(sftpPath + fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.lines();
    }

    private static void disconnect(ChannelSftp channel) {
        channel.exit();
        session.disconnect();
    }

    private static ChannelSftp connect() throws JSchException {
        session = getSession();
        ChannelSftp channel = (ChannelSftp) session.openChannel("sftp");
        channel.connect();
        return channel;
    }

    private static Session getSession() throws JSchException {
        JSch jsch = new JSch();
        Session session = jsch.getSession(sftpUser, sftpHost, Integer.parseInt(sftpPort));
        Properties config = new Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.setPassword(sftpPassword);
        session.connect();
        return session;
    }
}
