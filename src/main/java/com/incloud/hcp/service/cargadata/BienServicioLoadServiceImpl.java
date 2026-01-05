package com.incloud.hcp.service.cargadata;

import com.incloud.hcp.domain.BienServicio;
import com.incloud.hcp.domain.RubroBien;
import com.incloud.hcp.domain.UnidadMedida;
import com.incloud.hcp.repository.BienServicioRepository;
import com.incloud.hcp.repository.RubroBienRepository;
import com.incloud.hcp.repository.UnidadMedidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.charset.Charset;
import java.util.StringTokenizer;

/**
 * Created by USER on 16/11/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class BienServicioLoadServiceImpl implements BienServicioLoadService{
    @Autowired
    BienServicioRepository bienServicioRepository;

    @Autowired
    RubroBienRepository rubroBienRepository;

    @Autowired
    UnidadMedidaRepository unidadMedidaRepository;

    @Override
    public String loadDataBienServicio(Integer numberFile) {

        String respuesta = "exito";
        BufferedReader br = null;
        RubroBien rubroBien;
        UnidadMedida unidadMedida;
        BienServicio material;

        try {
            String line;
            String name = "com/incloud/hcp/datafile/bien_servicio" + numberFile + ".csv";
            ClassLoader classLoader = BienServicioLoadService.class.getClassLoader();
            File file = new File(classLoader.getResource(name).getFile());

            Charset inputCharset = Charset.forName("ISO-8859-1");
            br = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), inputCharset));

            int i=0;
            System.out.println("Iniciando insercion ...");
            while ((line = br.readLine()) != null) {
                StringTokenizer stringTokenizer = new StringTokenizer(line, "|");

                Integer idBienServicio = Integer.parseInt(stringTokenizer.nextToken());
                Integer idRubro = Integer.parseInt(stringTokenizer.nextToken());
                String codigoSap = stringTokenizer.nextToken();
                String descripcion = stringTokenizer.nextToken();
                String nroParte = stringTokenizer.nextToken();
                Integer idUnidadMedida = Integer.parseInt(stringTokenizer.nextToken());
                String tipoItem = stringTokenizer.nextToken();

                rubroBien = new RubroBien();
                rubroBien.setIdRubro(idRubro);

                unidadMedida = new UnidadMedida();
                unidadMedida.setIdUnidadMedida(idUnidadMedida);

                material = new BienServicio();
                //material.setIdBienServicio(idBienServicio);
                material.setCodigoSap(codigoSap);
                material.setDescripcion(descripcion);
                material.setNumeroParte(nroParte.trim());
                material.setTipoItem(tipoItem.trim());
                material.setRubroBien(rubroBien);
                material.setUnidadMedida(unidadMedida);

                //System.out.println(material.toString());
                bienServicioRepository.save(material);
                i++;
            }

            System.out.println("Insercion completada: " + i + " registros");
            respuesta="exito";
        } catch (IOException e) {
            e.printStackTrace();
            respuesta = "error";

        } finally {
            try {
                if (br != null)
                    br.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return respuesta;
    }
}
