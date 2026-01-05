package com.incloud.hcp.pdf.test;

import com.incloud.hcp.pdf.PdfGeneratorFactory;
import com.incloud.hcp.pdf.bean.PrefacturaPdfDto;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Ignore
public class PrefacturaPdfGeneratorServiceTest {

    @Test
    public void generatePdf() throws IOException {
        PrefacturaPdfDto prefacturaPdfDto = Mock.getInstance().getMockPrefacturaPdfDto();

        long l = System.currentTimeMillis();
        byte[] bytes = PdfGeneratorFactory.getJasperGenerator().generatePrefacturaPdfBytes(prefacturaPdfDto);
        System.out.println("Total generation time: " + (System.currentTimeMillis() - l));

        String pdfPath = "C:\\Users\\Admin\\Desktop\\MockPrefacturaPdf_" + prefacturaPdfDto.getPrefacturaReferencia() + ".pdf";

        Files.write(Paths.get(pdfPath), bytes);
    }
}
