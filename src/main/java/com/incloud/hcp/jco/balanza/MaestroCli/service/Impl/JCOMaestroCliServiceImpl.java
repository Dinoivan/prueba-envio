package com.incloud.hcp.jco.balanza.MaestroCli.service.Impl;

import com.incloud.hcp.domain.balanza.Cliente;
import com.incloud.hcp.domain.balanza.Transportista;
import com.incloud.hcp.jco.balanza.MaestroCli.dto.MaestroCliImport;
import com.incloud.hcp.jco.balanza.MaestroCli.dto.MaestroCliMapper;
import com.incloud.hcp.jco.balanza.MaestroCli.dto.MaestroCliResponse;
import com.incloud.hcp.jco.balanza.MaestroCli.service.JCOMaestroCliService;
import com.incloud.hcp.repository.ClienteRepository;
import com.sap.conn.jco.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class JCOMaestroCliServiceImpl implements JCOMaestroCliService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private ClienteRepository clienteRepository;
    @Value("${destination.rfc.profitgr}")
    private String destinationProfit;
    private final String FUNCION_CONSULTA_RFC = "ZPE_MM_CONSULTA_MAESTRO_CLI";
    @Override
    public List<MaestroCliResponse> consultaMaestro(MaestroCliImport maestroCliImport) throws Exception {
        logger.error("CONSULTA MAESTROCLI - START");
        String kunnr = maestroCliImport.getKunnr();
        logger.error("PARAMETRO kunnr: " + kunnr);
        String name1 = maestroCliImport.getName1();
        logger.error("PARAMETRO name1: " + name1);
        String stcd1 = maestroCliImport.getStcd1();
        logger.error("PARAMETRO stcd1: " + stcd1);
        JCoDestination destination = JCoDestinationManager.getDestination(destinationProfit);
        logger.error("CONSULTA MAESTROCLI - JCoDestination: " + destination);

        JCoRepository repository = destination.getRepository();
        logger.error("CONSULTA MAESTROCLI - JCoRepository: " + repository);

        JCoFunction jCoFunction = repository.getFunction(FUNCION_CONSULTA_RFC);
        logger.error("CONSULTA MAESTROCLI - JCoFunction: " + jCoFunction);

        logger.error("CONSULTA MAESTROCLI - mapFilter START");
        this.mapFilter(jCoFunction, kunnr,name1,stcd1);
        logger.error("CONSULTA MAESTROCLI - mapFilter END");

        logger.error("CONSULTA MAESTROCLI - jCoFunction.execute START");
        jCoFunction.execute(destination);
        logger.error("CONSULTA MAESTROCLI - jCoFunction.execute END");

        logger.error("CONSULTA MAESTROCLI - JCoParameterList START");
        JCoParameterList exportParameterList = jCoFunction.getTableParameterList();
        logger.error("CONSULTA MAESTROCLI - JCoParameterList END");

        logger.error("CONSULTA MAESTROCLI - maestroCliMapper START");
        MaestroCliMapper maestroCliMapper = MaestroCliMapper.newMapper(exportParameterList);
        logger.error("CONSULTA MAESTROCLI - maestroCliMapper END");

        logger.error("CONSULTA MAESTROCLI - Llenado de MaestroCliResponse START");
        List<MaestroCliResponse> maestroCliResponseList = maestroCliMapper.getMaestroCliList();
        logger.error("CONSULTA MAESTROCLI - maestroCliResponseList: " + maestroCliResponseList);
        logger.error("CONSULTA MAESTROCLI - Llenado de MaestroCliResponse END");

        return maestroCliResponseList;
    }

    public void mapFilter(JCoFunction jCoFunction, String kunnr, String name1, String stcd1){
        logger.error("CONSULTA MAESTROCLI - mapFilter START");
        logger.error("SEGUNDA VALIDACION DE PARAMETRO kunnr: " + kunnr);

        JCoParameterList parameterList = jCoFunction.getImportParameterList();
        logger.error("CONSULTA MAESTROCLI - JCoParameterList: " + parameterList);
        parameterList.setValue("PI_KNA1", kunnr);
        parameterList.setValue("PI_NAME1", name1);
        parameterList.setValue("PI_STCD1", stcd1);

        logger.error("CONSULTA MAESTROCLI - mapFilter END");
    }

    @Override
    public Cliente cambiarEstado(Long clienteId) throws Exception {
        logger.error("[cambiarEstadoCliente]:clienteId:{}", clienteId);
        Optional<Cliente> optionalCliente = this.clienteRepository.findById(clienteId);
        logger.error("[cambiarEstadoCliente]:clienteId:{}", optionalCliente);
        if(!optionalCliente.isPresent()){
            throw new Exception("No se encontró cliente con ID:"+ clienteId);
        }
        Cliente cliente = optionalCliente.get();
        cliente.setEstado(!cliente.getEstado());
        logger.error("[cambiarEstadoCliente]:clienteId:{}", cliente);
        return this.clienteRepository.save(cliente);
    }

    @Override
    public List<Cliente> findAll() {
        return this.clienteRepository.findAll().stream().collect(Collectors.toList());
    }

}
