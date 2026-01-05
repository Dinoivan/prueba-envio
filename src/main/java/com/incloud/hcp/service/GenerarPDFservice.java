package com.incloud.hcp.service;

import net.sf.jasperreports.engine.JRException;

import java.io.FileNotFoundException;

public interface GenerarPDFservice {
    byte[] generarPDFReporteGuia(Integer id);// throws FileNotFoundException, JRException;
}
