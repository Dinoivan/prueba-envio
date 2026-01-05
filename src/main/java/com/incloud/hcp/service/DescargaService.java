package com.incloud.hcp.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Created by Administrador on 30/11/2017.
 */
public interface DescargaService {
    HSSFWorkbook getProveedores(String selection) ;

    HSSFWorkbook getHomologacion();

}
