package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.AprobadorSolicitud;
import com.incloud.hcp.domain.HomologacionRespuesta;
import com.incloud.hcp.domain.SolicitudBlacklist;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.myibatis.mapper.*;
import com.incloud.hcp.repository.AprobadorSolicitudRepository;
import com.incloud.hcp.repository.SolicitudBlackListRepository;
import com.incloud.hcp.rest.bean.ProveedorDatosGeneralesDTO;
import com.incloud.hcp.service.DescargaService;
import com.incloud.hcp.service.LicitacionService;
import com.incloud.hcp.service.ProveedorService;
import com.incloud.hcp.service.reporte.proveedor.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrador on 30/11/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class DescargaServiceImpl implements DescargaService {

    @Autowired
    private ProveedorService proveedorService;

    @Autowired
    private SolicitudBlackListRepository solicitudBlackListRepository;

    @Autowired
    private AprobadorSolicitudRepository aprobadorSolicitudRepository;

    @Autowired
    private ContactoMapper contactoMapper;

    @Autowired
    private CuentaBancariaMapper cuentaBancariaMapper;

    @Autowired
    private LineaComercialMapper lineaComercialMapper;

    @Autowired
    private ProductoMapper productoMapper;

    @Autowired
    private HomologacionMapper homologacionMapper;

    @Autowired
    private LicitacionService licitacionService;

    @Override
    @Transactional(readOnly = true)
    public HSSFWorkbook getProveedores(String selection)  {

        Pattern p = Pattern.compile("[0-1]{6}");
        Matcher m = p.matcher(selection);

        if (!m.matches()) {
            throw new PortalException("La selección es incorrecta");
        }
        String[] opciones = selection.split("");

        HSSFWorkbook book = new HSSFWorkbook();
        if (opciones[0].equals("1")) {
            String fechaCreacionIni = new String();
            String fechaCreacionFin = new String();
            List<ProveedorDatosGeneralesDTO> list = this.proveedorService.getProveedorDatosGenerales(
                    fechaCreacionIni, fechaCreacionFin
            );

            ProveedorReporte rptProveedor = new ProveedorReporte(book);
            rptProveedor.agregar(list);
        }

        if (opciones[1].equals("1")) {
            SucursalReporte rptSucursal = new SucursalReporte(book);
            rptSucursal.agregar(contactoMapper.getListAllSucursalContactoProveedor());
        }

        if (opciones[2].equals("1")) {
            CuentaBancoReporte rptCuentaBancaria = new CuentaBancoReporte(book);
            rptCuentaBancaria.agregar(cuentaBancariaMapper.getListAllCuentasBancariasProveedor());
        }

        if (opciones[3].equals("1")) {
            LineaComercialReporte rptLineaComercial = new LineaComercialReporte(book);
            rptLineaComercial.agregar(lineaComercialMapper.getListAllLineasComercialesProveedor());
        }

        if (opciones[4].equals("1")) {
            ProductoReporte rptProducto = new ProductoReporte(book);
            rptProducto.agregar(productoMapper.getListAllProductoProveedor());
        }

        if (opciones[5].equals("1")) {
            List<SolicitudBlacklist> listSolicitudBlacklist = solicitudBlackListRepository.findAll();

            if (listSolicitudBlacklist != null && listSolicitudBlacklist.size() > 0) {
                listSolicitudBlacklist.forEach(solicitud -> {
                    List<AprobadorSolicitud> lista = aprobadorSolicitudRepository.getListAprobadorByIdSolicitud(solicitud.getIdSolicitud());
                    solicitud.setListAprobador(lista);
                });

                NoConformeReporte rptNoConforme = new NoConformeReporte(book);
                rptNoConforme.agregar(listSolicitudBlacklist);
            }
        }

        return book;
    }


    @Override
    @Transactional(readOnly = true)
    public HSSFWorkbook getHomologacion() {

        HSSFWorkbook book = new HSSFWorkbook();
        List<HomologacionRespuesta> list = this.homologacionMapper.getListHomologacionExcel();
        HomologacionReporte homologacionReporte = new HomologacionReporte(book);
        homologacionReporte.agregar(list);

        return book;
    }




}
