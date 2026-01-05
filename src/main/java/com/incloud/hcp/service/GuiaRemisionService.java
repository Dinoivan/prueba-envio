package com.incloud.hcp.service;


import com.incloud.hcp.domain.balanza.GuiaRemision;
import com.incloud.hcp.dto.DatosBLZProveedorDTO;
import com.incloud.hcp.dto.FiltroProveedorDTO;
import com.incloud.hcp.dto.GuiaRemisionDTO;
import com.incloud.hcp.dto.GuiaRemisionSapDTO;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.util.List;

public interface GuiaRemisionService {
    List<GuiaRemision> getAllGuiaRemision();
    GuiaRemision getGuiaRemisionById(Integer idGuiaRemision);
    GuiaRemisionSapDTO save(GuiaRemisionDTO guiaRemision) throws Exception;
    GuiaRemisionSapDTO RegistrarGRE(GuiaRemisionDTO guiaRemision) throws Exception;
    String obtenerPdf(GuiaRemisionDTO guiaRemision) throws Exception;
    GuiaRemisionSapDTO anularGuia(GuiaRemisionDTO guiaRemision) throws Exception;

    List<DatosBLZProveedorDTO> getProveedorByProveedorandRucandSocialanddireccionandemail(FiltroProveedorDTO filtro);
    List<DatosBLZProveedorDTO> getProveedorDtoByRuc(String ruc);
    List<DatosBLZProveedorDTO> getProveedorDtoByRazonSocial(String razonSocial);

    List<DatosBLZProveedorDTO> getProveedorDtoByAcreedorCodigoSap(String acreedorCodigoSap);
    List<GuiaRemision> getAllGuiaRemisionByTicketPesajeId(String ticketPesajeId);

    SXSSFWorkbook generaConstanciaPeso(Integer id);

    JSONObject findByIdGuiaRemision(Integer id) throws FileNotFoundException;

}
