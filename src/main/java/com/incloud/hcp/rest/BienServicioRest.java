package com.incloud.hcp.rest;

import com.incloud.hcp.bean.File64;
import com.incloud.hcp.domain.BienServicio;
import com.incloud.hcp.domain.LicitacionDetalle;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.repository.CentroAlmacenRepository;
import com.incloud.hcp.rest._framework.AppRest;
import com.incloud.hcp.sap.materiales.BienServicioResponse;
import com.incloud.hcp.service.BienServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by USER on 20/09/2017.
 */
@RestController
@RequestMapping(value = "/api/bien-servicio")
public class BienServicioRest extends AppRest {

    @Autowired
    private BienServicioService bienServicioService;

    @Autowired
    private CentroAlmacenRepository centroAlmacenRepository;


    @RequestMapping(value = "/consulta/codigo-sap/{codigoSap}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<BienServicio>> getListBienServicioByCodigoSap (@PathVariable("codigoSap") String codigoSap) {

        return Optional.of(this.bienServicioService.getListBienServicioByCodigoSap(codigoSap))
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @RequestMapping(value = "/consulta/numero-parte", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<BienServicio>> getListBienServicioByNroParte(@RequestBody String nroParte)  {

        List<BienServicio> lista= this.bienServicioService.getListBienServicioByNroParte(nroParte);

        return Optional.of(lista)
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }
    @RequestMapping(value = "/validate/excel", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<?>  getListBienServicioByExcel(@RequestBody File64 file)  {

        Map<String, Object> obj = this.bienServicioService.getListBienServicioByExcel(file);
        return this.processObject(obj);
       /* return Optional.of(lista)
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));*/
    }
    @RequestMapping(value = "/validate/excel2", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<List<LicitacionDetalle>> getListBienServicioByExcel2(@RequestBody File64 file)  {

        List<LicitacionDetalle> lista = new ArrayList<LicitacionDetalle>();
        for(int i = 0 ; i < 10 ; i++) {
            List<BienServicio> listaBs10 = this.bienServicioService.getListBienServicioByCodigoSap("3000009");
        }
        List<BienServicio> listaBs = this.bienServicioService.getListBienServicioByCodigoSap("3000009");
        List<BienServicio> listaBs2 = this.bienServicioService.getListBienServicioByCodigoSap("3000010");
        List<BienServicio> listaBs3 = this.bienServicioService.getListBienServicioByCodigoSap("3000009");
        //List<BienServicio> listaBs = this.bienServicioService.getListBienServicioByCodigoSap("3000009");

        BienServicio bs = new BienServicio();
        bs = listaBs3.get(0);

        //BienServicio bs = new BienServicio();
        //bs.setCodigoSap("E");
        //bs.setDescripcion("aver");
        LicitacionDetalle detalle = new LicitacionDetalle();
        detalle.setBienServicio(listaBs.get(0));

        LicitacionDetalle detalle2 = new LicitacionDetalle();
        detalle2.setBienServicio(listaBs2.get(0));

        LicitacionDetalle detalle3 = new LicitacionDetalle();
        detalle3.setBienServicio(bs);
        //detalle.set
        lista.add(detalle);
        lista.add(detalle2);
        lista.add(detalle3);
        return Optional.of(lista)
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @RequestMapping(value = "/consulta/grupo-articulo/{idGrupoArticulo}", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<BienServicio>> getListBienServicioByGrupoArticulo (@PathVariable("idGrupoArticulo") int idGrupoArticulo) {

        return Optional.of(this.bienServicioService.getListBienServicioByGrupoArticulo(idGrupoArticulo))
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @RequestMapping(value = "/sap/sync-last-date", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<?> sincronizarBienServicio () {

        BienServicioResponse response = new BienServicioResponse();
        try {
            response = this.bienServicioService.sincronizarBienServicioByLastDate();
        }catch(Exception e){
            e.printStackTrace();
            new PortalException(e.getMessage());
        }

        return Optional.of(response)
                .map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

}
