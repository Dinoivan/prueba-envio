package com.incloud.hcp.ws.insite.bean;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class SoapBody {

    @XmlElement(name = "consultaRUCResponse", namespace = "http://ws.insite.pe/sunat/ruc.php?wsdl")
    private ConsultaRUCResponse consultaRUCResponse;

    @XmlElement(name = "Fault", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
    private SoapFault fault;


}