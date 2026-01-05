package com.incloud.hcp.pdf;

import com.incloud.hcp.pdf.service.PdfGeneratorService;
import com.incloud.hcp.pdf.service.impl.PdfGeneratorServiceJasperImpl;

public class PdfGeneratorFactory {

    private PdfGeneratorFactory(){
    }

    public static PdfGeneratorService getJasperGenerator(){
        return PdfGeneratorServiceJasperImpl.getInstance();
    }
}
