package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.Parametro;
import com.incloud.hcp.repository.ParametroRepository;
import com.incloud.hcp.sap.SapLog;
import com.incloud.hcp.sap.solped.SolpedResponse;
import com.incloud.hcp.sap.solped.SolpedWebService;
import com.incloud.hcp.service.SolpedService;
import com.incloud.hcp.util.constant.WebServiceConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by USER on 11/09/2017.
 */
@Service
@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
public class SolpedServiceImpl implements SolpedService {

    private static Logger logger = LoggerFactory.getLogger(SolpedServiceImpl.class);

    @Autowired
    private SolpedWebService solpedWebService;

    @Autowired
    private ParametroRepository parametroRepository;

    @Override
    public SolpedResponse getSolpedResponseByCodigo(String codigoSolped) {

        Parametro wsdlParam = this.parametroRepository.getParametroByModuloAndTipo(WebServiceConstant.WEB_SERVICE,
                WebServiceConstant.URL_WSDL_SOLPED);
        if (wsdlParam == null){
            logger.error("url_wsdl not found");
        }
        Parametro userParam = this.parametroRepository.getParametroByModuloAndTipo(WebServiceConstant.WEB_SERVICE,
                WebServiceConstant.SAP_USERNAME);
        if (userParam == null){
            logger.error("sap_user not found");
        }
        Parametro passParam = this.parametroRepository.getParametroByModuloAndTipo(WebServiceConstant.WEB_SERVICE,
                WebServiceConstant.SAP_PASSWORD);
        if (passParam == null){
            logger.error("sap_pass not found");
        }

        if (wsdlParam == null || userParam == null || passParam == null){
            SolpedResponse solpedResponse = new SolpedResponse();

            logger.error(WebServiceConstant.MSJ_PARAM_NOEXIST);
            solpedResponse.setSapLog(new SapLog(WebServiceConstant.CODE_PARAM_NOEXIST,
                    WebServiceConstant.MSJ_PARAM_NOEXIST));
            return solpedResponse;
        }

        String urlWsdl = SolpedService.class.getClassLoader().getResource(wsdlParam.getValor()).toString();
        logger.error("urlWsdl :"+urlWsdl+"-userParam :"+userParam.getValor()+"passParam: "+passParam.getValor());
        return solpedWebService.getSolpedResponseByCodigo(
                codigoSolped,
                urlWsdl,
                userParam.getValor(),
                passParam.getValor()
        );
    }




}
